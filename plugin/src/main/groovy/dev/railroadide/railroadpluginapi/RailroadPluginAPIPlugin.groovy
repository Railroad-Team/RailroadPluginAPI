package dev.railroadide.railroadpluginapi

import org.gradle.api.Plugin
import org.gradle.api.Project

import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

class RailroadPluginAPIPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def ext = project.extensions.create('railroadDependency', GitDependencyExtension)
        ext.repoUrl.convention("https://github.com/Railroad-Team/Railroad")
        ext.commitHash.convention("HEAD")
        ext.subdirectory.convention("")
        ext.jarTaskName.convention("jar")
        ext.dependencyGroup.convention("dev.railroadide.gitdeps")
        ext.dependencyName.convention(ext.repoUrl.map { repo ->
            String name = repo.tokenize('/').last()
            if (name.endsWith(".git")) {
                name = name.substring(0, name.length() - 4)
            }
            return name.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9._-]", "-")
        })
        ext.dependencyVersion.convention(ext.commitHash.map { hash ->
            hash.replaceAll("[^A-Za-z0-9._-]", "-")
        })

        def checkoutDir = project.layout.buildDirectory.dir("gitDeps/checkout/repo")
        def builtJar = project.layout.buildDirectory.file(
                ext.jarTaskName.map { "gitDeps/artifacts/${it}.jar" }
        )
        def mavenRepoDir = project.layout.buildDirectory.dir("gitDeps/maven")

        def fetch = project.tasks.register("fetchGitDependency", GitExecTask) { task ->
            task.repositoryUrl.set(ext.repoUrl)
            task.commitHash.set(ext.commitHash)
            task.outputDir.set(checkoutDir)
        }

        def buildJar = project.tasks.register("buildGitDepJar", BuildJarTask) { task ->
            task.dependsOn(fetch)
            task.jarTaskName.set(ext.jarTaskName)
            task.subdirName.set(ext.subdirectory)
            task.checkoutDir.set(fetch.flatMap { it.outputDir })
            task.outputJar.set(builtJar)
        }

        def publishGitDepModule = project.tasks.register("publishGitDepModule", PublishGitDependencyModuleTask) { task ->
            task.dependsOn(buildJar)
            task.inputJar.set(builtJar)
            task.repositoryDir.set(mavenRepoDir)
            task.groupId.set(ext.dependencyGroup)
            task.artifactId.set(ext.dependencyName)
            task.versionId.set(ext.dependencyVersion)
        }

        project.repositories.maven { repo ->
            repo.name = "gitDependencyLocal"
            repo.url = project.uri(mavenRepoDir.get().asFile)
        }

        project.afterEvaluate {
            def notation = "${ext.dependencyGroup.get()}:${ext.dependencyName.get()}:${ext.dependencyVersion.get()}"
            project.dependencies.add("implementation", notation)
        }

        def prepared = new AtomicBoolean(false)
        def ensurePrepared = {
            if (prepared.get()) {
                return
            }

            synchronized (prepared) {
                if (prepared.get()) {
                    return
                }

                String groupPath = ext.dependencyGroup.get().replace('.', '/')
                String artifact = ext.dependencyName.get()
                String version = ext.dependencyVersion.get()
                File modulePom = new File(
                        mavenRepoDir.get().asFile,
                        "${groupPath}/${artifact}/${version}/${artifact}-${version}.pom"
                )

                if (!modulePom.exists()) {
                    fetch.get().run()
                    buildJar.get().run()
                    publishGitDepModule.get().run()
                }

                prepared.set(true)
            }
        }

        project.configurations.configureEach { cfg ->
            if (cfg.canBeResolved) {
                cfg.incoming.beforeResolve {
                    ensurePrepared.call()
                }
            }
        }

        project.tasks.matching {it.name in ["compileJava", "compileKotlin"]}.configureEach {
            it.dependsOn(publishGitDepModule)
        }
    }
}
