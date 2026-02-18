# RailroadPluginAPI

[![License: LGPL v3](https://img.shields.io/badge/License-LGPL_v3-blue.svg)](LICENSE)
[![Built with Gradle](https://img.shields.io/badge/Built%20with-Gradle-02303A?logo=gradle)](https://gradle.org)

RailroadPluginAPI is a Gradle plugin that turns a Git repository at a specific commit into a normal dependency.
It clones the repo, builds a JAR, publishes it into a local Maven-style repository, and wires it into your project automatically.

## Why This Plugin

- Pin dependencies to an exact Git commit.
- Consume non-published libraries without manual local installations.
- Build from repo root or a subdirectory (useful for monorepos).
- Keep a normal Gradle dependency flow in consuming projects.

## Quick Start

### Option A: Use a published release

Use this when the version you want is already published.

`settings.gradle`:

```groovy
pluginManagement {
    repositories {
        maven { url = uri("https://maven.railroadide.dev/releases") }
        gradlePluginPortal()
    }
}
```

Then apply the plugin in your project:

```groovy
plugins {
    id "dev.railroadide.railroadpluginapi" version "0.3.0"
}
```

### Option B: Self-publish locally (fallback)

If you want to use local changes or the version is not published yet:

```bash
./gradlew :plugin:publishToMavenLocal
```

`settings.gradle`:

```groovy
pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}
```

Then apply the same plugin id and the version you published locally in your consumer build.

### Configure the plugin

Groovy DSL (`build.gradle`):

```groovy
plugins {
    id "dev.railroadide.railroadpluginapi" version "0.3.0"
}

railroadDependency {
    repoUrl = "https://github.com/owner/repo.git"
    branch = "main" // optional
    commitHash = "a1b2c3d4e5f6"
    subdirectory = ""
    jarTaskName = "jar" // e.g. "shadowJar"
}
```

Kotlin DSL (`build.gradle.kts`):

```kotlin
plugins {
    id("dev.railroadide.railroadpluginapi") version "0.3.0"
}

configure<dev.railroadide.railroadpluginapi.GitDependencyExtension> {
    repoUrl.set("https://github.com/owner/repo.git")
    branch.set("main")
    commitHash.set("a1b2c3d4e5f6")
    subdirectory.set("")
    jarTaskName.set("jar")
}
```

The plugin generates and adds:

```text
<dependencyGroup>:<dependencyName>:<dependencyVersion>
```

to your `implementation` configuration automatically.

## Configuration Reference

Extension name: `railroadDependency`

| Property | Default | Description |
| --- | --- | --- |
| `repoUrl` | `https://github.com/Railroad-Team/Railroad` | Git repository URL to clone. |
| `branch` | `""` | Optional branch to target. When set with `commitHash=HEAD`, the plugin checks out `origin/<branch>`. |
| `commitHash` | `HEAD` | Commit, tag, or ref to check out. |
| `subdirectory` | `""` | Optional subdirectory to run the build in. |
| `jarTaskName` | `jar` | Gradle task used to produce the dependency JAR. |
| `dependencyGroup` | `dev.railroadide.gitdeps` | Group ID for generated coordinates. |
| `dependencyName` | Derived from repo name | Artifact ID for generated coordinates. |
| `dependencyVersion` | Derived from `commitHash` | Version for generated coordinates. |

## Tasks Registered

| Task | Purpose |
| --- | --- |
| `fetchGitDependency` | Clone/update repo and check out the requested commit. |
| `buildGitDepJar` | Execute configured JAR task and capture the built artifact. |
| `publishGitDepModule` | Publish JAR + generated POM into `build/gitDeps/maven`. |

The plugin also ensures preparation happens before dependency resolution and wires compile tasks such as `compileJava` and `compileKotlin` to depend on publication.

## Requirements

- Git available on `PATH`
- A working Java/Gradle environment
- The dependency repository must include:
  - a Gradle wrapper (`gradlew`/`gradlew.bat`), or
  - a system-installed `gradle` command

## Development

```bash
./gradlew :plugin:build
./gradlew :plugin:test
./gradlew :plugin:publishToMavenLocal
```

Remote publishing in this repository uses:

- `RAILROAD_MAVEN_USERNAME`
- `RAILROAD_MAVEN_PASSWORD`

## License

Licensed under GNU LGPL v3.0. See `LICENSE`.
