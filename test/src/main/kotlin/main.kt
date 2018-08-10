
import ru.capjack.lib.kt.logging.Logging
import ru.capjack.lib.kt.logging.error

fun main(args: Array<String>) {
	
	val l = Logging.getLogger("kaka")
	
	println(l::class)
	
	l.error { "as"}
	
//	Logger
	
}