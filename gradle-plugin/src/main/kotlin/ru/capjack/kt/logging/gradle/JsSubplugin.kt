package ru.capjack.kt.logging.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class JsSubplugin : KotlinGradleSubplugin<AbstractCompile> {
	companion object {
		const val COMPILER_PLUGIN_ID = "ru.capjack.kt-logging-js"
	}
	
	override fun isApplicable(project: Project, task: AbstractCompile): Boolean {
		return project.plugins.run {
			hasPlugin("kotlin2js") || hasPlugin("org.jetbrains.kotlin.multiplatform")
		}
	}
	
	override fun apply(
		project: Project,
		kotlinCompile: AbstractCompile,
		javaCompile: AbstractCompile?,
		variantData: Any?,
		androidProjectHandler: Any?,
		kotlinCompilation: KotlinCompilation<KotlinCommonOptions>?
	): List<SubpluginOption> {
		return emptyList()
	}
	
	override fun getCompilerPluginId(): String {
		return COMPILER_PLUGIN_ID
	}
	
	override fun getPluginArtifact(): SubpluginArtifact {
		return SubpluginArtifact(
			LoggingPlugin.ARTIFACT_GROUP,
			LoggingPlugin.ARTIFACT_NAME,
			LoggingPlugin.VERSION
		)
	}
}
