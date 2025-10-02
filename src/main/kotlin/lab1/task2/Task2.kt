package lab1.task2

import lab1.common.decryptCaesarPermutation
import lab1.common.encryptCaesar
import lab1.common.encryptCaesarPermutation
import lab1.common.normalizeForEncryption
import util.readUntilConditionSatisfy

fun main() {

    val option = readUntilConditionSatisfy(
        "Enter E to encrypt or D to encrypt the Caesar with permutation: ",
        {it.uppercase()},
        {it == "E" || it == "D"}
    )

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

    if (option == "E") {

        val message = readUntilConditionSatisfy(
            "Enter the message to Encrypt: ",
            {(it.normalizeForEncryption())},
            {true},
            "Message should only contain English letters & spaces"
        )

        println("Encrypted message ${message.encryptCaesarPermutation(key, key2)}")

    } else {

        val encryptedMessage = readUntilConditionSatisfy(
            "Enter the encrypted message to decrypt: ",
            {(it.normalizeForEncryption())},
            {true},
            "Message should only contain English letters & spaces"
        )

        println("Decrypted message ${encryptedMessage.encryptCaesarPermutation(key, key2)}")

    }
}

