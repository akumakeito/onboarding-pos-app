rootProject.name = "Manager App"

dependencyResolutionManagement {
    val gitLabPrivateTokenType: String by settings
    val gitLabPrivateToken: String by settings
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven("https://androidx.dev/snapshots/builds/7633184/artifacts/repository")
        maven {
            name = "gitlab-maven"
            url = uri("https://nomia.dev/api/v4/groups/nomia/-/packages/maven")
            credentials(HttpHeaderCredentials::class.java) {
                name = gitLabPrivateTokenType
                value = gitLabPrivateToken
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
        maven("https://jitpack.io")
    }
}

include(":pos:app")
include(":core:core")
include(":common-ui")
include(":pos:common-data")
include(":pos:erp-schema")
include(":pos:erp-api")
include(":pos:main")
include(":settings")
include(":core:mvi-coroutines")
include(":core:mvi-android")

include(":core:common:converter")
include(":core:common:state-handler")
include(":core:common:utils")
include(":core:ui:compose")
include(":core:ui:splash_screen")
include(":core:ui:preview")
include(":core:ui:status-bar-controller")
include(":core:common:config-provider")
include(":core:common:serializer")
include(":core:common:network")
