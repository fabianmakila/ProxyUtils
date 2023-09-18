plugins {
	id("proxyutils.platform-conventions")
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
		sequenceOf(
			"cloud.commandframework",
			"io.leangen",
			"net.kyori",
			"net.kyori.adventure.text.minimessage",
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