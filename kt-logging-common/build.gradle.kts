plugins {
	kotlin("platform.common")
	id("io.freefair.sources-jar")
	id("ru.capjack.capjack-publish")
}

dependencies {
	implementation(kotlin("stdlib-common"))
}