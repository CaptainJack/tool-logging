package ru.capjack.tool.logging

interface RecordFormatter {
	fun format(record: Record): String
}
