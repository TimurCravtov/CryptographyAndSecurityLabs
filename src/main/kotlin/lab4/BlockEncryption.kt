package lab4

import lab4.des.core.decryptDesBlock
import lab4.des.core.encryptDesBlock
import lab4.util.hexToBooleanArray
import lab4.util.toHexString

fun main() {
    val M = "0123456789ABCDEF"
    val K = randomBooleanArray(64).toHexString();
    println("M: $M")
    println("K: $K")
    val enc = M.hexToBooleanArray().encryptDesBlock(K.hexToBooleanArray(), true)
    println("C: ${enc.toHexString()}")
    val dec = enc.decryptDesBlock(K.hexToBooleanArray())
    println("D: ${dec.toHexString()}")
}
