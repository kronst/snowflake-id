plugins {
    id("snowflake-id.library")
    kotlin("plugin.spring")
}

dependencies {
    implementation(platform(libs.springBootDependencies))

    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation(kotlin("reflect"))

    api(project(":snowflake-id-core"))

    testImplementation("org.springframework.boot:spring-boot-test")
    testImplementation("org.assertj:assertj-core")
}
