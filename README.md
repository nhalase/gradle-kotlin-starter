# gradle-kotlin-starter

This repository contains an opinionated, but flexible, starting point for a Kotlin-based project using Gradle as the sole build tool.  

## Goals of this template

- The build pipeline MUST be platform-agnostic
  - Contributors should be free to develop using Windows, macOS, or Linux
- Linting and formatting MUST be independent of IDE/editor
- Nothing shall lock this template into any one CI server implementation

### The [`.gitattributes`](./.gitattributes) file

This presence of this file overrides the global `core.autocrlf` Git configuration. This ensures consistent behavior for all users, regardless of their Git settings and environment.  

**Why?**  

Windows is the ONLY operating system that uses `CRLF` [line endings](https://en.wikipedia.org/wiki/Newline); macOS and Linux both use `LF`.  

We are currently well into the 21st millennium and pretty much every IDE and text editor in existence on Windows 7 and newer supports changing default line endings to `LF`.  

`core.autocrlf` attempts to solve the problem of Git repositories having contributors from both `CRLF`-land and `RF`-land, but it can lead to inconsistent behavior when using `git`.  

The solution is to not be lazy. If you're on Windows, turn off `core.autocrlf` or use a `.gitattributes` file (see below), and configure your IDE/editor to use `LF` rather than `CRLF`. `core.autocrlf` really doesn't need to exist when Windows applications are capable of using `LF` files.  

> You'll want to make sure files you're committing are covered by the [`.gitattributes`](./.gitattributes) file.  
> 
> **General Guidance**:  If it's a text-based file, and it's not a native Windows file (e.g., `.bat`, `.ps`, `.ps1`), default to LF.

## Understanding the layout of this template

- [build-logic](./build-logic) contains extracted imperative Gradle build logic to be shared across Gradle subproject
  - [kotlin-common-conventions.gradle.kts](./build-logic/src/main/kotlin/dev.nhalase.kotlin-common-conventions.gradle.kts)
  - [kotlin-library-conventions.gradle.kts](./build-logic/src/main/kotlin/dev.nhalase.kotlin-library-conventions.gradle.kts)
    - Extends `kotlin-common-conventions.gradle.kts`
  - [kotlin-application-conventions.gradle.kts](./build-logic/src/main/kotlin/dev.nhalase.kotlin-application-conventions.gradle.kts)
    - Extends `kotlin-library-conventions.gradle.kts`
  - [kotlin-ktor-conventions.gradle.kts](./build-logic/src/main/kotlin/dev.nhalase.kotlin-ktor-conventions.gradle.kts)
    - Extends `kotlin-application-conventions.gradle.kts`
- [kotlin-ext](./kotlin-ext) is an example of a feature subproject
  - This specific feature example is a collection of utility functions and common interfaces that could be considered language extensions (or bloat :shrug:)
- [lib](./lib) is a Gradle library module that ties together feature subprojects
  - Has a Gradle `api` dependency (both `compile` and `runtime`) on [lib](./lib)
- [app](./app) is an example Gradle subproject representing a Kotlin CLI application
  - Has a Gradle `implementation` dependency (`runtime`) on [lib](./lib)
- [microservice](./microservice) is an example Gradle subproject representing a [Ktor](https://ktor.io/)-based microservice
  - Has a Gradle `implementation` dependency (`runtime`) on [lib](./lib)

### Versions

- The Kotlin version is declared in the both the [build-logic/gradle.properties](./build-logic/gradle.properties) and [rootProject gradle.properties](./gradle.properties) files
  - This is a workaround due to the precompiled scripts using the `kotlin-dsl` plugin
  - The `kotlin-dsl` Gradle plugin currently includes a bundled (old) version of Kotlin that will end up on your classpath (which we don't want)
    - Gradle and JetBrains _should_ be working on this for future versions of Gradle and Kotlin, but no release has been planned
- The Ktor version is declared in the [kotlin-ktor-conventions.gradle.kts](./build-logic/src/main/kotlin/dev.nhalase.kotlin-ktor-conventions.gradle.kts) precompiled script
- All other dependency versions should be declared as constraints in the [kotlin-common-conventions.gradle.kts](./build-logic/src/main/kotlin/dev.nhalase.kotlin-common-conventions.gradle.kts) precompiled script

### Gradle

Gradle 7.3.3 was chosen for this template because [Kotlin 1.8 came with full support](https://kotlinlang.org/docs/whatsnew18.html) for Gradle 7.3. Per the Kotlin "What's New" page, newer versions of Gradle should work, too.
