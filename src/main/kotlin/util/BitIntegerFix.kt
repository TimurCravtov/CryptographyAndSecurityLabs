package util

import java.math.BigInteger

operator fun BigInteger.minus(other: Int): BigInteger =
    this.subtract(other.toBigInteger())
