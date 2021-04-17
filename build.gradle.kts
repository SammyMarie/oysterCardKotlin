plugins {
    kotlin("jvm") version "1.4.32"
}

group = "com.sammy"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    implementation(kotlin("stdlib"))
    implementation("org.projectlombok:lombok:1.18.16")
    implementation("org.javamoney:moneta:1.4.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.assertj:assertj-core:3.19.0")
    testImplementation("org.mockito:mockito-core:3.9.0")
    testImplementation("io.mockk:mockk:1.11.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}