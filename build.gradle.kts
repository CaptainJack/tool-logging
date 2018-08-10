import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

plugins {
	kotlin("jvm") version "1.2.60" apply false
	id("nebula.release") version "6.3.5"
	id("ru.capjack.kotlin-sources-jar") version "0.2.0" apply false
}

subprojects {
	group = "ru.capjack.lib"
	
	repositories {
		jcenter()
	}
}