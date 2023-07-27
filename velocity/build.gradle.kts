plugins {
	id("proxyutils.platform-conventions")
	alias(libs.plugins.shadow)
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
			"space.arim"
		).forEach { pkg ->
			relocate(pkg, "${project.group}.${rootProject.name.lowercase()}.dependency.$pkg")
		}
		destinationDirectory.set(file("${rootProject.rootDir}/dist"))
		archiveBaseName.set("${rootProject.name}-Velocity")
		archiveClassifier.set("")
	}
}