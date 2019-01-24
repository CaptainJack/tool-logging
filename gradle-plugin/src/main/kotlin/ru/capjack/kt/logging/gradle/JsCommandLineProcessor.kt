package ru.capjack.kt.logging.gradle

import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor

class JsCommandLineProcessor : CommandLineProcessor {
	override val pluginId = JsSubplugin.COMPILER_PLUGIN_ID
	
	override val pluginOptions: Collection<CliOption> = emptyList()
}