plugins {
	kotlin("multiplatform") version "1.8.21"
	id("ru.capjack.publisher") version "1.1.0"
}

group = "ru.capjack.tool"

repositories {
	mavenCentral()
	mavenCapjack()
}

kotlin {
	jvm {
		jvmToolchain(17)
	}
	js(IR) {
		browser()
	}
	
	sourceSets {
		get("commonTest").dependencies {
			implementation(kotlin("test"))
		}
		
		get("jvmMain").dependencies {
			implementation("org.slf4j:slf4j-api:2.0.7")
		}
		get("jvmTest").dependencies {
			implementation("ch.qos.logback:logback-classic:1.4.7")
		}
		
		get("jsMain").dependencies {
			implementation("ru.capjack.tool:tool-lang:1.14.0")
		}
	}
}
