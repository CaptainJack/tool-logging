package ru.capjack.tool.logging.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.capjack.gradle.depver.DepverExtension

@Suppress("UnstableApiUsage")
open class LoggingPlugin : Plugin<Project> {
	companion object {
		const val NAME = "tool-logging"
		const val ARTIFACT_GROUP = "ru.capjack.tool"
		const val ARTIFACT_NAME = "tool-logging-gradle"
		
		val VERSION = this::class.java.classLoader.getResource("$NAME-version")!!.readText()
	}
	
	override fun apply(project: Project) {
		(project.extensions.findByName("depver") as? DepverExtension)?.set(ARTIFACT_GROUP, NAME, VERSION)
	}
}
