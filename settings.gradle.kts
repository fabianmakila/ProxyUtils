enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "ProxyUtils"

include(
    "common",
    "bungeecord",
    "velocity"
)

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}