package ru.capjack.tool.logging

interface Output {
	fun writeRecord(record: Record)
}