package ru.capjack.tool.logging

interface Logger {
	val name: String
	
	val errorEnabled: Boolean
	val warnEnabled: Boolean
	val infoEnabled: Boolean
	val debugEnabled: Boolean
	val traceEnabled: Boolean
	
	fun isEnabled(level: Level): Boolean
	
	fun error(message: String)
	fun error(message: String, error: Throwable)
	
	fun warn(message: String)
	fun warn(message: String, error: Throwable)
	
	fun info(message: String)
	fun info(message: String, error: Throwable)
	
	fun debug(message: String)
	fun debug(message: String, error: Throwable)
	
	fun trace(message: String)
	fun trace(message: String, error: Throwable)
	
	fun log(level: Level, message: String)
	fun log(level: Level, message: String, error: Throwable)
}
