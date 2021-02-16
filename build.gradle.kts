plugins {
	kotlin("multiplatform") version "1.4.30"
	id("nebula.release") version "15.3.1"
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
		compilations.all { kotlinOptions.jvmTarget = "11" }
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
			implementation("org.slf4j:slf4j-api:1.7.30")
		}
		get("jvmTest").dependencies {
			implementation(kotlin("test-junit"))
			implementation("ch.qos.logback:logback-classic:1.2.3")
		}
		
		get("jsMain").dependencies {
			implementation("ru.capjack.tool:tool-lang:1.8.0")
		}
		get("jsTest").dependencies {
			implementation(kotlin("test-js"))
		}
	}
}
