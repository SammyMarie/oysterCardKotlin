plugins {
    kotlin("jvm") version "1.4.32"
}

group = "com.sammy"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val lombok: String by project
val moneta: String by project
val junit5: String by project
val assertj: String by project
val mockito: String by project
val mockk: String by project

dependencies {
    annotationProcessor("org.projectlombok:lombok:${lombok}")
    implementation(kotlin("stdlib"))
    implementation("org.projectlombok:lombok:${lombok}")
    implementation("org.javamoney:moneta:${moneta}")

    testImplementation("org.junit.jupiter:junit-jupiter:${junit5}")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.assertj:assertj-core:${assertj}")
    testImplementation("org.mockito:mockito-core:${mockito}")
    testImplementation("io.mockk:mockk:${mockk}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}