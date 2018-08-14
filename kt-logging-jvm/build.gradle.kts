import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("platform.jvm")
	id("io.freefair.sources-jar")
	id("ru.capjack.capjack-publish")
	`java-library`
}

dependencies {
	expectedBy(project(":kt-logging-common"))
	
	implementation(kotlin("stdlib-jdk8"))
	api("org.slf4j:slf4j-api:1.7.+")
}

JavaVersion.VERSION_1_8.also {
	configure<JavaPluginConvention> { sourceCompatibility = it }
	tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = it.toString() }
}