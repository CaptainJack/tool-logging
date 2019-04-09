package ru.capjack.tool.logging

import org.slf4j.spi.LocationAwareLogger

class AwareLoggerWrapper(logger: LocationAwareLogger) : AbstractLoggerWrapper<LocationAwareLogger>(logger) {
	private val fqcn = this::class.java.name
	
	override fun error(message: String) {
		log(LocationAwareLogger.ERROR_INT, message)
	}
	
	override fun error(message: String, error: Throwable) {
		log(LocationAwareLogger.ERROR_INT, message, error)
	}
	
	override fun warn(message: String) {
		log(LocationAwareLogger.WARN_INT, message)
	}
	
	override fun warn(message: String, error: Throwable) {
		log(LocationAwareLogger.WARN_INT, message, error)
	}
	
	override fun info(message: String) {
		log(LocationAwareLogger.INFO_INT, message)
	}
	
	override fun info(message: String, error: Throwable) {
		log(LocationAwareLogger.INFO_INT, message, error)
	}
	
	override fun debug(message: String) {
		log(LocationAwareLogger.DEBUG_INT, message)
	}
	
	override fun debug(message: String, error: Throwable) {
		log(LocationAwareLogger.DEBUG_INT, message, error)
	}
	
	override fun trace(message: String) {
		log(LocationAwareLogger.TRACE_INT, message)
	}
	
	override fun trace(message: String, error: Throwable) {
		log(LocationAwareLogger.TRACE_INT, message, error)
	}
	
	override fun log(level: Level, message: String) {
		log(level.laLevel, message)
	}
	
	override fun log(level: Level, message: String, error: Throwable) {
		log(level.laLevel, message, error)
	}
	
	private fun log(level: Int, message: String, t: Throwable? = null) {
		logger.log(null, fqcn, level, message, null, t)
	}
	
	private val Level.laLevel: Int
		get() = when (this) {
			Level.ERROR -> LocationAwareLogger.ERROR_INT
			Level.WARN  -> LocationAwareLogger.WARN_INT
			Level.INFO  -> LocationAwareLogger.INFO_INT
			Level.DEBUG -> LocationAwareLogger.DEBUG_INT
			Level.TRACE -> LocationAwareLogger.TRACE_INT
		}
	
}
