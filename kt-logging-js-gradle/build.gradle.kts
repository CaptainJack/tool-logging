import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	`kotlin-dsl`
	`java-gradle-plugin`
	`maven-publish`
	id("com.gradle.plugin-publish") version "0.10.0"
	id("io.freefair.sources-jar")
	id("ru.capjack.capjack-bintray")
}

dependencies {
	compileOnly(kotlin("gradle-plugin"))
	compileOnly(kotlin("compiler-embeddable"))
}

gradlePlugin {
	plugins.create("KtLoggingJs") {
		id = "ru.capjack.kt-logging-js"
		implementationClass = "ru.capjack.kt.logging.js.gradle.LoggingPlugin"
		displayName = "Lib KtLoggingJs"
	}
}

pluginBundle {
	vcsUrl = "https://github.com/CaptainJack/kt-logging"
	website = vcsUrl
	description = "Kotlin compiler plugin for support kt-logging-js library"
	tags = listOf("kotlin", "javascript", "logging")
}

rootProject.tasks["postRelease"].dependsOn(tasks["publishPlugins"])

tasks.withType<ProcessResources> {
	inputs.property("version", version.toString())
	expand(project.properties)
}

