plugins {
	`kotlin-dsl`
	`java-gradle-plugin`
	`maven-publish`
	id("com.gradle.plugin-publish") version "0.10.0"
}

dependencies {
	compileOnly(kotlin("gradle-plugin"))
	compileOnly(kotlin("compiler-embeddable"))
}

gradlePlugin {
	plugins.create("KtLogging") {
		id = "ru.capjack.kt-logging"
		implementationClass = "ru.capjack.tool.kt.logging.gradle.LoggingPlugin"
		displayName = "kt-logging"
	}
}

pluginBundle {
	vcsUrl = "https://github.com/CaptainJack/kt-logging"
	website = vcsUrl
	description = "Plugin for support kt-logging library"
	tags = listOf("capjack", "kotlin", "logging")
}

rootProject.tasks["postRelease"].dependsOn(tasks["publishPlugins"])

tasks.withType<ProcessResources> {
	inputs.property("version", version.toString())
	expand(project.properties)
}