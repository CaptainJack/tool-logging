package ru.capjack.tool.logging

inline val Any.ownLogger: Logger
	get() = Logging.getLogger(this::class)