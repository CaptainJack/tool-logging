package ru.capjack.lib.kt.logging.internal

import org.slf4j.spi.LocationAwareLogger
import ru.capjack.lib.kt.logging.Logger

internal class AwareLogger(private val logger: LocationAwareLogger) : Logger {
	private val fqcn = this::class.java.name
	
	override val errorEnabled: Boolean
		get() = logger.isErrorEnabled
	
	override val warnEnabled: Boolean
		get() = logger.isWarnEnabled
	
	override val infoEnabled: Boolean
		get() = logger.isInfoEnabled
	
	override val debugEnabled: Boolean
		get() = logger.isDebugEnabled
	
	override val traceEnabled: Boolean
		get() = logger.isTraceEnabled
	
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