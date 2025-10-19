package lab4.util

infix fun BooleanArray.xor(other: BooleanArray): BooleanArray {
    require(size == other.size) { "Arrays must be the same size" }
    return BooleanArray(size) { i -> this[i] xor other[i] }
}

fun BooleanArray.toHexString(): String {
    require(isNotEmpty()) { "Array cannot be empty" }
    val sb = StringBuilder()

    for (i in indices step 4) {
        var value = 0
        for (j in 0 until 4) {
            if (i + j < size && this[i + j]) {
                value = value or (1 shl (3 - j))
            }
        }
        sb.append(value.toString(16))
    }

    return sb.toString().uppercase()
}

fun String.hexToBooleanArray(): BooleanArray {
    require(isNotEmpty()) { "String cannot be empty" }
    require(all { it.isDigit() || it.lowercaseChar() in 'a'..'f' }) { "Invalid hex string: $this" }

    val bits = BooleanArray(length * 4)
    forEachIndexed { i, c ->
        val value = c.digitToInt(16)
        for (j in 0 until 4) {
            bits[i * 4 + j] = (value and (1 shl (3 - j))) != 0
        }
    }
    return bits
}

fun String.plainTextToBooleanArrayAscii(): BooleanArray {
    require(isNotEmpty()) { "String cannot be empty" }

    val bits = BooleanArray(length * 8)
    forEachIndexed { i, ch ->
        val value = ch.code
        for (j in 0 until 8) {
            bits[i * 8 + j] = (value and (1 shl (7 - j))) != 0
        }
    }
    return bits
}
