plugins {
    kotlin("jvm")
    id("snowflake-id.publishing")
}

group = rootProject.group

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(platform(libs.findLibrary("junitBom").get()))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

kotlin {
    jvmToolchain(17)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
