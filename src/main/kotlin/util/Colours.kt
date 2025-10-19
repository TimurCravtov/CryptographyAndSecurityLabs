package util

fun String.black() = "\u001B[30m$this\u001B[0m"
fun String.red() = "\u001B[31m$this\u001B[0m"
fun String.green() = "\u001B[32m$this\u001B[0m"
fun String.yellow() = "\u001B[33m$this\u001B[0m"
fun String.blue() = "\u001B[34m$this\u001B[0m"
fun String.purple() = "\u001B[35m$this\u001B[0m"
fun String.cyan() = "\u001B[36m$this\u001B[0m"
fun String.white() = "\u001B[37m$this\u001B[0m"

fun String.bgBlack() = "\u001B[40m$this\u001B[0m"
fun String.bgWhite() = "\u001B[47m$this\u001B[0m"
