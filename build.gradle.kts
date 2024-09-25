/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api("org.apache.logging.log4j:log4j-core:2.17.1")
    api("junit:junit:4.13.1")
    api("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation("javax.validation:validation-api:2.0.1.Final")
}

group = "io.github.gusthavosouza.srt"
version = "0.0.1"
description = "SRTParser"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
