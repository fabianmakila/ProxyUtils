plugins {
    id("proxyutils.java-conventions")
    alias(libs.plugins.shadow)
    alias(libs.plugins.pluginYml.bungee)
}

dependencies {
    implementation(projects.common)

    compileOnly(libs.waterfall.api)

    implementation(libs.cloud.bungeecord)
    implementation(libs.adventure.platform.bungeecord)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        minimize {
            exclude(dependency("com.github.ben-manes.caffeine:caffeine:.*"))
        }
        sequenceOf(
            "cloud.commandframework",
            "com.fasterxml",
            "com.github.benmanes",
            "com.google.errorprone",
            "com.maxmind",
            "io.leangen",
            "net.kyori.adventure.text.minimessage",
            "org.bstats",
            "org.yaml",
            "space.arim",
            "net.kyori"
        ).forEach { pkg ->
            relocate(pkg, "${project.group}.${rootProject.name.lowercase()}.dependency.$pkg")
        }
        destinationDirectory.set(file("${rootProject.rootDir}/dist"))
        archiveBaseName.set(rootProject.name + "-Bungeecord")
        archiveClassifier.set("")
    }
}

bungee {
    main = "fi.fabianadrian.proxyutils.bungeecord.ProxyUtilsBungeecord"
    name = rootProject.name
    author = "FabianAdrian"
}