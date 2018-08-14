package ru.capjack.kt.logging

import kotlin.reflect.KClass

expect object Logging {
	fun getLogger(name: String): Logger
	
	fun getLogger(clazz: KClass<out Any>): Logger
}