package ru.capjack.lib.kt.logging.internal

import ru.capjack.lib.kt.logging.Logger
import org.slf4j.Logger as Slf4jLogger

abstract class AbstractLoggerWrapper<L : Slf4jLogger>(protected val logger: L) : Logger {
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
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false
		
		other as AbstractLoggerWrapper<*>
		
		if (logger != other.logger) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		return logger.hashCode()
	}
}
