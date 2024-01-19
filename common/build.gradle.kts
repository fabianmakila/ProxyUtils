plugins {
	id("proxyutils.java-conventions")
}

dependencies {
	compileOnly(libs.cloud.core)
	compileOnly(libs.adventure.api)
	compileOnly(libs.slf4j)
	compileOnly(libs.guava)
	compileOnly(libs.snakeyaml)

	compileOnly(libs.minimessage)

	// Config
	implementation(libs.dazzleconf) {
		exclude("org.yaml")
	}
}