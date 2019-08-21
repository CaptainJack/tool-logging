package ru.capjack.tool.logging

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertSame

class TestClassNameAbbreviator {
	@Test
	fun short_name_does_not_change() {
		val abbreviator = ClassNameAbbreviator(48)
		val name = "ru.capjack.tool.logging.TestClassNameAbbreviator"
		assertSame(
			name,
			abbreviator.abbreviate(name)
		)
	}
	
	@Test
	fun long_name_shortens() {
		val abbreviator = ClassNameAbbreviator(40)
		val name = "ru.capjack.tool.logging.TestClassNameAbbreviator"
		assertEquals(
			"r.c.t.logging.TestClassNameAbbreviator",
			abbreviator.abbreviate(name)
		)
	}
	
	@Test
	fun long_name_shortens_with_dots_limit() {
		val abbreviator = ClassNameAbbreviator(32, 2)
		val name = "ru.capjack.tool.logging.TestClassNameAbbreviator"
		assertEquals(
			"r.c.tool.logging.TestClassNameAbbreviator",
			abbreviator.abbreviate(name)
		)
	}
	
	@Test
	fun shortened_name_is_cached() {
		val abbreviator = ClassNameAbbreviator(16)
		
		val name = "ru.capjack.tool.logging.TestClassNameAbbreviator"
		val short = abbreviator.abbreviate(name)
		
		assertNotEquals(name, short)
		
		assertSame(
			short,
			abbreviator.abbreviate(name)
		)
	}
}