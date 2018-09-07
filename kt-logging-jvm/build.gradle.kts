import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("platform.jvm")
	id("io.freefair.sources-jar")
	id("ru.capjack.capjack-bintray")
	`java-library`
}

dependencies {
	expectedBy(project(":kt-logging-common"))
	
	api("org.slf4j:slf4j-api:1.7.+")
	
	implementation(kotlin("stdlib-jdk8"))
	
	testImplementation(kotlin("test-junit"))
	testImplementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }