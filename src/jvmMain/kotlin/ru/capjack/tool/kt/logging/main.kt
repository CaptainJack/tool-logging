package ru.capjack.tool.kt.logging

fun main() {
	
	try {
		foo1()
	}
	catch (e: Exception) {
		e.printStackTrace()
	}
	
}

fun foo1() {
	foo2()
}

fun foo2() {
	throw RuntimeException("Hello")
}