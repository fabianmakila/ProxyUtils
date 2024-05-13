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
}

tasks {
	shadowJar {
		sequenceOf(
			"io.leangen",
			"org.bstats",
			"org.incendo.cloud",
			"space.arim"
		).forEach {
			relocate(it, "${project.group}.${rootProject.name.lowercase()}.dependency.$it")
		}
	}
}