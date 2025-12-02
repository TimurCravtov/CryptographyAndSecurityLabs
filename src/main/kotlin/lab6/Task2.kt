package lab6

import lab5.algorithms.ElGamalPublicData
import lab5.algorithms.ElGamalSetupData
import lab5.algorithms.randomInt
import java.math.BigInteger
import util.minus

fun main() {

    val p = BigInteger("32317006071311007300153513477825163362488057133489075174588434139269806834136210002792056362640164685458556357935330816928829023080573472625273554742461245741026202527916572972862706300325263428213145766931414223654220941111348629991657478268034230553086349050635557712219187890332729569696129743856241741236237225197346402691855797767976823014625397933058015226858730761197532436467475855460715043896844940366130497697812854295958659597567051283852132784468522925504568272879113720098931873959143374175837826000278034973198552060607533234122603254684088120031105907484281003994966956119696956248629032338072839127039")
    val g = BigInteger.valueOf(2)

    val privateKeyElGamal = randomInt(BigInteger.ONE, p - 1);

    val setup: ElGamalSetupData = ElGamalSetupData(p, g, privateKeyElGamal)
    val publicKeys: ElGamalPublicData = setup.toPublic()

    val hash = hash(messageFromLab2, "SHA-512")
    val hashNum = BigInteger(hash)

    val k = randomInt(BigInteger.valueOf(1), p - 1);
    val r = g.modPow(k, p)

    val s = ((hashNum - privateKeyElGamal * r) * k.modInverse(p - 1)).mod(p - 1)

    val signature = ElGamalSignature(r, s);

    // receiver

    val v1 = (publicKeys.beta.modPow(signature.r, p) * r.modPow(signature.s, p)).mod(p)
    val v2 = g.modPow(hashNum, p)

    println(v1 == v2)


}

data class ElGamalSignature(val r: BigInteger, val s: BigInteger)

val p = 1;