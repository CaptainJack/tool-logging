package ru.capjack.tool.logging

import kotlin.reflect.KClass

actual object Logging {
	private val output = ProxyOutput(ConsoleOutput(ConsoleOutputFormatter()))
	private var rootLevel: Level = Level.INFO
	private var loggers: MutableMap<String, DefaultLogger> = mutableMapOf()
	private var levels: MutableMap<String, Level> = mutableMapOf()
	
	actual fun getLogger(name: String): Logger {
		return loggers.getOrPut(name) {
			DefaultLogger(name, defineLevel(name), output)
		}
	}
	
	actual fun getLogger(clazz: KClass<out Any>): Logger {
		val pkg = (clazz.js.asDynamic().`$metadata$`.`package` as String?)?.let { "$it." } ?: ""
		return getLogger(pkg + (clazz.simpleName ?: clazz.js.name))
	}
	
	fun setOutput(value: Output) {
		output.target = value
	}
	
	fun setLevel(value: Level) {
		rootLevel = value
	}
	
	fun setLevel(name: String, level: Level) {
		levels[name] = level
		val path = "$name."
		for (logger in loggers.values) {
			if (logger.name == name || level.name.startsWith(path)) {
				logger.level = level
			}
		}
	}
	
	private fun defineLevel(name: String): Level {
		var n = name
		
		while (true) {
			val level = levels[n]
			if (level != null) {
				return level
			}
			val i = n.lastIndexOf('.')
			if (i <= 0) {
				break
			}
			n = n.substring(0, i - 1)
		}
		
		return rootLevel
	}
}