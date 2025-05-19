plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
    kotlin("plugin.jpa") version "1.9.23"
}

group = "com.ralphmorales"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.rate.limiting)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation("org.hibernate:hibernate-core:6.6.0.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.postgresql:postgresql:42.3.1")
    implementation("dev.restate:sdk-kotlin-http:2.1.0")
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.ktor:ktor-server-test-host:3.1.3")
    testImplementation("io.insert-koin:koin-test:3.5.0")
    testImplementation("io.insert-koin:koin-test-junit5:3.5.0")
    testImplementation("org.testcontainers:postgresql:1.21.0")
    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:2.0.2")
}

tasks.test {
    useJUnitPlatform()
}
