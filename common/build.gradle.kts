plugins {
    id("proxyutils.java-conventions")
}

dependencies {
    compileOnly(libs.cloud.core)
    compileOnly(libs.adventure.api)
    compileOnly(libs.slf4j)
    compileOnly(libs.guava)
    compileOnly(libs.snakeyaml)

    implementation(libs.geoip2)
    implementation(libs.caffeine) {
        exclude("org.checkerframework")
    }

    implementation(libs.minimessage) {
        exclude("net.kyori")
    }

    // Config
    implementation(libs.dazzleconf) {
        exclude("org.yaml")
    }
}