package ru.capjack.lib.kt.logging.js.gradle

import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration

class CommandLineProcessor : CommandLineProcessor {
	override val pluginId = Const.PLUGIN
	
	override val pluginOptions: Collection<CliOption> = emptyList()
	
	override fun processOption(option: CliOption, value: String, configuration: CompilerConfiguration) {}
}