package ru.capjack.tool.kt.logging

inline fun Logger.error(message: () -> String) {
	if (errorEnabled) error(message())
}

inline fun Logger.error(t: Throwable, message: () -> String) {
	if (errorEnabled) error(message(), t)
}


inline fun Logger.warn(message: () -> String) {
	if (errorEnabled) warn(message())
}

inline fun Logger.warn(t: Throwable, message: () -> String) {
	if (errorEnabled) warn(message(), t)
}


inline fun Logger.info(message: () -> String) {
	if (errorEnabled) info(message())
}

inline fun Logger.info(t: Throwable, message: () -> String) {
	if (errorEnabled) info(message(), t)
}


inline fun Logger.debug(message: () -> String) {
	if (errorEnabled) debug(message())
}

inline fun Logger.debug(t: Throwable, message: () -> String) {
	if (errorEnabled) debug(message(), t)
}


inline fun Logger.trace(message: () -> String) {
	if (errorEnabled) trace(message())
}

inline fun Logger.trace(t: Throwable, message: () -> String) {
	if (errorEnabled) trace(message(), t)
}