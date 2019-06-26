plugins {
	kotlin("multiplatform") version "1.3.40"
	id("nebula.release") version "10.1.2"
	id("ru.capjack.bintray") version "0.18.1"
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
		commonMain.get().dependencies {
			implementation(kotlin("stdlib-common"))
		}
		commonTest.get().dependencies {
			implementation(kotlin("test-common"))
			implementation(kotlin("test-annotations-common"))
		}
	}
	
	jvm().compilations {
		get("main").defaultSourceSet.dependencies {
			implementation(kotlin("stdlib-jdk8"))
			api("org.slf4j:slf4j-api:1.7.+")
			
		}
		get("test").defaultSourceSet.dependencies {
			implementation(kotlin("test-junit"))
			implementation("ch.qos.logback:logback-classic:1.2.3")
		}
	}
	
	js().compilations {
		get("main").defaultSourceSet.dependencies {
			implementation(kotlin("stdlib-js"))
			implementation("ru.capjack.tool:tool-lang:0.4.2")
		}
		get("test").defaultSourceSet.dependencies {
			implementation(kotlin("test-js"))
		}
		
		get("test").compileKotlinTask.apply {
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