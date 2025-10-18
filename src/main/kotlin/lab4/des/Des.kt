package lab4.des

import lab4.util.xor
import java.util.BitSet

fun BooleanArray.encryptDes(key: BooleanArray): String {

    val KPlus = applyPermutation(PC1, key);

    val C0 = KPlus.slice(0..31)
    val D0 = KPlus.slice(31..63)

    for (i in 1..15) {
        val shift = when (i) {
            1, 2, 9, 16 -> 1
            else -> 2
        }

    }

    val roundKey = C0 + D0;

    return "";
}



fun BooleanArray.decryptDes(key: BooleanArray): Boolean {

}

internal fun BooleanArray.rotateLeft(bits: Int): BooleanArray {
    val size = this.size
    val result = BooleanArray(size)

    for (i in this.indices) {
        val newIndex = (i - bits + size) % size
        result[i] = this[newIndex]
    }
    return result
}

internal fun f(Rn: BooleanArray, Kn_plus_1: BooleanArray): BooleanArray {

    val R_E_permuted = applyPermutation(E, Rn);
    val R_Permuted_XOR_ed = R_E_permuted xor Kn_plus_1

    val B_blocks = R_Permuted_XOR_ed.toList().chunked(6).map {it.toBooleanArray() };

    return B_blocks
        .mapIndexed { i, b -> apply_S_box(b, i + 1).toList() }
        .flatten().toBooleanArray()
}

fun getCfromL16R16(L16: BooleanArray, R16: BooleanArray): BooleanArray {
    val R16L16 = R16 + L16
    val C = applyPermutation(IP_1, R16L16);
    return C;
}
