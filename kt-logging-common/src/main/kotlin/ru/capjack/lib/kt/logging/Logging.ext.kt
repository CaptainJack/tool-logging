package ru.capjack.lib.kt.logging

inline fun <reified T : Any> Logging.getLogger(): Logger {
	return getLogger(T::class)
}