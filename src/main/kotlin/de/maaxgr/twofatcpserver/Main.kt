import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import de.maaxgr.twofatcpserver.util.debug
import de.maaxgr.twofatcpserver.util.pasteText
import java.awt.Robot
import java.io.File
import java.lang.Exception
import java.util.*

fun main() {
    val file = File("config.properties")
            .also {
                if (!it.exists()) {
                    throw Exception("File config.properties does not exists!")
                }
            }

    val prop = Properties().apply { load(file.inputStream()) }

    val port = prop.getProperty("port", "8123").toInt()
    val passphrase = prop.getProperty("passphrase", null)

    Main(port, passphrase)
}

class Main(port: Int, passphrase: String) {

    init {
        val tcpServer = TCPServer(port, passphrase)
        tcpServer.addReceiver { message ->
            message.debug(this)

            GlobalScope.launch {
                when (message.identifier) {
                    "PASTE_TEXT" -> Robot().pasteText(message.message)
                    "PASTE_TEXT_DELAY" -> {
                        delay(1000)
                        Robot().pasteText(message.message)
                    }
                }
            }

        }
    }

}
