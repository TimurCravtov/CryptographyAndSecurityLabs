package lab3

internal fun filler(doubledLetter: Char) = if (doubledLetter == 'X') 'Y' else 'X'

fun String.code(char: Char) = this.indexOf(char.uppercaseChar())

fun String.encryptPlayfair(alphabet: String, key: String, rows: Int, columns: Int): String {

    val preprocessedMessage = preprocessPlayfair(this)
    val square = generateSquare(alphabet, key, rows, columns)

    val chunks = preprocessedMessage.chunked(2).map {it.toMutableList()}

    for (chunk in chunks) {

        val coords1 = square.coordinates(chunk[0]);
        val coords2 = square.coordinates(chunk[1]);


        // if they are in the same row
        if (coords1.first == coords2.first) {
            chunk[0] = square[coords1.first][square.nextColumn(coords1.second)]
            chunk[1] = square[coords2.first][square.nextColumn(coords2.second)]

        // if they are in the same column
        } else if (coords1.second == coords2.second) {
            chunk[0] = square[square.nextRow(coords1.first)][coords1.second]
            chunk[1] = square[square.nextRow(coords2.first)][coords1.second]

        } else {

            // if rectangle, swap columns
            chunk[0] = square[coords1.first][coords2.second]
            chunk[1] = square[coords2.first][coords1.second]

        }

    }

    return chunks.flatten().joinToString("")
}

fun String.decryptPlayfair(alphabet: String, key: String, rows: Int, columns: Int, removeFillers: Boolean = true): String {

    val square = generateSquare(alphabet, key, rows, columns)

    // split message into pairs
    val chunks = this.chunked(2).map { it.toMutableList() }

    for (chunk in chunks) {

        val coords1 = square.coordinates(chunk[0])
        val coords2 = square.coordinates(chunk[1])

        // same row -> move left
        if (coords1.first == coords2.first) {
            chunk[0] = square[coords1.first][square.prevColumn(coords1.second)]
            chunk[1] = square[coords2.first][square.prevColumn(coords2.second)]

            // same column -> move up
        } else if (coords1.second == coords2.second) {
            chunk[0] = square[square.prevRow(coords1.first)][coords1.second]
            chunk[1] = square[square.prevRow(coords2.first)][coords2.second]

            // rectangle -> swap columns
        } else {
            chunk[0] = square[coords1.first][coords2.second]
            chunk[1] = square[coords2.first][coords1.second]
        }
    }

    var result = chunks.flatten().joinToString("")
    if (removeFillers) result = result.removePlayfairFillers()
    return result;
}


internal fun String.removePlayfairFillers(
    isFiller: (Char) -> Boolean = { it == 'X' || it == 'Y' }
): String {
    val result = StringBuilder()
    val pairs = this.chunked(2)

    for ((index, pair) in pairs.withIndex()) {
        if (index == 0) {
            result.append(pair)
            continue
        }

        val prev = pairs[index - 1]
        val prevSecond = prev.getOrNull(1)
        val currFirst = pair.first()

        if (prevSecond != null && isFiller(prevSecond) && prev.first() == currFirst) {

            result.setLength(result.length - 1)
            result.append(currFirst)
            if (pair.length == 2) result.append(pair[1])
        } else {
            result.append(pair)
        }
    }

    return result.toString()
}


/**
 * returns <row, column>
 */
internal fun Array<Array<Char>>.coordinates(char: Char): Pair<Int, Int> {
    for (rowIndex in this.indices) {
        val row = this[rowIndex]
        for (colIndex in row.indices) {
            if (row[colIndex] == char) {
                return rowIndex to colIndex
            }
        }
    }
    throw NoSuchElementException("Character '$char' not found in the array")
}

internal fun Array<Array<Char>>.nextColumn(column: Int) = (column + 1) % this[0].size
internal fun Array<Array<Char>>.nextRow(row: Int) = (row + 1) % this.size

internal fun Array<Array<Char>>.prevColumn(column: Int): Int = (column - 1 + this[0].size) % this[0].size
internal fun Array<Array<Char>>.prevRow(row: Int): Int = (row - 1 + this.size) % this.size


internal fun preprocessPlayfair(
    message: String,
    filler: (Char) -> Char = { if (it == 'X') 'Y' else 'X' }
): String {
    val processed = mutableListOf<Char>()

    for (c in message) {
        if (processed.isNotEmpty() && processed.last() == c && processed.size % 2 != 0) {
            processed.add(filler(c))
        }
        processed.add(c)
    }

    if (processed.size % 2 == 1) {
        processed.add(filler(processed.last()))
    }

    return processed.joinToString("")
}



internal fun generateSquare(alphabet: String, key: String, rows:Int = 5, columns: Int = 5): Array<Array<Char>> {

    if (!key.all { it in alphabet }) throw IllegalArgumentException("All the letters in the key needs to be in the alphabet")
    if (alphabet.length != rows * columns) throw IllegalArgumentException("The alphabet length should equal the rows * columns")

    val combined = (key + alphabet).toList().distinct()

    return combined.chunked(columns)
        .map { chunk -> chunk.toTypedArray() }
        .toTypedArray()

}