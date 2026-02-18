package dev.railroadide.railroadpluginapi

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.os.OperatingSystem
import org.gradle.process.ExecOperations

import javax.inject.Inject

abstract class BuildJarTask extends DefaultTask {
    @Input
    abstract Property<String> getJarTaskName()
    @Input
    abstract Property<String> getSubdirName()
    @InputDirectory
    abstract DirectoryProperty getCheckoutDir()
    @OutputFile
    abstract RegularFileProperty getOutputJar()
    @Inject
    protected abstract ExecOperations getExecOperations()

    @TaskAction
    void run() {
        File repoRoot = checkoutDir.get().asFile
        String subdir = subdirName.get().trim()
        File projectDir = subdir
                ? new File(repoRoot, subdir)
                : repoRoot

        if (!projectDir.exists()) {
            throw new GradleException("Subdir '${subdirName.get()}' does not exist in ${repoRoot.absolutePath}")
        }

        boolean windows = OperatingSystem.current().isWindows()
        File wrapper = new File(projectDir, windows ? "gradlew.bat" : "gradlew")

        List<String> cmd = wrapper.exists()
                ? [wrapper.absolutePath]
                : ["gradle"]

        execOperations.exec {
            workingDir projectDir
            environment "JAVA_HOME", System.getProperty("java.home")
            commandLine(cmd + ["--no-daemon", jarTaskName.get()])
        }

        File libsDir = new File(projectDir, "build/libs")
        if (!libsDir.exists()) {
            throw new GradleException("No build/libs directory in ${projectDir.absolutePath}. Did the repo produce a jar?")
        }

        // Pick newest non-sources/non-javadoc jar
        File jar = libsDir.listFiles()
                ?.findAll { it.isFile() && it.name.endsWith(".jar") && !it.name.endsWith("-sources.jar") && !it.name.endsWith("-javadoc.jar") }
                ?.max { it.lastModified() }

        if (jar == null) {
            throw new GradleException("No jar found in ${libsDir.absolutePath}. Try configuring jarTask (e.g. shadowJar) or check repo build.")
        }

        File out = outputJar.get().asFile
        out.parentFile.mkdirs()
        jar.withInputStream { input ->
            out.withOutputStream { output ->
                output << input
            }
        }
    }
}
