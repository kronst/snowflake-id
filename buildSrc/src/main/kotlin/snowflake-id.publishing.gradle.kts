plugins {
    `maven-publish`
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        pom {
            name.set(project.name)
            description.set("Lightweight library for generating snowflake id - ${project.name}")
            url.set("https://github.com/kronst/snowflake-id")

            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }

            developers {
                developer {
                    id.set("kronst")
                    name.set("Roman Konstantynovskyi")
                }
            }

            scm {
                connection.set("scm:git:git://github.com/kronst/snowflake-id.git")
                developerConnection.set("scm:git:ssh://github.com/kronst/snowflake-id.git")
                url.set("https://github.com/kronst/snowflake-id")
            }
        }
    }

    repositories {
        maven {
            url = uri(rootProject.layout.buildDirectory.dir("staging-deploy"))
        }
    }
}
