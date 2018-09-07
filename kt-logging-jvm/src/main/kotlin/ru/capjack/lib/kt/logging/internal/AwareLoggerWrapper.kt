package ru.capjack.lib.kt.logging.internal

import org.slf4j.spi.LocationAwareLogger

internal class AwareLoggerWrapper(logger: LocationAwareLogger) : AbstractLoggerWrapper<LocationAwareLogger>(logger) {
	private val fqcn = this::class.java.name
	
	override fun error(message: String) {
		log(LocationAwareLogger.ERROR_INT, message)
	}
	
	override fun error(message: String, t: Throwable) {
		log(LocationAwareLogger.ERROR_INT, message, t)
	}
	
	override fun warn(message: String) {
		log(LocationAwareLogger.WARN_INT, message)
	}
	
	override fun warn(message: String, t: Throwable) {
		log(LocationAwareLogger.WARN_INT, message, t)
	}
	
	override fun info(message: String) {
		log(LocationAwareLogger.INFO_INT, message)
	}
	
	override fun info(message: String, t: Throwable) {
		log(LocationAwareLogger.INFO_INT, message, t)
	}
	
	override fun debug(message: String) {
		log(LocationAwareLogger.DEBUG_INT, message)
	}
	
	override fun debug(message: String, t: Throwable) {
		log(LocationAwareLogger.DEBUG_INT, message, t)
	}
	
	override fun trace(message: String) {
		log(LocationAwareLogger.TRACE_INT, message)
	}
	
	override fun trace(message: String, t: Throwable) {
		log(LocationAwareLogger.TRACE_INT, message, t)
	}
	
	private fun log(level: Int, message: String, t: Throwable? = null) {
		logger.log(null, fqcn, level, message, null, t)
	}
}