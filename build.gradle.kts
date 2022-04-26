plugins {
	kotlin("multiplatform") version "1.6.21"
	id("ru.capjack.publisher") version "1.0.0"
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
			implementation("org.slf4j:slf4j-api:1.7.36")
		}
		get("jvmTest").dependencies {
			implementation("ch.qos.logback:logback-classic:1.2.11")
		}
		
		get("jsMain").dependencies {
			implementation("ru.capjack.tool:tool-lang:1.12.0")
		}
	}
}
