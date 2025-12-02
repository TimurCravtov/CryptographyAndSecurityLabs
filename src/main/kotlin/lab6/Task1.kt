package lab6

import lab4.util.hexToBooleanArray
import lab4.util.toBitString
import lab5.algorithms.decryptRsa
import lab5.algorithms.encryptRsa
import lab5.algorithms.keyGen
import lab5.algorithms.messageToBigInt
import java.math.BigInteger
import java.security.MessageDigest

fun main() {

    // sender
    val keys = keyGen(nMin = 3072)

    val hash = hash(messageFromLab2, "SHA3-224")
    val signature = encryptRsa(BigInteger(hash), keys.privateKey, keys.n)

    // receiver

    val decryptedSignature = decryptRsa(signature, keys.publicKey, keys.n)

    val hashReceiver = hash(messageFromLab2, "SHA3-224")
    val hashNum = BigInteger(hashReceiver)


    println(hashNum == decryptedSignature)
}

fun hash(message: String, alg: String): ByteArray {

    val digest = MessageDigest.getInstance(alg)
    val hash = digest.digest(message.toByteArray())
    return hash

}

val messageFromLab2 = "it ran with almost unbelievable efficiency. the bags of mail for deliverythat morning to the\n" +
        "embassies in vienna were brought to the blackchamber each day at 7 a.m. there the letters were\n" +
        "opened by meltingtheir seals with a candle. the order of the letters in an envelope wasnoted\n" +
        "and the letters given to a subdirector. he read them and orderedthe important parts copied.\n" +
        "all the employees could write rapidly, andsome knew shorthand. long letters were dictated to\n" +
        "save time,sometimes using four stenographers to a single letter. if a letter was in alanguage\n" +
        "that he did not know, the subdirector gave it to a cabinetemployee familiar with it. two\n" +
        "translators were always on hand. alleuropean languages could be read, and when a new one was needed,\n" +
        "anofficial learned it. armenian, for ejample, took one cabinet polyglot onlya few months to learn,\n" +
        "and he was paid the usual 500 florins for his newknowledge. after copying, the letters were\n" +
        "replaced in their envelopes intheir original order and the envelopes re-sealed, using forged\n" +
        "seals toimpress the original waj. the letters were returned to the post office by9:30 a.m.at\n" +
        "10 a.m., the mail that was passing through this crossroads of thecontinent arrived and was\n" +
        "handled in the same way, though with lesshurry because it was in transit. usually it would be\n" +
        "back in the post by 2p.m., though sometimes it was kept as late as 7 p.m. at 11\n" +
        "a.m.,interceptions made by the police for purposes of political surveillancearrived. and at 4\n" +
        "p.m., the couriers brought the letters that theembassies were sending out that day. these\n" +
        "were back in the stream ofcommunications by 6:30 p.m. copied material was handed to thedirector\n" +
        "of the cabinet, who ejcerpted information of special interest androuted it to the proper\n" +
        "agencies, as police, army, or railwayadministration, and sent the mass of diplomatic material to\n" +
        "the court. all told, the ten-man cabinet handled an average ofbetween 80 and 100 letters a\n" +
        "day.astonishingly, their nimble fingers hardly ever stuffed letters into thewrong packet,\n" +
        "despite the speed with which they worked. in one of thefew recorded blunders, an intercepted\n" +
        "letter to the duke of modena waserroneously re-sealed with the closely similar signet of parma.\n" +
        "when theduke noticed the substitution, he sent it to parma with the wry note, \"notjust\n" +
        "me—you too.\" both states protested, but the viennese greeted themwith a blank stare, a\n" +
        "shrug, and a bland profession of ignorance. despitethis, the ejistence of the black chamber was\n" +
        "well known to the variousdelegates to the austrian court, and was even tacitly acknowledged\n" +
        "bythe austrians. when the british'ambassador complained humorously that he was getting\n" +
        "copiesinstead of his original correspondence, the chancellor replied coolly,\"how clumsy these\n" +
        "people are!\"enciphered correspondence was subjected to the usual cryptanalyticsweating\n" +
        "process. the viennese enjoyded remarkable success in this work.the french ambassador, who was\n" +
        "apprised of its successes from paperssold him by a masked man on a bridge, remarked in astonishment\n" +
        "that\"our ciphers of 1200 [groups] hold out only a little while against theability of the\n" +
        "austrian decipherers.\" he added that though he suggestednew ways of ciphering and continual\n" +
        "changes of ciphers, \"i still findmyself without secure means for the secrets i have to\n" +
        "transmit toconstantinople, stockholm, and st. petersburg.\""