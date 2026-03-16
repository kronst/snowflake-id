plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "snowflake-id"

include("snowflake-id-core")
include("snowflake-id-spring-boot-starter")
