import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
	kotlin("multiplatform") version "1.3.11"
	id("ru.capjack.ktjs-test") version "0.8.0"
	id("nebula.release") version "9.1.2"
	id("ru.capjack.capjack-bintray") version "0.12.0"
}

allprojects {
	repositories {
		jcenter()
	}
}

evaluationDependsOn(":kt-logging-js-gradle")

kotlin {
	targets {
		add(presets["jvm"].createTarget("jvm").apply {
			compilations.all {
				tasks.getByName<KotlinJvmCompile>(compileKotlinTaskName).kotlinOptions.jvmTarget = "1.8"
			}
		})
		
		add(presets["js"].createTarget("js").apply {
			compilations.all {
				tasks.getByName<KotlinJsCompile>(compileKotlinTaskName).kotlinOptions {
					sourceMap = true
					sourceMapEmbedSources = "always"
					moduleKind = "umd"
				}
				tasks.getByName<KotlinJsCompile>("compileTestKotlinJs") {
					val pluginJar = project(":kt-logging-js-gradle").tasks.getByName<Jar>("jar")
					dependsOn(pluginJar)
					kotlinOptions.freeCompilerArgs += listOf("-Xplugin=${pluginJar.archivePath.absolutePath}")
				}
			}
		})
	}
	
	sourceSets {
		commonMain {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
			}
		}
		commonTest {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-test-common")
				implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
			}
		}
		
		named("jvmMain") {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
				api("org.slf4j:slf4j-api:1.7.+")
			}
		}
		named("jvmTest") {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-test-junit")
				implementation("ch.qos.logback:logback-classic:1.2.3")
			}
		}
		
		named("jsMain") {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
			}
		}
		named("jsTest") {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-test-js")
			}
		}
	}
}