package lab4

import lab4.des.getCfromL16R16
import lab4.util.binaryStringToBooleanArray
import util.readUntilConditionSatisfy
import kotlin.random.Random

fun main() {

    val L16 = randomBooleanArray(32);
    val R16 = randomBooleanArray(32);

//    val L16 = readUntilConditionSatisfy("L16: ", { it }, { it.length == 32 && it.all { c -> c == '1' || c == '0' } }).binaryStringToBooleanArray()
//    val R16 = readUntilConditionSatisfy("R16: ", { it }, { it.length == 32 && it.all { c -> c == '1' || c == '0' } }).binaryStringToBooleanArray()

    getCfromL16R16(L16, R16, true);

}

fun randomBooleanArray(bits: Int): BooleanArray {
    return BooleanArray(bits) { Random.nextBoolean() }
}
