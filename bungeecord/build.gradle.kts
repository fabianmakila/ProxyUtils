plugins {
	id("proxyutils.platform-conventions")
	alias(libs.plugins.pluginYml.bungee)
}

dependencies {
	compileOnly(libs.waterfall.api)

	implementation(libs.adventure.platform.bungeecord)
	implementation(libs.minimessage)

	implementation(libs.bstats.bungeecord)
	implementation(libs.cloud.bungeecord)
}

tasks {
	shadowJar {
		sequenceOf(
			"cloud.commandframework",
			"io.leangen",
			"net.kyori",
			"org.bstats",
			"space.arim"
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