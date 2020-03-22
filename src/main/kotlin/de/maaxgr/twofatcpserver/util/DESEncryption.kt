package de.maaxgr.twofatcpserver.util

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


class DESEncryption(key: String) {

    companion object {
        const val CIPHER = "AES/ECB/PKCS5Padding"
    }

    private val secretKey: SecretKeySpec = buildKey(key)

    @Throws(UnsupportedEncodingException::class, NoSuchAlgorithmException::class)
    private fun buildKey(myKey: String): SecretKeySpec {
        var key: ByteArray = myKey.toByteArray(Charsets.UTF_8)
        val sha: MessageDigest = MessageDigest.getInstance("SHA-1")
        key = sha.digest(key)
        key = Arrays.copyOf(key, 16)
        return SecretKeySpec(key, "AES")
    }

    fun encrypt(string: String): String? {
        try {
            val cipher = Cipher.getInstance(CIPHER)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return Base64.getEncoder().encodeToString(cipher.doFinal(string.toByteArray(Charsets.UTF_8)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun decrypt(string: String?): String? {
        try {
            val cipher = Cipher.getInstance(CIPHER)
            cipher.init(Cipher.DECRYPT_MODE, secretKey)

            val byteArray = cipher.doFinal(Base64.getDecoder().decode(string))
            return String(byteArray)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }



}
