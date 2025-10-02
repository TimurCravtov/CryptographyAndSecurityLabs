package lab1.common

import kotlin.text.iterator

fun String.normalizeForEncryption(): String {
    if (!this.all { it in 'a'..'z' || it in 'A'..'Z' || it.isWhitespace() }) {
        throw RuntimeException("String has wrong format. Only English letters are allowed")
    }
    return this.replace(" ", "").uppercase()
}

val alphabet = buildString {
    for (c in 'A'..'Z') {
        append(c)
    }
}

fun alphabetWithKeyInserted(key: String): String {

    val upperKey = key.uppercase().filter { it.isLetter() }
    val keyUnique = upperKey.toList().distinct()

    return buildString {
        keyUnique.forEach { append(it) }
        alphabet.filter { it !in keyUnique }.forEach { append(it) }
    }
}


fun code(char: Char): Int = alphabet.indexOf(char.uppercaseChar())

fun String.encryptCaesar(key: Int): String {
    val normalized = this.normalizeForEncryption()
    val shift = (key % 26 + 26) % 26
    return buildString {
        for (char in normalized) {
            val index = code(char)
            if (index != -1) {
                val newIndex = (index + shift) % 26
                append(alphabet[newIndex])
            }
        }
    }
}

fun String.decryptCaesar( key: Int): String {
    return this.encryptCaesar((26 - key) % 26)
}

fun String.encryptCaesarPermutation(key: Int, key2: String) : String {
    val cryptoAlphabet = alphabetWithKeyInserted(key2)
    val normalized = this.normalizeForEncryption()
    val shift = (key % 26 + 26) % 26
    return buildString {
        for (char in normalized) {
            val index = cryptoAlphabet.indexOf(char)
            if (index != -1) {
                val newIndex = (index + shift) % 26
                append(cryptoAlphabet[newIndex])
            }
        }
    }
}

fun String.decryptCaesarPermutation(key: Int, key2: String): String {
    val cryptoAlphabet = alphabetWithKeyInserted(key2)
    val normalized = this.normalizeForEncryption()
    val shift = key % 26
    return buildString {
        for (char in normalized) {
            val index = cryptoAlphabet.indexOf(char)
            if (index != -1) {
                val newIndex = (index - shift + 26) % 26
                append(cryptoAlphabet[newIndex])
            }
        }
    }
}

