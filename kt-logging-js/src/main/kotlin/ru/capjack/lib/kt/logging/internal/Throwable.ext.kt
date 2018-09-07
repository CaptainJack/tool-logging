package ru.capjack.lib.kt.logging.internal

internal val Throwable.stackTrace: String
	get() = asDynamic().stack as? String ?: "<no stack trace>"