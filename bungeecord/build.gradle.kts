plugins {
	id("proxyutils.platform-conventions")
	alias(libs.plugins.pluginYml.bungee)
}

dependencies {
	compileOnly(libs.bungeecord.api)

	implementation(libs.adventure.minimessage)
	implementation(libs.adventure.platform.bungeecord)

	implementation(libs.bstats.bungeecord)
	implementation(libs.cloud.bungeecord)
	implementation(libs.slf4j)
}

tasks {
	shadowJar {
		sequenceOf(
			"cloud.commandframework",
			"io.leangen",
			"net.kyori",
			"org.bstats",
			"space.arim",
			"org.slf4j"
		).forEach {
			relocate(it, "${project.group}.${rootProject.name.lowercase()}.dependency.$it")
		}
	}
}

bungee {
	main = "fi.fabianadrian.proxyutils.bungeecord.ProxyUtilsBungeecord"
	name = rootProject.name
	author = "FabianAdrian"
}