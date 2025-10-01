package lab1

import java.lang.Character.isLetter
import java.lang.Character.isWhitespace

fun normalizeString(string: String): String {
    if (!(string.chars().allMatch { isLetter(it) || isWhitespace(it) })) {
        throw RuntimeException("String has wrong format. Only English letters are allowed")
    } else return string.replace(" ", "").uppercase()
}

val alphabet = buildString {
    for (c in 'A'..'Z') {
        append(c)
    }
}

fun code(char: Char): Int = alphabet.indexOf(char.uppercaseChar())

fun encrypt(message: String, key: Int): String? {
    val normalized = normalizeString(message)
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