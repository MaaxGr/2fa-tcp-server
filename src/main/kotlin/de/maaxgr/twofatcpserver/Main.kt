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

    val passphrase = prop.getProperty("passphrase", null)

    Main(passphrase)
}

class Main(passphrase: String) {

    init {
        val tcpServer = TCPServer(passphrase)
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