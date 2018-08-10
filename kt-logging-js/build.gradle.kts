plugins {
	kotlin("platform.js")
	id("ru.capjack.kotlin-sources-jar")
	id("ru.capjack.capjack-publish")
}

dependencies {
	expectedBy(project(":kt-logging-common"))
	
	implementation(kotlin("stdlib-js"))
}