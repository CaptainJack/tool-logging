package ru.capjack.lib.kt.logging

import org.slf4j.LoggerFactory
import org.slf4j.spi.LocationAwareLogger
import ru.capjack.lib.kt.logging.internal.AwareLogger
import ru.capjack.lib.kt.logging.internal.DefaultLogger
import kotlin.reflect.KClass

actual object Logging {
	@JvmStatic
	actual fun getLogger(name: String): Logger {
		return wrap(LoggerFactory.getLogger(name))
	}
	
	@JvmStatic
	actual fun getLogger(clazz: KClass<*>): Logger {
		return wrap(LoggerFactory.getLogger(clazz.java))
	}
	
	@JvmStatic
	private fun wrap(logger: org.slf4j.Logger): Logger {
		return if (logger is LocationAwareLogger)
			AwareLogger(logger)
		else
			DefaultLogger(logger)
	}
}