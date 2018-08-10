plugins {
	kotlin("platform.js")
	id("ru.capjack.kotlin-sources-jar")
}

dependencies {
	expectedBy(project(":kt-logging-common"))
	
	implementation(kotlin("stdlib-js"))
}