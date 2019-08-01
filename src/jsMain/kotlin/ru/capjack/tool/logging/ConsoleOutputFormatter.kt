package ru.capjack.tool.logging

import ru.capjack.tool.lang.stackTrace

class ConsoleOutputFormatter(
	classNameLengthLimit: Int = 54
) : RecordFormatter {
	private val levelLength = Level.values().map { it.name.length }.max()!!
	private val loggerAbbreviator: ClassNameAbbreviator = ClassNameAbbreviator(classNameLengthLimit)
	
	override fun format(record: Record): String {
		val level = record.level.name.padEnd(levelLength)
		val logger = loggerAbbreviator.abbreviate(record.logger)
		
		var result = "$level $logger: ${record.message}"
		
		if (record.error != null) {
			result += "\n" + record.error.stackTrace
		}
		
		return result
	}
}
