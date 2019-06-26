package ru.capjack.tool.logging

import ru.capjack.tool.logging.Level.*
import kotlin.js.Date

class DefaultLogger(
	override val name: String,
	var level: Level,
	private val output: Output

) : Logger {
	
	override val errorEnabled: Boolean
		get() = isEnabled(ERROR)
	
	override val warnEnabled: Boolean
		get() = isEnabled(WARN)
	
	override val infoEnabled: Boolean
		get() = isEnabled(INFO)
	
	override val debugEnabled: Boolean
		get() = isEnabled(DEBUG)
	
	override val traceEnabled: Boolean
		get() = isEnabled(TRACE)
	
	override fun isEnabled(level: Level): Boolean {
		return this.level.ordinal >= level.ordinal
	}
	
	override fun error(message: String) {
		log(ERROR, message)
	}
	
	override fun error(message: String, error: Throwable) {
		log(ERROR, message, error)
	}
	
	override fun warn(message: String) {
		log(WARN, message)
	}
	
	override fun warn(message: String, error: Throwable) {
		log(WARN, message, error)
	}
	
	override fun info(message: String) {
		log(INFO, message)
	}
	
	override fun info(message: String, error: Throwable) {
		log(INFO, message, error)
	}
	
	override fun debug(message: String) {
		log(DEBUG, message)
	}
	
	override fun debug(message: String, error: Throwable) {
		log(DEBUG, message, error)
	}
	
	override fun trace(message: String) {
		log(TRACE, message)
	}
	
	override fun trace(message: String, error: Throwable) {
		log(TRACE, message, error)
	}
	
	override fun log(level: Level, message: String) {
		doLog(level, message)
	}
	
	override fun log(level: Level, message: String, error: Throwable) {
		doLog(level, message, error)
	}
	
	private fun doLog(level: Level, message: String, error: Throwable? = null) {
		if (isEnabled(level)) {
			output.writeRecord(Record(Date.now(), name, level, message, error))
		}
	}
}