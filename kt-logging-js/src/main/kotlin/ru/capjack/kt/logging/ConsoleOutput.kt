package ru.capjack.kt.logging

class ConsoleOutput : Output {
	private val loggerAbbreviator = ClassNameAbbreviator(20)
	
	override fun writeRecord(record: Record) {
		when (record.level) {
			Level.ERROR -> console::error
			Level.WARN  -> console::warn
			Level.INFO  -> console::info
			Level.DEBUG -> console::log
			Level.TRACE -> console::log
		}.asDynamic().call(
			null,
			"${record.level.name.padEnd(5)} ${loggerAbbreviator.abbreviate(record.logger)}: ${record.message}",
			record.throwable
		)
	}
}