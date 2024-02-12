plugins {
    id("fabric-loom") version "1.5-SNAPSHOT"
    id("maven-publish")
    id("signing")
    id("com.diffplug.spotless") version "6+"
}

val minecraftVersion = property("minecraft_version") as String
val modId = property("mod_id") as String

base {
    archivesName = modId
}

version = "${property("mod_version")}+$minecraftVersion"
group = property("maven_group") as String

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
}

loom {
    splitEnvironmentSourceSets()

    mods {
        register(modId) {
            sourceSet(sourceSets["main"])
            sourceSet(sourceSets["client"])
        }
    }
}

afterEvaluate {
    loom {
        runs {
            configureEach {
                vmArg("-javaagent:${configurations.compileClasspath.get().find { cls -> cls.name.contains("sponge-mixin") }}")
                vmArg("-XX:+AllowEnhancedClassRedefinition")
            }
        }
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }

    withType<JavaCompile> {
        options.release = 17
    }

    jar {
        from("LICENSE")
    }
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

signing {
    sign(publishing.publications)
}

publishing {
    publications {
        register<MavenPublication>(modId) {
            from(components["java"])
            artifact(tasks["sourcesJar"])

            pom {
                name = modId
                description = "A small library of shared code."
                url = "https://github.com/dicedpixels/pixel/"

                licenses {
                    license {
                        name = "MIT"
                    }
                }

                developers {
                    developer {
                        name = "dicedpixels"
                    }
                }

                scm {
                    url = "https://github.com/dicedpixels/pixel/"
                }
            }
        }

        repositories {
            maven {
                url = uri("https://repo.repsy.io/mvn/dicedpixels/fabric/")
                credentials {
                    username = providers.gradleProperty("repsyUsername").get()
                    password = providers.gradleProperty("repsyPassword").get()
                }
            }
        }
    }
}

spotless {
    java {
        toggleOffOn()
        palantirJavaFormat()
        removeUnusedImports()
        importOrder("java", "javax", "", "net.minecraft", "com.mojang", "net.fabricmc", "xyz.dicedpixels")
        formatAnnotations()
    }

    kotlinGradle {
        ktlint()
    }

    json {
        target("src/**/*.json")
        gson()
    }
}
