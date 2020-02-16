package de.maaxgr.twofatcpserver.util

import java.io.InputStream
import java.net.ServerSocket
import java.net.Socket

fun ServerSocket.acceptEndless(callback: (Socket) -> Unit) = acceptUntil({ true }, callback)

fun ServerSocket.acceptUntil(condition: () -> Boolean = { true }, callback: (Socket) -> Unit) {
    while (condition.invoke()) {
        callback.invoke(accept())
    }
}

fun InputStream.readBufferedText(): String {
    return bufferedReader().readText()
}