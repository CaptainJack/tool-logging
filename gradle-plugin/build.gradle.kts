plugins {
	`kotlin-dsl`
	`java-gradle-plugin`
	`maven-publish`
	id("com.gradle.plugin-publish") version "0.10.1"
	id("ru.capjack.bintray")
}

repositories {
	gradlePluginPortal()
}

dependencies {
	compileOnly(kotlin("gradle-plugin"))
	compileOnly(kotlin("compiler-embeddable"))
	
	implementation("ru.capjack.gradle:gradle-depver:0.1.0")
}

gradlePlugin {
	plugins.create("Logging") {
		id = "ru.capjack.logging"
		implementationClass = "ru.capjack.tool.logging.gradle.LoggingPlugin"
		displayName = "ru.capjack.logging"
	}
}

pluginBundle {
	vcsUrl = "https://github.com/CaptainJack/tool-logging"
	website = vcsUrl
	description = "Plugin for support tool-logging library"
	tags = listOf("capjack", "kotlin", "logging")
}

rootProject.tasks["postRelease"].dependsOn(tasks["publishPlugins"])

tasks.withType<ProcessResources> {
	inputs.property("version", version.toString())
	expand(project.properties)
}