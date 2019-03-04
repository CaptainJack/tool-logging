package ru.capjack.tool.kt.logging

interface RecordFormatter {
	fun format(record: Record): String
}
