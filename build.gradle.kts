import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants.MODULE_UMD
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation.Companion.MAIN_COMPILATION_NAME
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation.Companion.TEST_COMPILATION_NAME

plugins {
	kotlin("multiplatform") version "1.3.21"
	id("nebula.release") version "10.0.1"
	id("ru.capjack.bintray") version "0.17.0"
	id("ru.capjack.kojste") version "0.11.0"
}

allprojects {
	group = "ru.capjack.tool"
	repositories {
		jcenter()
		maven("https://dl.bintray.com/capjack/public")
	}
}

capjackBintray {
	publications(":", ":tool-logging-gradle")
}

kotlin {
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
	}
	
	jvm().compilations {
		all {
			kotlinOptions.jvmTarget = "1.8"
		}
		
		get(MAIN_COMPILATION_NAME).defaultSourceSet {
			dependencies {
				implementation(kotlin("stdlib-jdk8"))
				api("org.slf4j:slf4j-api:1.7.+")
			}
		}
		
		get(TEST_COMPILATION_NAME).defaultSourceSet {
			dependencies {
				implementation(kotlin("test-junit"))
				implementation("ch.qos.logback:logback-classic:1.2.3")
			}
		}
	}
	
	js().compilations {
		all {
			kotlinOptions.moduleKind = MODULE_UMD
		}
		
		get(MAIN_COMPILATION_NAME).defaultSourceSet {
			dependencies {
				implementation(kotlin("stdlib-js"))
				implementation("ru.capjack.tool:tool-lang-js:0.2.0-dev.2+396902a")
			}
		}
		
		get(TEST_COMPILATION_NAME).defaultSourceSet {
			dependencies {
				implementation(kotlin("test-js"))
			}
		}
		
		get(TEST_COMPILATION_NAME).compileKotlinTask.apply {
			val plugin = ":tool-logging-gradle"
			evaluationDependsOn(plugin)
			val jar = project(plugin).tasks.getByName<Jar>("jar")
			dependsOn(jar)
			kotlinOptions.freeCompilerArgs += listOf(
				"-Xplugin=${jar.archiveFile.get().asFile.absolutePath}"
			)
		}
	}
}