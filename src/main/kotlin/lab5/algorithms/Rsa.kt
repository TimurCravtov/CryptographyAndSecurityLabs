package lab5.algorithms

import lab1.common.code
import java.math.BigInteger
import kotlin.random.Random
import util.minus
import java.nio.charset.Charset
import javax.crypto.KeyGenerator

fun randomPrime(nBitsMin: Int, nBitsMax: Int): BigInteger {
    val bitNumber = Random.nextInt(nBitsMin, nBitsMax)
    return BigInteger.probablePrime(bitNumber, java.util.Random());
}

fun messageToBigInt(message: String): BigInteger {
    return BigInteger(1, message.toByteArray())
}


fun encryptRsa(message: BigInteger, publicKey: BigInteger, n: BigInteger): BigInteger {

    require(message.bitLength() <= 2048)

    val cipherText = message.modPow(publicKey, n)

    return cipherText

}
fun decryptRsa(encryptedMessage: BigInteger, privateKey: BigInteger, n: BigInteger): String {

    val message = encryptedMessage.modPow(privateKey, n)

    return bigIntToAsciiString(message)
}

fun bigIntToAsciiString(bigInt: BigInteger): String {
    return bigInt.toByteArray().toString(Charset.defaultCharset())
}



fun keyGen(isPublicKeyDefault: Boolean = true): KeyGenData {
    val p = randomPrime(1024, 2100)
    var q = randomPrime(1024, 2100)

    while (p == q) {
        q = randomPrime(1024, 2100)
    }

    val n = p * q
    val phi = (p - BigInteger.ONE) * (q - BigInteger.ONE)

    val e = if (isPublicKeyDefault)
        BigInteger.valueOf(65537)
    else
        generateSequence { randomInt(BigInteger.TWO, phi - BigInteger.ONE) }
            .first { it.gcd(phi) == BigInteger.ONE }

    val d = e.modInverse(phi)

    return KeyGenData(e, d, n)
}

data class KeyGenData(val publicKey: BigInteger, val privateKey: BigInteger, val n: BigInteger)
