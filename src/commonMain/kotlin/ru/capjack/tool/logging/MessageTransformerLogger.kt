package ru.capjack.tool.logging

abstract class MessageTransformerLogger(private val target: Logger) : Logger by target {
	override fun error(message: String) {
		log(Level.ERROR, message)
	}
	
	override fun error(message: String, error: Throwable) {
		log(Level.ERROR, message, error)
	}
	
	override fun warn(message: String) {
		log(Level.WARN, message)
	}
	
	override fun warn(message: String, error: Throwable) {
		log(Level.WARN, message, error)
	}
	
	override fun info(message: String) {
		log(Level.INFO, message)
	}
	
	override fun info(message: String, error: Throwable) {
		log(Level.INFO, message, error)
	}
	
	override fun debug(message: String) {
		log(Level.DEBUG, message)
	}
	
	override fun debug(message: String, error: Throwable) {
		log(Level.DEBUG, message, error)
	}
	
	override fun trace(message: String) {
		log(Level.TRACE, message)
	}
	
	override fun trace(message: String, error: Throwable) {
		log(Level.TRACE, message, error)
	}
	
	override fun log(level: Level, message: String) {
		if (isEnabled(level)) {
			target.log(level, transform(message))
		}
	}
	
	override fun log(level: Level, message: String, error: Throwable) {
		if (isEnabled(level)) {
			target.log(level, transform(message), error)
		}
	}
	
	protected abstract fun transform(message: String): String
}
