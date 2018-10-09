package ru.capjack.kt.logging.internal

import ru.capjack.kt.logging.Level
import ru.capjack.kt.logging.Level.*
import ru.capjack.kt.logging.Logger
import ru.capjack.kt.logging.Output
import ru.capjack.kt.logging.Record
import kotlin.browser.window

internal class DefaultLogger(
	val name: String,
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
	
	override fun error(message: String) {
		log(ERROR, message)
	}
	
	override fun error(message: String, t: Throwable) {
		log(ERROR, message, t)
	}
	
	override fun warn(message: String) {
		log(WARN, message)
	}
	
	override fun warn(message: String, t: Throwable) {
		log(WARN, message, t)
	}
	
	override fun info(message: String) {
		log(INFO, message)
	}
	
	override fun info(message: String, t: Throwable) {
		log(INFO, message, t)
	}
	
	override fun debug(message: String) {
		log(DEBUG, message)
	}
	
	override fun debug(message: String, t: Throwable) {
		log(DEBUG, message, t)
	}
	
	override fun trace(message: String) {
		log(TRACE, message)
	}
	
	override fun trace(message: String, t: Throwable) {
		log(TRACE, message, t)
	}
	
	private fun log(level: Level, message: String, t: Throwable? = null) {
		if (isEnabled(level)) {
			output.writeRecord(Record(window.performance.now(), name, level, message, t))
		}
	}
	
	private fun isEnabled(level: Level): Boolean {
		return this.level.ordinal >= level.ordinal
	}
}