pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://www.jitpack.io")
        maven(url = "https://maven.google.com")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://www.jitpack.io")
        maven(url = "https://maven.google.com")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "SkillSpot"
includeBuild("build-logic")
include(
    ":app",
    ":shared:common:resources",
    ":shared:routing",

    ":shared:core:data",
    ":shared:core:domain",
    ":shared:core:ui",
    ":shared:core:routing",

    ":shared:feature:home:data",
    ":shared:feature:home:domain",
    ":shared:feature:home:ui",
    ":shared:feature:home:routing",

    ":shared:feature:search:data",
    ":shared:feature:search:domain",
    ":shared:feature:search:ui",
    ":shared:feature:search:routing",

    ":shared:feature:settings:data",
    ":shared:feature:settings:domain",
    ":shared:feature:settings:ui",
    ":shared:feature:settings:routing",

    ":shared:feature:auth:data",
    ":shared:feature:auth:domain",
    ":shared:feature:auth:ui",
    ":shared:feature:auth:routing",

    ":shared:feature:login:data",
    ":shared:feature:login:domain",
    ":shared:feature:login:ui",
    ":shared:feature:login:routing",

    ":shared:feature:register:data",
    ":shared:feature:register:domain",
    ":shared:feature:register:ui",
    ":shared:feature:register:routing",
)
 