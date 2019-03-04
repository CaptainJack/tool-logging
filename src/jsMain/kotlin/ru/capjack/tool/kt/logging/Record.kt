package ru.capjack.tool.kt.logging

class Record(
	val time: Double,
	val logger: String,
	val level: Level,
	val message: String,
	val throwable: Throwable?
)
