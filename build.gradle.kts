plugins {
	kotlin("multiplatform") version "1.3.50"
	id("nebula.release") version "11.1.0"
	id("ru.capjack.bintray") version "1.0.0"
}

allprojects {
	group = "ru.capjack.tool"
	repositories {
		jcenter()
		maven("https://dl.bintray.com/capjack/public")
	}
}


kotlin {
	jvm {
		compilations.all { kotlinOptions.jvmTarget = "1.8" }
	}
	js {
		browser()
		compilations["main"].kotlinOptions {
			sourceMap = true
			sourceMapEmbedSources = "always"
		}
		
		compilations["test"].compileKotlinTask.apply {
			evaluationDependsOn(":tool-logging-gradle")
			val jar = project(":tool-logging-gradle").tasks.getByName<Jar>("jar")
			dependsOn(jar)
			kotlinOptions.freeCompilerArgs += "-Xplugin=${jar.archiveFile.get().asFile.absolutePath}"
		}
	}
	
	sourceSets {
		get("commonMain").dependencies {
			implementation(kotlin("stdlib-common"))
		}
		get("commonTest").dependencies {
			implementation(kotlin("test-common"))
			implementation(kotlin("test-annotations-common"))
		}
		
		get("jvmMain").dependencies {
			implementation(kotlin("stdlib-jdk8"))
			implementation("org.slf4j:slf4j-api:1.7.26")
		}
		get("jvmTest").dependencies {
			implementation(kotlin("test-junit"))
			implementation("ch.qos.logback:logback-classic:1.2.3")
		}
		
		get("jsMain").dependencies {
			implementation(kotlin("stdlib-js"))
			implementation("ru.capjack.tool:tool-lang:0.6.1")
		}
		get("jsTest").dependencies {
			implementation(kotlin("test-js"))
		}
	}
}
