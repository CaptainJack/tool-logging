package ru.capjack.tool.kt.logging

class ProxyOutput(var target: Output) : Output {
	override fun writeRecord(record: Record) = target.writeRecord(record)
}