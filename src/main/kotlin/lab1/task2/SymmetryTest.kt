package lab1.task2

import lab1.common.decryptCaesarPermutation
import lab1.common.encryptCaesar
import lab1.common.encryptCaesarPermutation
import lab1.common.normalizeForEncryption
import util.readUntilConditionSatisfy

fun main() {

    val key = readUntilConditionSatisfy(
        prompt = "Enter the first key (integer): ",
        parser = { it.toIntOrNull() },
        condition = { it in 1..25 },
        "The key must be within [1, 25]"
    )

    val key2 = readUntilConditionSatisfy(
        prompt = "Enter the second key (string with 7 letters): ",
        parser = { it.normalizeForEncryption() },
        condition = { it.length >= 7 },
        "The key must have 7 or more letters"
    )

    val message = readUntilConditionSatisfy(
        "Enter the message to Encrypt: ",
        {(it.normalizeForEncryption())},
        {true},
        "Message should only contain English letters & spaces"
    )


    val enc = message.encryptCaesarPermutation(key, key2)
    println("Message: $message; key: $key, key2: $key2, e(k1,k2): $enc",)
    val dec = enc.decryptCaesarPermutation(key, key2)
    println("Encrypted message: $enc; key: $key, key2: $key2, d(k1,k2): $dec")

}

