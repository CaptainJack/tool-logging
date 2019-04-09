package ru.capjack.tool.logging

import org.slf4j.Logger

class DefaultLoggerWrapper(logger: Logger) : AbstractLoggerWrapper<Logger>(logger) {
	override fun error(message: String) {
		logger.error(message)
	}
	
	override fun error(message: String, error: Throwable) {
		logger.error(message, error)
	}
	
	override fun warn(message: String) {
		logger.warn(message)
	}
	
	override fun warn(message: String, error: Throwable) {
		logger.warn(message, error)
	}
	
	override fun info(message: String) {
		logger.info(message)
	}
	
	override fun info(message: String, error: Throwable) {
		logger.info(message, error)
	}
	
	override fun debug(message: String) {
		logger.debug(message)
	}
	
	override fun debug(message: String, error: Throwable) {
		logger.debug(message, error)
	}
	
	override fun trace(message: String) {
		logger.trace(message)
	}
	
	override fun trace(message: String, error: Throwable) {
		logger.trace(message, error)
	}
	
	override fun log(level: Level, message: String) {
		when (level) {
			Level.ERROR -> error(message)
			Level.WARN  -> warn(message)
			Level.INFO  -> info(message)
			Level.DEBUG -> debug(message)
			Level.TRACE -> trace(message)
		}
	}
	
	override fun log(level: Level, message: String, error: Throwable) {
		when (level) {
			Level.ERROR -> error(message, error)
			Level.WARN  -> warn(message, error)
			Level.INFO  -> info(message, error)
			Level.DEBUG -> debug(message, error)
			Level.TRACE -> trace(message, error)
		}
	}
}