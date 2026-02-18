package dev.railroadide.railroadpluginapi

import org.gradle.api.provider.Property

abstract class GitDependencyExtension {
    abstract Property<String> getRepoUrl()
    abstract Property<String> getCommitHash()
    abstract Property<String> getSubdirectory()
    abstract Property<String> getJarTaskName()
    abstract Property<String> getDependencyGroup()
    abstract Property<String> getDependencyName()
    abstract Property<String> getDependencyVersion()
}
