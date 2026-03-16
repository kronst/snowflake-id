import org.jreleaser.model.Active

plugins {
    id("org.jreleaser") version "1.22.0"
}

group = "io.github.kronst"

jreleaser {
    project {
        copyright.set("Roman Konstantynovskyi")
        name.set("snowflake-id")
        description.set("Lightweight Kotlin library for generating Snowflake IDs.")

        links {
            homepage.set("https://github.com/kronst/snowflake-id")
        }
    }
    signing {
        active.set(Active.ALWAYS)
        pgp {
            armored.set(true)
        }
    }
    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    active.set(Active.ALWAYS)
                    url.set("https://central.sonatype.com/api/v1/publisher/")
                    namespace.set("io.github.kronst")
                    stagingRepository("build/staging-deploy")
                }
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}
