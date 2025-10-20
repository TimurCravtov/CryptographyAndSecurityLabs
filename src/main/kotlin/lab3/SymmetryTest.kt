package lab3

import lab3.utils.makeReplacements
import lab3.utils.normalizeForEncryption
import util.alphabets.romanianAlphabet
import util.readUntilConditionSatisfy

fun main() {

    val replacements = mapOf(
        listOf('I', 'Î') to 'I'
    )

    val alphabet = romanianAlphabet.makeReplacements(replacements).toList().distinct().joinToString("")

    val k = readUntilConditionSatisfy(
        prompt = "Introduceți cheia: ",
        parser = { it.normalizeForEncryption(alphabet, replacements) },
        condition= { it.length > 7}
    )

    val message = readUntilConditionSatisfy(
        prompt = "Introduceți mesajul: ",
        parser = { it.normalizeForEncryption(alphabet, replacements) },
        condition= { true }
    )

    val enc = message.encryptPlayfair(alphabet, k, 6, 5)
    println("Mesajul encriptat: $enc" )
    val dec = enc.decryptPlayfair(alphabet, k, 6, 5)
    println("Mesajul decryptat: $dec" )


}