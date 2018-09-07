package ru.capjack.lib.kt.logging

class ConsoleOutput(
	private val formatter: RecordFormatter
) : Output {
	
	override fun writeRecord(record: Record) {
		val fn = when (record.level) {
			Level.ERROR -> console::error
			Level.WARN  -> console::warn
			Level.INFO  -> console::info
			Level.DEBUG -> console::log
			Level.TRACE -> console::log
		}.asDynamic().unsafeCast<Function1<String, Unit>>()
		
		fn.invoke(formatter.format(record))
	}
}