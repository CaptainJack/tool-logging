package ru.capjack.kt.logging.js.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.compile.AbstractCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption
import ru.capjack.kt.logging.js.gradle.compiler.Const

class LoggingSubplugin : KotlinGradleSubplugin<AbstractCompile> {
	
	override fun isApplicable(project: Project, task: AbstractCompile): Boolean {
		return LoggingPlugin.isEnabled(project)
	}
	
	override fun getCompilerPluginId(): String {
		return Const.PLUGIN_ID
	}
	
	override fun getPluginArtifact(): SubpluginArtifact {
		return SubpluginArtifact("ru.capjack.gradle", "gradle-kt-logging-js", LoggingPlugin.VERSION)
	}
	
	override fun apply(
		project: Project,
		kotlinCompile: AbstractCompile,
		javaCompile: AbstractCompile,
		variantData: Any?,
		androidProjectHandler: Any?,
		javaSourceSet: SourceSet?
	): List<SubpluginOption> {
		return emptyList()
	}
}
