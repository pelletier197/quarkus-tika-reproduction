plugins {
    java
    id("io.quarkus")
}


group = "com.example.tika"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:3.0.1.Final"))
    implementation("io.quarkiverse.tika:quarkus-tika:2.0.0")
    implementation("org.jboss.logging:commons-logging-jboss-logging")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

configurations.all {
    exclude(group="commons-logging", module="commons-logging")
}
