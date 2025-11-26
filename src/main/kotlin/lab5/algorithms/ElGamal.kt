package lab5.algorithms

import java.math.BigInteger
import java.security.PublicKey
import util.minus

fun encryptGamal(message: BigInteger, publicData: ElGamalPublicData): ElGamalEncryptionData {

    val (p, g, beta) = publicData

    val i = randomInt(BigInteger.valueOf(2), p-2)
    val ke = g.modPow(i, p)
    val km = beta.modPow(i, p)

    val y = (message * km).mod(p)

    return ElGamalEncryptionData(ke, y)

}

fun decryptElGamal(privateData: ElGamalSetupData, encData: ElGamalEncryptionData): BigInteger {

    val (kE, y) = encData

    val d = privateData.d
    val p = privateData.p

    val km = kE.modPow(d, p)
    val kminverse = km.modInverse(p)
    val x = (y * kminverse).mod(p)

    return x
}

data class ElGamalEncryptionData(val kE: BigInteger, val y: BigInteger)

data class ElGamalSetupData(
    val p: BigInteger,
    val g: BigInteger,
    val d: BigInteger
) {
    val beta: BigInteger = g.modPow(d, p)

    fun toPublic(): ElGamalPublicData =
        ElGamalPublicData(p, g, beta)

    companion object {
        fun from(p: BigInteger, g: BigInteger, d: BigInteger): ElGamalSetupData =
            ElGamalSetupData(p, g, d)
    }
}
data class ElGamalPublicData(
    val p: BigInteger,
    val g: BigInteger,
    val beta: BigInteger
)


fun keyGenElGamal() {
    val p = randomPrime(2048, 2060)
    val x = randomInt(BigInteger.valueOf(2), p - 2)
    val g = BigInteger.valueOf(2);
    val y = g.modPow(x, p)
}

fun randomInt(lowerIncluded: BigInteger, upperIncluded: BigInteger): BigInteger {
    var result: BigInteger
    do {
        result = BigInteger(upperIncluded.bitLength(), java.util.Random())
    } while (result !in lowerIncluded..upperIncluded)
    return result
}

data class PublicKeys(val p: BigInteger, val g: BigInteger, val y: BigInteger)