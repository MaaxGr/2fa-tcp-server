import com.google.gson.Gson
import de.maaxgr.twofatcpserver.entity.NetworkMessage
import de.maaxgr.twofatcpserver.util.*
import java.net.ServerSocket

class TCPServer(private val port: Int, passphrase: String) {

    private val encryption = DESEncryption(passphrase)
    private val onReceive: MutableSet<(NetworkMessage) -> Unit> = mutableSetOf()

    init {
        Thread {
            ServerSocket(port).acceptEndless { socket ->
                socket.getInputStream().readBufferedText().also { string ->
                    val decryptedString = encryption.decrypt(string)
                    val message = Gson().fromJson<NetworkMessage>(decryptedString, NetworkMessage::class.java)

                    string.debug(this)
                    onReceive.invokeAll(message)
                }
            }
        }.start()
    }

    fun addReceiver(receiver: (NetworkMessage) -> Unit) = onReceive.add(receiver)

}
