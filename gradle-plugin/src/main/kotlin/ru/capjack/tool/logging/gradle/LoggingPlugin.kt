package ru.capjack.tool.logging.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

open class LoggingPlugin : Plugin<Project> {
	companion object {
		const val NAME = "tool-logging"
		const val ARTIFACT_GROUP = "ru.capjack.tool"
		const val ARTIFACT_NAME = "tool-logging-gradle"
		
		val VERSION = this::class.java.classLoader.getResource("$NAME-version").readText()
	}
	
	override fun apply(project: Project) {
		configureDefaultVersionsResolutionStrategy(project)
	}
	
	private fun configureDefaultVersionsResolutionStrategy(project: Project) {
		project.configurations.all {
			resolutionStrategy.eachDependency(Action {
				if (requested.group == ARTIFACT_GROUP && requested.name.startsWith(NAME) && requested.version.isNullOrEmpty()) {
					useVersion(VERSION)
				}
			})
		}
	}
}
