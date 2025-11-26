package lab5.algorithms

import java.math.BigInteger
import java.nio.charset.Charset

fun String.messageToBigInt(): BigInteger {
    return BigInteger(1, this.toByteArray())
}

fun BigInteger.bigIntToString(): String {
   return this.toByteArray().toString(Charset.defaultCharset())
}