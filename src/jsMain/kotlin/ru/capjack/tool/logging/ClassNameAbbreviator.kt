package ru.capjack.tool.logging

class ClassNameAbbreviator(
	private val lengthLimit: Int,
	private val dotsLimit: Int = 16
) {
	private val cache = js("{}")
	
	fun abbreviate(name: String): String {
		if (name.length < lengthLimit) {
			return name
		}
		
		val cached = cache[name].unsafeCast<String?>()
		if (cached != null) {
			return cached
		}
		
		val dots = IntArray(dotsLimit)
		val dotCount = computeDots(name, dots)
		
		if (dotCount == 0) {
			return name
		}
		
		val lengths = IntArray(dotsLimit + 1)
		computeLengths(name, dots, lengths, dotCount)
		
		val builder = StringBuilder(lengthLimit).append(name.take(lengths[0] - 1))
		
		for (i in 1..dotCount) {
			builder.append(name.substring(dots[i - 1], dots[i - 1] + lengths[i]))
		}
		
		val result = builder.toString()
		cache[name] = result
		
		return result
	}
	
	private fun computeLengths(name: String, dots: IntArray, lengths: IntArray, dotCount: Int) {
		var toTrim = name.length - lengthLimit
		
		var previousDot = -1
		
		for (i in 0 until dotCount) {
			val available = dots[i] - previousDot - 1
			previousDot = dots[i]
			
			val len = if (toTrim > 0) {
				if (available < 1) available else 1
			}
			else available
			
			toTrim -= available - len
			lengths[i] = len + 1
		}
		
		lengths[dotCount] = name.length - dots[dotCount - 1]
	}
	
	private fun computeDots(name: String, dots: IntArray): Int {
		var dotCount = 0
		var k = 0
		while (true) {
			k = name.indexOf('.', k)
			if (k != -1 && dotCount < dotsLimit) {
				dots[dotCount++] = k++
			}
			else {
				break
			}
		}
		return dotCount
	}
}