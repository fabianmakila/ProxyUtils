plugins {
    `java-library`
    id("com.diffplug.spotless")
}

group = "fi.fabianadrian"
version = "0.1.0"
description = "Common utilities and features for Minecraft proxies."

java.toolchain.languageVersion.set(JavaLanguageVersion.of(11))

spotless {
    java {
        indentWithSpaces(4)
        trimTrailingWhitespace()
        removeUnusedImports()
    }
}