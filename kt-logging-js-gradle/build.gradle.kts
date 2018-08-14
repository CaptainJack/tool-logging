import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	`java-gradle-plugin`
	`maven-publish`
	id("com.gradle.plugin-publish") version "0.10.0"
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	compileOnly(kotlin("gradle-plugin"))
	compileOnly(kotlin("compiler-embeddable"))
}

JavaVersion.VERSION_1_8.also {
	configure<JavaPluginConvention> { sourceCompatibility = it }
	tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = it.toString() }
}

gradlePlugin {
	(plugins) {
		"KtLoggingJs" {
			id = "ru.capjack.kt-logging-js"
			implementationClass = "ru.capjack.kt.logging.js.gradle.LoggingPlugin"
		}
	}
}

pluginBundle {
	vcsUrl = "https://github.com/CaptainJack/kt-logging"
	website = vcsUrl
	description = "Kotlin compiler plugin for support kt-logging-js library"
	tags = listOf("kotlin", "javascript", "logging")
	
	plugins["KtLoggingJs"].displayName = "KtLoggingJs plugin"
}

rootProject.tasks["postRelease"].dependsOn(tasks["publishPlugins"])

tasks.withType<ProcessResources> {
	inputs.property("version", version.toString())
	expand(project.properties)
}

