plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

rootProject.name = "jobterview"

include("jobterview-common")
include("jobterview-domain")
include("jobterview-batch")
include("jobterview-api")
include("jobterview-mail")
