package lab3.utils

fun String.makeReplacements(replacements: Map<List<Char>, Char>): String {
    var result = this
    for ((chars, replacement) in replacements) {
        val pattern = chars.joinToString(separator = "", prefix = "[", postfix = "]") { Regex.escape(it.uppercase()) }
        result = result.replace(Regex(pattern), replacement.toString())
    }
    return result
}

fun String.normalizeForEncryption(alphabet: String, replacements: Map<List<Char>, Char>): String {

    var result = this.uppercase().makeReplacements(replacements) // with replacements
    result = result.replace(" ", ""); // without whitespaces
    val replacedAlphabet = alphabet.makeReplacements(replacements) // alphabet with replacements (just in case it's not already replaced)

    if (!result.all { it in replacedAlphabet }) {
        throw RuntimeException("String has wrong format. Only letters in [$alphabet] are allowed")
    }
    return result;
}


