package lab4.des.core

import lab4.des.OutputFormat
import lab4.util.*
import lab4.util.hexToBooleanArray
import lab4.util.plainTextToBooleanArrayAscii
import lab4.util.rotateLeftCyclic
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

    val log = Logger.instance
    log!!.seen = loggerActive

    log.log("Step: Permute m ".black().bgWhite())
    log.log("M: ${this.toBitString().blue()}")
    log.log("Table IP: ")
    log.log(IP.toList().chunked(8).joinToString("\n") { it.joinToString(" ") }.purple())

    val mPermuted = applyPermutation(IP, this) // keeps size, 64 bits

    log.log("M permuted: ${mPermuted.toBitString().blue()}")
    log.log("Step: Get L0, R0".black().bgWhite())

    val L0 = mPermuted.sliceArray(0..31) // 32 bits
    val R0 = mPermuted.sliceArray(32..63) // 32 bits

    log.log("L0 = ${L0.toBitString().yellow()}")
    log.log("R0 = ${R0.toBitString().cyan()}")

    var LCurrent = L0
    var RCurrent = R0

    val KList = getKList(key, loggerActive);

    log.log("Step: Applying Li = Ri-1; Ri = Li-1 xor f(Ri-1, Ki)".black().bgWhite())

    for (i in 1..16) {

        log.seen = (i == 1 || i == 16) && loggerActive;
        log.log("Round $i:".red())
        log.log("Executing R$i = L${i-1} xor f(R${i-1}, K$i)")

        val fRezult = f(RCurrent, KList[i-1])

        log.log("f  = ${fRezult.toBitString().blue()}")
        log.log("L${i-1} = ${LCurrent.toBitString().cyan()}")
        log.log("R$i = L${i-1} xor f rezult")

        val RNext = LCurrent xor fRezult

        log.log("R$i = ${RNext.toBitString().blue()}")

        val LNext = RCurrent

        LCurrent = LNext
        RCurrent = RNext
    }

    val C = getCfromL16R16(LCurrent, RCurrent, loggerActive);

    return C
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
fun getKList(key: BooleanArray, loggerActive: Boolean = false): List<BooleanArray> {

    val log = Logger.instance
    log!!.seen = loggerActive
    log.log("Step: Getting subkeys. ".black().bgWhite())

    log.log("Making K+")
    log.log("Permutation table PC_1 = ".cyan())
    log.log(PC_1.toList().chunked(8).joinToString("\n") { it.joinToString(" ") }.purple())

    val KPlus = applyPermutation(PC_1, key); // KPlus becomes 56 bits

    log.log("KPlus = ${KPlus.toBitString().green()}")

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

        val CNext = CList.last().rotateLeftCyclic(shiftNumber)
        val DNext = DList.last().rotateLeftCyclic(shiftNumber)

        CList.add(CNext)
        DList.add(DNext)
        KList.add(applyPermutation(PC_2, CNext + DNext))

    }

    return KList.toList();
}



/**
 * @param Rn - has 32 bits
 * @param Kn_plus_1 - has 48 bits
 * @return 32 bit result
 */
internal fun f(Rn: BooleanArray, Kn_plus_1: BooleanArray): BooleanArray {

    val log = Logger.instance
    log!!.log("Step: f ".white().bgBlack())

    log.log("Rn extension using E table: ")
    log.log(E.toList().chunked(8).joinToString("\n") { it.joinToString(" ") }.purple())

    val R_E_permuted = applyPermutation(E, Rn) // extends R to 48 bits
    log.log("R with permutation: ${R_E_permuted.toBitString().cyan()}")
    log.log("Kn                : ${Kn_plus_1.toBitString().green()}")

    val R_Permuted_XOR_ed = R_E_permuted xor Kn_plus_1
    log.log("R_E_permut XOR Kn : ${R_Permuted_XOR_ed.toBitString().yellow()}")

    val B_blocks = R_Permuted_XOR_ed.toList().chunked(6).map { it.toBooleanArray() } // 48 → 8 blocks of 6 bits
    log.log("B blocks: ${B_blocks.map { it.toBitString().red() }}")

    val sBoxOutput = B_blocks.mapIndexed { i, b ->
        val sBox = S_BOXES[i]

        log.log("\nS${i + 1} table:".cyan())
        log.log(
            sBox.joinToString("\n") { row ->
                row.joinToString(" ") { "%2d".format(it) }
            }.purple()
        )

        log.log("S${i + 1} input : ${b.toBitString().yellow()}")
        val sResult = apply_S_box(b, i + 1)
        log.log("S${i + 1} output: ${sResult.toBitString().green()} (${Integer.parseInt(sResult.toBitString(), 2)})")
        sResult.toList()
    }.flatten().toBooleanArray()

    log.log("Applying P permutation to result (S1(B1)...S8(B8)): ${sBoxOutput.toBitString().cyan()}")
    log.log(P.toList().chunked(8).joinToString("\n") { it.joinToString(" ") }.purple())

    val PPermRezult = applyPermutation(P, sBoxOutput)
    log.log("Permutation result: ${PPermRezult.toBitString().yellow()}")
    return PPermRezult
}

fun getCfromL16R16(L16: BooleanArray, R16: BooleanArray, loggerActive: Boolean = false): BooleanArray {

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
