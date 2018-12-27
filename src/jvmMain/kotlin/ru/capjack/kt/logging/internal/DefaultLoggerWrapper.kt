package ru.capjack.kt.logging.internal

import org.slf4j.Logger

internal class DefaultLoggerWrapper(logger: Logger) : AbstractLoggerWrapper<Logger>(logger) {
	override fun error(message: String) {
		logger.error(message)
	}
	
	override fun error(message: String, t: Throwable) {
		logger.error(message, t)
	}
	
	override fun warn(message: String) {
		logger.warn(message)
	}
	
	override fun warn(message: String, t: Throwable) {
		logger.warn(message, t)
	}
	
	override fun info(message: String) {
		logger.info(message)
	}
	
	override fun info(message: String, t: Throwable) {
		logger.info(message, t)
	}
	
	override fun debug(message: String) {
		logger.debug(message)
	}
	
	override fun debug(message: String, t: Throwable) {
		logger.debug(message, t)
	}
	
	override fun trace(message: String) {
		logger.trace(message)
	}
	
	override fun trace(message: String, t: Throwable) {
		logger.trace(message, t)
	}
}