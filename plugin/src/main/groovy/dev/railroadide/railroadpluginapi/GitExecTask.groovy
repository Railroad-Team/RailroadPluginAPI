package dev.railroadide.railroadpluginapi

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

import javax.inject.Inject

abstract class GitExecTask extends DefaultTask {
    @Input
    abstract Property<String> getRepositoryUrl()
    @Input
    abstract Property<String> getCommitHash()
    @OutputDirectory
    abstract DirectoryProperty getOutputDir()
    @Inject
    protected abstract ExecOperations getExecOperations()

    @TaskAction
    void run() {
        File dir = outputDir.get().asFile
        dir.parentFile.mkdirs()

        // Clone if missing
        if (!new File(dir, ".git").exists()) {
            execOperations.exec {
                commandLine "git", "clone", "--no-checkout", repositoryUrl.get(), dir.absolutePath
            }
        }

        // Fetch + checkout commit (force)
        execOperations.exec {
            workingDir dir
            commandLine "git", "fetch", "--all", "--tags", "--prune"
        }
        execOperations.exec {
            workingDir dir
            commandLine "git", "checkout", "--force", commitHash.get()
        }
    }
}
