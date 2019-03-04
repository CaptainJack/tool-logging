package ru.capjack.tool.kt.logging

inline fun <reified T : Any> Logging.getLogger(): Logger {
	return getLogger(T::class)
}