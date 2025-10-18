package lab1.task1

import lab1.common.decryptCaesar
import lab1.common.encryptCaesar
import lab1.common.normalizeForEncryption
import util.readUntilConditionSatisfy

fun main() {

    val option = readUntilConditionSatisfy(
        "Enter E to encrypt or D to decrypt: ",
        {it.uppercase()},
        {it == "E" || it == "D"}
    )

    val key = readUntilConditionSatisfy(
        prompt = "Enter the key: ",
        parser = { it.toIntOrNull() },
        condition = { it in 1..25 },
        "The key must be within [1, 25]"
    )

    if (option == "E") {

        val message = readUntilConditionSatisfy(
            "Enter the message to Encrypt: ",
            {(it.normalizeForEncryption())},
            {true},
            "Message should only contain English letters & spaces"
        )

        println("Encrypted message: ${message.encryptCaesar(key)}")

    } else {
        val encodedMessage = readUntilConditionSatisfy(
            "Enter the encoded message to decrypt: ",
            {it.normalizeForEncryption()},
            {true},
            "Message should only contain English Letters & Spaces"
        )

        println("Decrypted message: ${encodedMessage.decryptCaesar(key)}");

    }

}


