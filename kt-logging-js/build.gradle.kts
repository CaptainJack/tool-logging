import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

plugins {
	kotlin("platform.js")
	id("io.freefair.sources-jar")
	id("ru.capjack.capjack-bintray")
}

dependencies {
	expectedBy(project(":kt-logging-common"))
	
	implementation(kotlin("stdlib-js"))
	
	testImplementation(kotlin("test-js"))
}

tasks.withType<KotlinJsCompile> {
	kotlinOptions {
		sourceMap = true
		sourceMapEmbedSources = "always"
		moduleKind = "umd"
	}
}