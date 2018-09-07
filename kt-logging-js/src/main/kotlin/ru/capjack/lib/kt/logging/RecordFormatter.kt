package ru.capjack.lib.kt.logging

interface RecordFormatter {
	fun format(record: Record): String
}
