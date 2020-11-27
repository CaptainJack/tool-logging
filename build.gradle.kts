plugins {
	kotlin("multiplatform") version "1.4.20"
	id("nebula.release") version "15.3.0"
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
	js(IR) {
		browser()
	}
	
	sourceSets {
		get("commonTest").dependencies {
			implementation(kotlin("test-common"))
			implementation(kotlin("test-annotations-common"))
		}
		
		get("jvmMain").dependencies {
			implementation("org.slf4j:slf4j-api:1.7.26")
		}
		get("jvmTest").dependencies {
			implementation(kotlin("test-junit"))
			implementation("ch.qos.logback:logback-classic:1.2.3")
		}
		
		get("jsMain").dependencies {
			implementation("ru.capjack.tool:tool-lang:1.6.1")
		}
		get("jsTest").dependencies {
			implementation(kotlin("test-js"))
		}
	}
}
