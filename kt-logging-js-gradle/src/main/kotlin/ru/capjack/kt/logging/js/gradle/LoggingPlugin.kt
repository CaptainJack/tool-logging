package ru.capjack.kt.logging.js.gradle

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
		project.configurations.all { configuration ->
			configuration.resolutionStrategy.eachDependency { details ->
				val requested = details.requested
				if (requested.group == "ru.capjack.lib" && requested.name == "lib-kt-logging-js" && requested.version.isEmpty()) {
					details.useVersion(VERSION)
				}
			}
		}
	}
}
