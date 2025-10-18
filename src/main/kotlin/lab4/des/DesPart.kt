package lab4.des

import lab4.util.xor
import kotlin.random.Random

fun getM(L16: BooleanArray, R16: BooleanArray) {

    require(L16.size == R16.size);
    require(R16.size == 32)

    var currentL = L16
    var currentR = R16

    for (i in 15..0) {

        val randomKey = BooleanArray(48) {Random.nextBoolean()}

        val previousR = currentL
        val previousL = currentR xor f(currentL, randomKey);

        currentL = previousL
        currentR = previousR

    }
}




