package lab4.des

import java.util.BitSet

fun applyPermutation(table: IntArray, array: BooleanArray): BooleanArray {
    val result = BooleanArray(table.size)
    for (i in table.indices) {
        result[i] = array[table[i] - 1]
    }
    return result
}

fun unapplyPermutation(table: IntArray, array: BooleanArray): BooleanArray {
    val result = BooleanArray(array.size)
    for (i in table.indices) {
        result[table[i] - 1] = array[i]
    }
    return result
}

/**
 * @param boxNumber - actual DES box number, 1..8
 */
fun apply_S_box(block: BooleanArray, boxNumber: Int): BooleanArray {

    val row = (if (block[0]) 2 else 0) + (if (block[5]) 1 else 0)
    val column = (if (block[1]) 8 else 0) +
            (if (block[2]) 4 else 0) +
            (if (block[3]) 2 else 0) +
            (if (block[4]) 1 else 0)

    val value = S_BOXES[boxNumber - 1][row][column]

    return BooleanArray(4) { bit ->
        (value shr (3 - bit) and 1) == 1 // 2bit => 0110 -> 0011 -> 0001 = 1 => (~, ~, 1, ~)
    }
}


