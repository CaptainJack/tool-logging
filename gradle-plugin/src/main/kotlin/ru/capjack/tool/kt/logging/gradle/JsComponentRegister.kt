package ru.capjack.tool.kt.logging.gradle

import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension

class JsComponentRegister : ComponentRegistrar {
	
	override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
		JsSyntheticTranslateExtension.registerExtension(project, JsExtension())
	}
}