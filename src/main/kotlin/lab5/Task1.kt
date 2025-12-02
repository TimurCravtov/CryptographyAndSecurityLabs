package lab5

import lab5.algorithms.bigIntToString
import lab5.algorithms.decryptRsa
import lab5.algorithms.encryptRsa
import lab5.algorithms.keyGen
import lab5.algorithms.messageToBigInt

fun main() {
    val sayMyName = "Timur Cravțov"

    val keys = keyGen();
    println($"Key data. Public key: ${keys.publicKey};\nPrivate key: ${keys.privateKey}, \nn: ${keys.n}")

    val messageInt = sayMyName.messageToBigInt();

    val ciphertext = encryptRsa(messageInt, keys.publicKey, keys.n)
    println("ciphertext: $ciphertext")

    println(ciphertext)

    val d = decryptRsa(ciphertext, keys.privateKey, keys.n)
    println(d.bigIntToString())
}