plugins {
	id("proxyutils.platform-conventions")
}

dependencies {
	// Velocity
	compileOnly(libs.velocity.api)
	annotationProcessor(libs.velocity.api)

	// Libraries
	implementation(libs.bstats.velocity)
	implementation(libs.snakeyaml)
	implementation(libs.cloud.velocity)
}

tasks {
	build {
		dependsOn(shadowJar)
	}
	shadowJar {
		sequenceOf(
			"cloud.commandframework",
			"io.leangen",
			"net.kyori.adventure.text.minimessage",
			"org.bstats",
			"org.yaml",
			"space.arim"
		).forEach {
			relocate(it, "${project.group}.${rootProject.name.lowercase()}.dependency.$it")
		}
	}
}