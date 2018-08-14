package ru.capjack.kt.logging

interface Output {
	fun writeRecord(record: Record)
}