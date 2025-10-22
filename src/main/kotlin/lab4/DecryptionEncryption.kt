package lab4

import lab4.des.*
import lab4.des.encryptDesPlaintext
import lab4.util.plainTextToBooleanArrayAscii
import lab4.util.toHexString
import util.isHexString
import util.readUntilConditionSatisfy

val M = "0000 0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100 1101 1110 1111".replace(" ", "").toList().map {it == '1'}.toBooleanArray()
val K = "00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001".replace(" ", "").toList().map {it == '1'}.toBooleanArray()

fun main() {

    val plainText = "This is plaintext"

    val key = randomBooleanArray(64)

    val encrypted: String = plainText.encryptDesPlaintext(key.toHexString(), outputFormat = OutputFormat.PLAIN_TEXT_ASCII)
    println("Encrypted text: $encrypted")
    println("Encrypted in hex: ${encrypted.plainTextToBooleanArrayAscii().toHexString()}")

    val decrypted: String = encrypted.plainTextToBooleanArrayAscii().toHexString().decryptDesHexEncodedMessage(key.toHexString(), outputFormat = OutputFormat.PLAIN_TEXT_ASCII)
    println(decrypted)
}
