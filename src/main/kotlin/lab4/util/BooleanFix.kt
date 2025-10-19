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

fun String.binaryStringToBooleanArray(): BooleanArray {
    require(isNotEmpty()) { "String cannot be empty" }
    require(all { it == '0' || it == '1' }) { "Invalid binary string: $this" }

    return BooleanArray(length) { i -> this[i] == '1' }
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

fun BooleanArray.toAsciiString(): String {
    require(size % 8 == 0) { "BooleanArray size must be divisible by 8 to convert to ASCII" }

    val sb = StringBuilder()
    for (i in indices step 8) {
        // Take 8 bits
        val byteBits = sliceArray(i until i + 8)
        // Convert to integer
        var byteValue = 0
        for (j in byteBits.indices) {
            if (byteBits[j]) {
                byteValue = byteValue or (1 shl (7 - j)) // MSB first
            }
        }
        sb.append(byteValue.toChar())
    }
    return sb.toString()
}


fun BooleanArray.toBitString(): String {
    return joinToString("") { if (it) "1" else "0" }
}
