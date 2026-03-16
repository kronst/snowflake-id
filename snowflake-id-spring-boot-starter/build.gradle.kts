plugins {
    id("snowflake-id.library")
    id("org.jetbrains.kotlin.plugin.spring")
}

dependencies {
    implementation(platform(libs.springBootDependencies))
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    api(project(":snowflake-id-core"))

    testImplementation("org.springframework.boot:spring-boot-test")
    testImplementation("org.assertj:assertj-core")
}
