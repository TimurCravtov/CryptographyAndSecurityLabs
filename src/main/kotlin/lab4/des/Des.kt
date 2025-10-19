package lab4.des

import lab4.toBitString
import lab4.util.hexToBooleanArray
import lab4.util.toHexString
import lab4.util.xor
import util.Logger
import util.green
import util.*

/**
 * @receiver - 64 bit array
 * @param key = 64 bits
 * @return Encrypted C
 */
fun BooleanArray.encryptDesBlock(key: BooleanArray, loggerActive: Boolean = false): BooleanArray {

    val mPermuted = applyPermutation(IP, this) // keeps size, 64 bits

    val L0 = mPermuted.sliceArray(0..31) // 32 bits
    val R0 = mPermuted.sliceArray(32..63) // 32 bits

    var LCurrent = L0
    var RCurrent = R0

    val KList = getKList(key);

    for (i in 1..16) {
        val LNext = RCurrent;
        val RNext = LCurrent xor f(RCurrent, KList[i - 1]) // 32 bits

        LCurrent = LNext
        RCurrent = RNext

    }


    val C = getCfromL16R16(LCurrent, RCurrent);

    return C
}

fun String.encryptHexEncodedDesBlock(hexEncodedKey: String): String {
    val block = this.hexToBooleanArray();
    val key = this.hexToBooleanArray();

    require(block.size == 64); require(key.size == 64)

    val result = block.encryptDesBlock(key)
    return result.toHexString()

}

/**
 * @receiver - 64 bits
 * @param key - 64 bits
 */
fun BooleanArray.decryptDesBlock(key: BooleanArray): BooleanArray {

    val R16L16 = unapplyPermutation(IP_1, this) // 64 bits

    val L16 = R16L16.sliceArray(32..63)
    val R16 = R16L16.sliceArray(0..31)


    val keyList = getKList(key);

    var LCurrent = L16
    var RCurrent = R16

    for (i in 16 downTo 1) {

        val K_i = keyList[i-1]
        val RPrev = LCurrent;
        val LPrev = RCurrent xor f(RPrev, K_i)

        LCurrent = LPrev
        RCurrent = RPrev


    }


    val m = unapplyPermutation(IP, LCurrent + RCurrent);
    return m;

}


/**
 * @return 16 elements of 48 bit keys
 */
fun getKList(key: BooleanArray): List<BooleanArray> {

    val KPlus = applyPermutation(PC_1, key); // KPlus becomes 56 bits

    // C0 and D0 have 28 bits
    val C0 = KPlus.sliceArray(0..27)
    val D0 = KPlus.sliceArray(28..55)

    // keys have 48 bits (28 + 28 from c0 and d0, and some not used)
    val CList = mutableListOf<BooleanArray>();
    val DList = mutableListOf<BooleanArray>();
    val KList = mutableListOf<BooleanArray>();

    CList.add(C0);
    DList.add(D0);

    for (i in 1..16) {

        val shiftNumber = when (i) {
            1, 2, 9, 16 -> 1
            else -> 2
        }

        val CNext = CList.last().rotateLeft(shiftNumber)
        val DNext = DList.last().rotateLeft(shiftNumber)

        CList.add(CNext)
        DList.add(DNext)
        KList.add(applyPermutation(PC_2, CNext + DNext))

    }

    return KList.toList();
}

internal fun BooleanArray.rotateLeft(bits: Int): BooleanArray {
    val size = size
    val result = BooleanArray(size)
    for (i in 0 until size) {
        result[i] = this[(i + bits) % size]
    }
    return result
}

/**
 * @param Rn - has 32 bits
 * @param Kn_plus_1 - has 48 bits
 * @return 32 bit result
 */
internal fun f(Rn: BooleanArray, Kn_plus_1: BooleanArray): BooleanArray {

    val R_E_permuted = applyPermutation(E, Rn) // which extends R to 48 bits

    val R_Permuted_XOR_ed = R_E_permuted xor Kn_plus_1

    val B_blocks = R_Permuted_XOR_ed.toList().chunked(6).map {it.toBooleanArray() }; // 48 -> 8 blocks for 6 bits

    val sBoxOutput = B_blocks
        .mapIndexed { i, b -> apply_S_box(b, i + 1).toList() } // 8 blocks * 4 bit => 32 bit
        .flatten().toBooleanArray()

    return applyPermutation(P, sBoxOutput)
}

fun getCfromL16R16(L16: BooleanArray, R16: BooleanArray, loggerActive: Boolean = true): BooleanArray {

    val log = Logger.instance
    log!!.seen = loggerActive;

    log.log("Step: From L16 and R16, get C".black().bgWhite())
    log.log("Got L16 = ${L16.toBitString().yellow()}, R16 = ${R16.toBitString().cyan()}")
    log.log("R16L16 = ${R16.toBitString().cyan()}${L16.toBitString().yellow()}")

    val R16L16 = R16 + L16

    log.log("Permutation table IP^-1 = ".cyan())
    log.log(IP_1.toList().chunked(8).joinToString("\n") { it.joinToString(" ") }.purple())

    val C = applyPermutation(IP_1, R16L16);

    log.log("C = ${C.toBitString().blue()}")
    return C;
}
