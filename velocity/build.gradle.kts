plugins {
	id("proxyutils.platform-conventions")
}

dependencies {
	// Velocity
	compileOnly(libs.velocity.api)
	annotationProcessor(libs.velocity.api)

	// Libraries
	implementation(libs.bstats.velocity)
	implementation(libs.cloud.velocity)
	implementation(libs.snakeyaml)
}

tasks {
	shadowJar {
		sequenceOf(
			"cloud.commandframework",
			"io.leangen",
			"org.bstats",
			"org.yaml",
			"space.arim"
		).forEach {
			relocate(it, "${project.group}.${rootProject.name.lowercase()}.dependency.$it")
		}
	}
}