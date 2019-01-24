package ru.capjack.kt.logging.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

open class LoggingPlugin : Plugin<Project> {
	companion object {
		const val ARTIFACT_GROUP = "ru.capjack.kt.logging"
		const val ARTIFACT_NAME = "kt-logging-gradle"
		
		val VERSION = this::class.java.classLoader.getResource("kt-logging-version").readText()
	}
	
	override fun apply(project: Project) {
		configureDefaultVersionsResolutionStrategy(project)
	}
	
	private fun configureDefaultVersionsResolutionStrategy(project: Project) {
		project.configurations.forEach { configuration ->
			configuration.resolutionStrategy.eachDependency(Action {
				if (requested.group == ARTIFACT_GROUP && requested.version.isNullOrEmpty()) {
					useVersion(VERSION)
				}
			})
		}
	}
}
