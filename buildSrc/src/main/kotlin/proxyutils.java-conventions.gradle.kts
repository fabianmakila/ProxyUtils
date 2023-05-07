plugins {
    java

    id("com.diffplug.spotless")
    id("net.kyori.indra")
}

group = "fi.fabianadrian"
version = "0.1.0"
description = "Common utilities and features for Minecraft proxies."

indra {
    javaVersions {
        target(11)
    }
}

spotless {
    java {
        indentWithSpaces(4)
        trimTrailingWhitespace()
        removeUnusedImports()
    }
}