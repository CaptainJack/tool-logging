plugins {
	kotlin("multiplatform") version "1.5.0"
	id("ru.capjack.publisher") version "0.1.0"
}

group = "ru.capjack.tool"

repositories {
	mavenCentral()
	mavenCapjack()
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
			implementation(kotlin("test"))
		}
		
		get("jvmMain").dependencies {
			implementation("org.slf4j:slf4j-api:1.7.30")
		}
		get("jvmTest").dependencies {
			implementation("ch.qos.logback:logback-classic:1.2.3")
		}
		
		get("jsMain").dependencies {
			implementation("ru.capjack.tool:tool-lang:1.11.1")
		}
	}
}
