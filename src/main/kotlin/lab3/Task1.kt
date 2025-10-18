package lab3

import lab3.utils.normalizeForEncryption
import util.readUntilConditionSatisfy

import util.alphabets.romanianAlphabet
import lab3.utils.makeReplacements


fun main() {

    val replacements = mapOf(
        listOf('I', 'Î') to 'I'
    )

    val alphabet = romanianAlphabet.makeReplacements(replacements).toList().distinct().joinToString("")

    val option = readUntilConditionSatisfy(
        "Enter E to encrypt or D to decrypt: ",
        {it.uppercase()},
        {it == "E" || it == "D"}
    )

    if (option == "E") {

        val message = readUntilConditionSatisfy(
            "Enter the message to encrypt: ",
            { it.normalizeForEncryption(alphabet, replacements) },
            { true },
            "The message can only contain characters in [$alphabet]"
        )

        val key = readUntilConditionSatisfy(
            "Enter the key to use for encryption: ",
            { it.normalizeForEncryption(alphabet, replacements) },
            { it.length >= 7 },
            "Key length should be 7 or more, it can only contain symbols in [${alphabet}]"
        )

        val enc = message.encryptPlayfair(alphabet, key, 5, 6) // for romanian alphabet
        println("Encrypted message: $enc")

    } else {
        val message = readUntilConditionSatisfy(
            "Enter the message to decrypt: ",
            { it.normalizeForEncryption(alphabet, replacements) },
            { true },
            "The message can only contain characters in ${alphabet}"
        )

        val key = readUntilConditionSatisfy(
            "Enter the key to use for decryption: ",
            { it.normalizeForEncryption(alphabet, replacements) },
            { it.length >= 7 },
            "Key length should be 7 or more, it can only contain symbols in [${alphabet}]"
        )

        val enc = message.decryptPlayfair(alphabet, key, 5, 6) // for romanian alphabet
        println("Decrypted message: $enc")

    }

}

