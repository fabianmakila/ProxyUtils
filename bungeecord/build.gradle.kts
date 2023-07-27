plugins {
	id("proxyutils.platform-conventions")
	alias(libs.plugins.shadow)
	alias(libs.plugins.pluginYml.bungee)
}

dependencies {
	compileOnly(libs.waterfall.api)

	implementation(libs.cloud.bungeecord)
	implementation(libs.adventure.platform.bungeecord)
	implementation(libs.bstats.bungeecord)
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
			"net.kyori",
			"net.kyori.adventure.text.minimessage",
			"org.bstats",
			"space.arim"
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