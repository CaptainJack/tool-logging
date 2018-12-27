package ru.capjack.kt.logging

interface Logger {
	val errorEnabled: Boolean
	val warnEnabled: Boolean
	val infoEnabled: Boolean
	val debugEnabled: Boolean
	val traceEnabled: Boolean
	
	fun error(message: String)
	fun error(message: String, t: Throwable)
	
	fun warn(message: String)
	fun warn(message: String, t: Throwable)
	
	fun info(message: String)
	fun info(message: String, t: Throwable)
	
	fun debug(message: String)
	fun debug(message: String, t: Throwable)
	
	fun trace(message: String)
	fun trace(message: String, t: Throwable)
	
	override fun toString(): String
}
