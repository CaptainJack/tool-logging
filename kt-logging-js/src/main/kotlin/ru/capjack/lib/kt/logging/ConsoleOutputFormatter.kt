package ru.capjack.lib.kt.logging

import ru.capjack.lib.kt.logging.internal.stackTrace

class ConsoleOutputFormatter(
	classNameLengthLimit: Int = 20
) : RecordFormatter {
	private val levelLength = Level.values().map { it.name.length }.max()!!
	private val loggerAbbreviator: ClassNameAbbreviator = ClassNameAbbreviator(classNameLengthLimit)
	
	override fun format(record: Record): String {
		val level = record.level.name.padEnd(levelLength)
		val logger = loggerAbbreviator.abbreviate(record.logger)
		
		var result = "$level $logger: ${record.message}"
		
		if (record.throwable != null) {
			result += "\n${record.throwable.stackTrace}"
			var cause = record.throwable.cause
			while (cause != null) {
				result += "\nCaused by: ${cause.stackTrace}"
				cause = cause.cause
			}
		}
		
		return result
	}
}
