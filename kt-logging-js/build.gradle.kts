import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

plugins {
	kotlin("platform.js")
	id("io.freefair.sources-jar")
	id("ru.capjack.capjack-bintray")
	id("ru.capjack.ktjs-test") version "0.1.0"
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

evaluationDependsOn(":kt-logging-js-gradle")

tasks.getByName<KotlinJsCompile>("compileTestKotlin2Js") {
	val pluginJar = project(":kt-logging-js-gradle").tasks.getByName<Jar>("jar")
	dependsOn(pluginJar)
	kotlinOptions.freeCompilerArgs += listOf("-Xplugin=${pluginJar.archivePath.absolutePath}")
}