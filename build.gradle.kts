import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val groupName: String by project
val currentVersion: String by project

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.10"
	id("io.spring.dependency-management") version "1.1.7"
}

allprojects {
	group = groupName
	version = currentVersion

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java-library")
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-allopen")
	apply(plugin = "kotlin-spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")

		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
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
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("org.springframework.context.annotation.Configuration")
	annotation("org.springframework.boot.autoconfigure.SpringBootApplication")
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

