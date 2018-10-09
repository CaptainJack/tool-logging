package ru.capjack.kt.logging

interface RecordFormatter {
	fun format(record: Record): String
}
