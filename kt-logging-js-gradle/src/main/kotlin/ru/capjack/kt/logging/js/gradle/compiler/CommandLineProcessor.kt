package ru.capjack.kt.logging.js.gradle.compiler

import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration

class CommandLineProcessor : CommandLineProcessor {
	override val pluginId = Const.PLUGIN_ID
	
	override val pluginOptions: Collection<CliOption> = emptyList()
	
	override fun processOption(option: CliOption, value: String, configuration: CompilerConfiguration) {}
}