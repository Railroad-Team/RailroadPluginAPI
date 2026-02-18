package dev.railroadide.railroadpluginapi

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class PublishGitDependencyModuleTask extends DefaultTask {
    @InputFile
    abstract RegularFileProperty getInputJar()
    @OutputDirectory
    abstract DirectoryProperty getRepositoryDir()
    @Input
    abstract Property<String> getGroupId()
    @Input
    abstract Property<String> getArtifactId()
    @Input
    abstract Property<String> getVersionId()

    @TaskAction
    void run() {
        String group = groupId.get()
        String artifact = artifactId.get()
        String version = versionId.get()

        String groupPath = group.replace('.', '/')
        File versionDir = new File(repositoryDir.get().asFile, "${groupPath}/${artifact}/${version}")
        versionDir.mkdirs()

        File sourceJar = inputJar.get().asFile
        File targetJar = new File(versionDir, "${artifact}-${version}.jar")
        sourceJar.withInputStream { input ->
            targetJar.withOutputStream { output ->
                output << input
            }
        }

        File pomFile = new File(versionDir, "${artifact}-${version}.pom")
        pomFile.text = """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>${group}</groupId>
  <artifactId>${artifact}</artifactId>
  <version>${version}</version>
</project>
"""
    }
}
