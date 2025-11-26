package lab5

import lab5.algorithms.randomInt
import java.math.BigInteger
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

fun main() {

    val p = BigInteger("32317006071311007300153513477825163362488057133489075174588434139269806834136210002792056362640164685458556357935330816928829023080573472625273554742461245741026202527916572972862706300325263428213145766931414223654220941111348629991657478268034230553086349050635557712219187890332729569696129743856241741236237225197346402691855797767976823014625397933058015226858730761197532436467475855460715043896844940366130497697812854295958659597567051283852132784468522925504568272879113720098931873959143374175837826000278034973198552060607533234122603254684088120031105907484281003994966956119696956248629032338072839127039")
    val alpha = BigInteger("3")

    // alice private key
    val a = randomInt(BigInteger.ONE, p - BigInteger.ONE)

    // bob private key
    val b = randomInt(BigInteger.ONE, p - BigInteger.ONE)

    // alice public key
    val A = alpha.modPow(a, p)     // α^a mod p

    // bob public key
    val B = alpha.modPow(b, p)     // α^b mod p

    // alice computed common key
    val kA = B.modPow(a, p)        // (α^b)^a mod p

    // bob computed common key
    val kB = A.modPow(b, p)        // (α^a)^b mod p

    require(kA == kB)
    println("Common key: $kA")

    // use common key as key for aes
    val raw = kA.toByteArray()
    val secretBytes =
        if (raw.size >= 32) raw.copyOfRange(raw.size - 32, raw.size)
        else ByteArray(32 - raw.size) + raw

    val aesKey = SecretKeySpec(secretBytes, "AES")

    // random iv
    val iv = ByteArray(16).also { Random.nextBytes(it) }
    val ivSpec = IvParameterSpec(iv)

    val plaintext = "Some text which alice encrypted"
    println("plaintext: $plaintext")

    // Encrypt
    val cipherEnc = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipherEnc.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec)
    val ciphertext = cipherEnc.doFinal(plaintext.toByteArray())

    println("Ciphertext (hex): " + ciphertext.joinToString("") { "%02x".format(it) })

    // decrypt
    val cipherDec = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipherDec.init(Cipher.DECRYPT_MODE, aesKey, ivSpec)
    val decrypted = cipherDec.doFinal(ciphertext).toString(Charsets.UTF_8)

    println("Decrypted: $decrypted")
}
