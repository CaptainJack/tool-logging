package ru.capjack.lib.kt.logging

interface Output {
	fun writeRecord(record: Record)
}