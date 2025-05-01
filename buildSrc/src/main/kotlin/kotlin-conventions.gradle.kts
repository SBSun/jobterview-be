import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
    kotlin("plugin.spring")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "21"
    targetCompatibility = "21"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21"
        freeCompilerArgs.plus("-Xjsr305=strict")
        freeCompilerArgs.plus("-Xjvm-default=enable")
        freeCompilerArgs.plus("-progressive")
        freeCompilerArgs.plus("-XXLanguage:+InlineClasses")
    }
}

tasks.withType<Jar> {
    enabled = true
}

tasks.withType<BootJar> {
    enabled = false
}

tasks.withType<Test> {
//    failFast = true

    useJUnitPlatform()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.0")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.0")
    testImplementation("io.kotest:kotest-property-jvm:5.9.0")
    testImplementation("io.kotest:kotest-framework-datatest-jvm:5.9.0")
    testImplementation("io.mockk:mockk:1.13.10")
}
