version = "1.0.0"
group = "xyz.srnyx"

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral() // org.spigotmc:spigot-api
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots") // org.spigotmc:spigot-api
    maven("https://oss.sonatype.org/content/repositories/snapshots") // org.spigotmc:spigot-api
    maven("https://jitpack.io") // xyz.srnyx:annoying-api
}

dependencies {
    compileOnly("org.spigotmc", "spigot-api", "1.11-R0.1-SNAPSHOT")
	implementation("xyz.srnyx", "annoying-api", "2.0.3")
}

tasks {
    // Make 'gradle build' run 'gradle shadowJar'
    build {
        dependsOn("shadowJar")
    }

    // Remove '-all' from the JAR file name and relocate the AnnoyingAPI package
    shadowJar {
        archiveClassifier.set("")
        relocate("xyz.srnyx.annoyingapi", "xyz.srnyx.stopbeinganidiot.annoyingapi")
    }

    // Text encoding
    compileJava {
        options.encoding = "UTF-8"
    }

    // Replace '${name}' and '${version}'
    processResources {
        inputs.property("name", project.name)
        inputs.property("version", version)
        filesMatching("**/plugin.yml") {
            expand("name" to project.name, "version" to version)
        }
    }
}