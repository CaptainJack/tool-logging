package ru.capjack.kt.logging.js.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

open class LoggingPlugin : Plugin<Project> {
	companion object {
		val VERSION = this::class.java.classLoader.getResource("kt-logging-version").readText()
		
		fun isEnabled(project: Project) = project.plugins.hasPlugin(LoggingPlugin::class.java)
	}
	
	override fun apply(project: Project) {
		configureDefaultVersionsResolutionStrategy(project)
	}
	
	private fun configureDefaultVersionsResolutionStrategy(project: Project) {
		project.configurations.forEach { configuration ->
			configuration.resolutionStrategy.eachDependency(Action {
				if (requested.group == Const.GROUP && requested.name == Const.ARTIFACT_LIB && requested.version.isNullOrEmpty()) {
					useVersion(VERSION)
				}
			})
		}
	}
}
