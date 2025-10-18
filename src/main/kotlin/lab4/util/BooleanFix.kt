package lab4.util

infix fun BooleanArray.xor(other: BooleanArray): BooleanArray {
    require(this.size == other.size) { "Arrays must be the same size" }
    return BooleanArray(this.size) { this[it] != other[it] }
}
