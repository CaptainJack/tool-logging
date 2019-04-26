package ru.capjack.tool.logging

class PrefixedLogger(private val target: Logger, private val prefix: String) : Logger by target {
	override fun error(message: String) {
		target.error(prefix(message))
	}
	
	override fun error(message: String, error: Throwable) {
		target.error(prefix(message), error)
	}
	
	override fun warn(message: String) {
		target.warn(prefix(message))
	}
	
	override fun warn(message: String, error: Throwable) {
		target.warn(prefix(message), error)
	}
	
	override fun info(message: String) {
		target.info(prefix(message))
	}
	
	override fun info(message: String, error: Throwable) {
		target.info(prefix(message), error)
	}
	
	override fun debug(message: String) {
		target.debug(prefix(message))
	}
	
	override fun debug(message: String, error: Throwable) {
		target.debug(prefix(message), error)
	}
	
	override fun trace(message: String) {
		target.trace(prefix(message))
	}
	
	override fun trace(message: String, error: Throwable) {
		target.trace(prefix(message), error)
	}
	
	override fun log(level: Level, message: String) {
		target.log(level, prefix(message))
	}
	
	override fun log(level: Level, message: String, error: Throwable) {
		target.log(level, prefix(message), error)
	}
	
	private fun prefix(message: String): String {
		return prefix + message
	}
}
