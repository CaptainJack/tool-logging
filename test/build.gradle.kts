import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	application
}

dependencies {
	implementation(project(":kt-logging-jvm"))
	implementation(kotlin("stdlib-jdk8"))
	implementation("ch.qos.logback:logback-classic:1.2.+")
}

JavaVersion.VERSION_1_8.also {
	configure<JavaPluginConvention> { sourceCompatibility = it }
	tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = it.toString() }
}

application {
	mainClassName = "MainKt"
}