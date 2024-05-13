plugins {
	id("proxyutils.java-conventions")
}

dependencies {
	compileOnly(libs.adventure.api)
	compileOnly(libs.adventure.minimessage)

	compileOnly(libs.cloud.core)
	implementation(libs.cloud.minecraftExtras)

	implementation(libs.dazzleconf) {
		exclude("org.yaml")
	}
	compileOnly(libs.guava)
	compileOnly(libs.slf4j)
	compileOnly(libs.snakeyaml)
}