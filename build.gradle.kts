import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

plugins {
	kotlin("multiplatform") version "1.3.20"
	id("ru.capjack.ktjs-test") version "0.8.0"
	id("nebula.release") version "9.2.0"
	id("ru.capjack.capjack-bintray") version "0.15.0"
}

allprojects {
	group = "ru.capjack.kt.logging"
	repositories {
		jcenter()
	}
}

capjackBintray {
	publications(":", ":kt-logging-gradle")
}

kotlin {
	jvm {
		compilations.all {
			kotlinOptions {
				jvmTarget = "1.8"
			}
		}
	}
	
	js {
		compilations.all {
			kotlinOptions {
				sourceMap = true
				sourceMapEmbedSources = "always"
				moduleKind = "umd"
			}
		}
		
		compilations.getByName(KotlinCompilation.TEST_COMPILATION_NAME) {
			tasks.getByName<KotlinJsCompile>(compileKotlinTaskName) {
				val plugin = ":kt-logging-gradle"
				evaluationDependsOn(plugin)
				val jar = project(plugin).tasks.getByName<Jar>("jar")
				dependsOn(jar)
				kotlinOptions.freeCompilerArgs += listOf(
					"-Xplugin=${jar.archiveFile.get().asFile.absolutePath}"
				)
			}
		}
	}
	
	sourceSets {
		commonMain {
			dependencies {
				implementation(kotlin("stdlib-common"))
			}
		}
		commonTest {
			dependencies {
				implementation(kotlin("test-common"))
				implementation(kotlin("test-annotations-common"))
			}
		}
		
		jvm {
			compilations[KotlinCompilation.MAIN_COMPILATION_NAME].defaultSourceSet {
				dependencies {
					implementation(kotlin("stdlib-jdk8"))
					api("org.slf4j:slf4j-api:1.7.+")
				}
			}
			
			compilations[KotlinCompilation.TEST_COMPILATION_NAME].defaultSourceSet {
				dependencies {
					implementation(kotlin("test-junit"))
					implementation("ch.qos.logback:logback-classic:1.2.3")
				}
			}
		}
		
		js {
			compilations[KotlinCompilation.MAIN_COMPILATION_NAME].defaultSourceSet {
				dependencies {
					implementation(kotlin("stdlib-js"))
				}
			}
			
			compilations[KotlinCompilation.TEST_COMPILATION_NAME].defaultSourceSet {
				dependencies {
					implementation(kotlin("test-js"))
				}
			}
			
		}
	}
}