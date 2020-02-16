package de.maaxgr.twofatcpserver.util

fun <T> T.debug(context: Any) {
    println("Debug '$this' in class ${context.javaClass.simpleName}")
}