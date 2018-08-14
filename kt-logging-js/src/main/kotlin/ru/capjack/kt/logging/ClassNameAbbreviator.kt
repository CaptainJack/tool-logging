package ru.capjack.kt.logging

class ClassNameAbbreviator(private val targetLength: Int) {
	private val maxDots = 16
	
	fun abbreviate(fqClassName: String): String {
		val buf = StringBuilder(targetLength)
		
		val inLen = fqClassName.length
		if (inLen < targetLength) {
			return fqClassName
		}
		
		val dotIndexesArray = IntArray(maxDots)
		val lengthArray = IntArray(maxDots + 1)
		val dotCount = computeDotIndexes(fqClassName, dotIndexesArray)
		
		if (dotCount == 0) {
			return fqClassName
		}
		
		computeLengthArray(fqClassName, dotIndexesArray, lengthArray, dotCount)
		
		buf.append(fqClassName.take(lengthArray[0] - 1))
		
		for (i in 1..dotCount) {
			buf.append(fqClassName.substring(dotIndexesArray[i - 1], dotIndexesArray[i - 1] + lengthArray[i]))
		}
		
		return buf.toString()
	}
	
	private fun computeLengthArray(className: String, dotArray: IntArray, lengthArray: IntArray, dotCount: Int) {
		var toTrim = className.length - targetLength
		
		for (i in 0 until dotCount) {
			var previousDotPosition = -1
			if (i > 0) {
				previousDotPosition = dotArray[i - 1]
			}
			val available = dotArray[i] - previousDotPosition - 1
			
			val len = if (toTrim > 0) {
				if (available < 1) available else 1
			}
			else available
			
			toTrim -= available - len
			lengthArray[i] = len + 1
		}
		
		lengthArray[dotCount] = className.length - dotArray[dotCount - 1]
	}
	
	private fun computeDotIndexes(className: String, dotArray: IntArray): Int {
		var dotCount = 0
		var k = 0
		while (true) {
			k = className.indexOf('.', k)
			if (k != -1 && dotCount < maxDots) {
				dotArray[dotCount] = k
				dotCount++
				k++
			}
			else {
				break
			}
		}
		return dotCount
	}
}