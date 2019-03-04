package ru.capjack.tool.kt.logging

import ru.capjack.tool.ktjs.lang.stackTrace

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
			result += "\n" + record.throwable.stackTrace
		}
		
		return result
	}
}
