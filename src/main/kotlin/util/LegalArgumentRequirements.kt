package util

fun String.isHexString(): Boolean {

    if (this.isEmpty()) return false
    return this.all { it in '0'..'9' || it in 'a'..'f' || it in 'A'..'F' }
}
