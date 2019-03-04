package ru.capjack.tool.kt.logging

interface Output {
	fun writeRecord(record: Record)
}