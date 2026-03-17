plugins {
    id("snowflake-id.library")
    kotlin("plugin.spring")
    alias(libs.plugins.springBoot)
    alias(libs.plugins.springDependencyManagement)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation(kotlin("reflect"))

    api(project(":snowflake-id-core"))

    testImplementation("org.springframework.boot:spring-boot-test")
    testImplementation("org.assertj:assertj-core")
}
