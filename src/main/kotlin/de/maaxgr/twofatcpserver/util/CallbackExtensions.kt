package de.maaxgr.twofatcpserver.util

fun <T> Collection<(T) -> Unit>.invokeAll(content: T) = forEach { it.invoke(content) }