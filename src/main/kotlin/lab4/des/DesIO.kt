package lab4.des

import lab4.des.core.encryptDesBlock
import lab4.util.hexToBooleanArray
import lab4.util.plainTextToBooleanArrayAscii
import lab4.util.toAsciiString
import lab4.util.toHexString
import util.isHexString
import kotlin.collections.chunked
import kotlin.collections.toBooleanArray

fun String.encryptHexEncodedPlaintext(hexEncodedKey: String): String {

    require(hexEncodedKey.isHexString() && hexEncodedKey.length == 16)

    val booleanKey = hexEncodedKey.hexToBooleanArray()
    val booleanMessage = this.hexToBooleanArray()

    val remainder = booleanMessage.size % 64
    val paddingSize = if (remainder == 0) 0 else 64 - remainder
    // pad at the end with zeros to make full 64-bit blocks
    val paddedMessage = booleanMessage + BooleanArray(paddingSize) { false }

    val encryptedBooleanArray = paddedMessage
        .toList()
        .chunked(64)
        .map { it.toBooleanArray().encryptDesBlock(booleanKey) }
        .flatMap { it.toList() }
        .toBooleanArray()

    return encryptedBooleanArray.toHexString()
}

fun String.encryptHexEncodedDesBlock(hexEncodedKey: String): String {

    require(hexEncodedKey.isHexString() && hexEncodedKey.length == 16)
    require(this.isHexString() && hexEncodedKey.length == 16)

    val block = this.hexToBooleanArray();
    val key = this.hexToBooleanArray();

    require(block.size == 64); require(key.size == 64)

    val result = block.encryptDesBlock(key)
    return result.toHexString()
}
inline fun <reified T> String.encryptDesPlaintext(hexEncodedKey: String, outputFormat: OutputFormat): T {

    val booleanKey = hexEncodedKey.hexToBooleanArray()
    val booleanMessage = this.plainTextToBooleanArrayAscii()
    val remainder = booleanMessage.size % 64
    val paddingSize = if (remainder == 0) 0 else 64 - remainder
    val paddedMessage = BooleanArray(paddingSize) { false } + booleanMessage

    val encryptedBooleanArray = paddedMessage
        .toList()
        .chunked(64)
        .map { it.toBooleanArray().encryptDesBlock(booleanKey) }
        .flatMap { it.toList() }
        .toBooleanArray()

    val result: Any = when (outputFormat) {
        OutputFormat.PLAIN_TEXT_ASCII -> encryptedBooleanArray.toAsciiString()
        OutputFormat.HEX -> encryptedBooleanArray.toHexString()
        OutputFormat.BOOLEAN_ARRAY -> encryptedBooleanArray
    }

    return result as T
}
