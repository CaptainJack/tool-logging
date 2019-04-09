package ru.capjack.tool.logging

class Record(
	val time: Double,
	val logger: String,
	val level: Level,
	val message: String,
	val error: Throwable?
)
