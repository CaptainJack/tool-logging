package ru.capjack.tool.logging

class ConsoleOutput(
	private val formatter: RecordFormatter
) : Output {
	
	override fun writeRecord(record: Record) {
		val message = formatter.format(record)
		when (record.level) {
			Level.ERROR -> console.error(message)
			Level.WARN  -> console.warn(message)
			Level.INFO  -> console.info(message)
			Level.DEBUG -> console.log(message)
			Level.TRACE -> console.log(message)
		}
	}
}
