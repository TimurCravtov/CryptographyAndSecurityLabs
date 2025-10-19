package lab4

import kotlin.random.Random

val M = "0000 0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100 1101 1110 1111".replace(" ", "").toList().map {it == '1'}.toBooleanArray()
val K = "00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001".replace(" ", "").toList().map {it == '1'}.toBooleanArray()

fun main() {

}

fun randomBooleanArray(bits: Int): BooleanArray {
    return BooleanArray(bits) { Random.nextBoolean() }
}

fun BooleanArray.toBitString(): String {
    return joinToString("") { if (it) "1" else "0" }
}

