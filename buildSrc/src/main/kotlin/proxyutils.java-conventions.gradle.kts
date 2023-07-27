plugins {
	`java-library`
	id("com.diffplug.spotless")
}

group = "fi.fabianadrian"
version = "0.1.0"
description = "Common utilities and features for Minecraft proxies."

java.toolchain.languageVersion.set(JavaLanguageVersion.of(11))

tasks {
	compileJava {
		options.encoding = "UTF-8"
	}
}

spotless {
	java {
		indentWithTabs()
		trimTrailingWhitespace()
		removeUnusedImports()
	}
}