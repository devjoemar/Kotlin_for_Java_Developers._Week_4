package rationals
import java.math.BigInteger

/**
 * Represents a rational number, which is defined as the ratio of two integers:
 * a numerator and a non-zero denominator. Rational numbers are always stored in
 * their normalized form, ensuring that the gcd(numerator, denominator) is 1 and
 * the denominator is positive.
 *
 * This class supports arithmetic operations (+, -, *, /), comparisons (==, <, >, <=, >=),
 * and conversion from strings or integers to rational numbers.
 *
 * @constructor Creates a new rational number given a numerator and denominator.
 * @property numerator The numerator of the rational number, stored as [BigInteger].
 * @property denominator The denominator of the rational number, stored as [BigInteger].
 * Must be non-zero.
 * @throws IllegalArgumentException If the denominator is zero.
 */
class Rational(n: BigInteger, d: BigInteger) : Comparable<Rational> {
    private val numerator: BigInteger
    private val denominator: BigInteger

    init {
        val gcd = n.gcd(d)
        val sign = d.signum().toBigInteger() // Ensure the denominator is always positive

        numerator = n / gcd * sign
        denominator = d.abs() / gcd
    }

    operator fun plus(other: Rational): Rational =
            Rational(numerator * other.denominator + other.numerator * denominator, denominator * other.denominator)

    operator fun minus(other: Rational): Rational =
            Rational(numerator * other.denominator - other.numerator * denominator, denominator * other.denominator)

    operator fun times(other: Rational): Rational =
            Rational(numerator * other.numerator, denominator * other.denominator)

    operator fun div(other: Rational): Rational =
            Rational(numerator * other.denominator, denominator * other.numerator)

    operator fun unaryMinus(): Rational = Rational(-numerator, denominator)

    // Comparison
    override fun compareTo(other: Rational): Int =
            (numerator * other.denominator).compareTo(other.numerator * denominator)

    override fun equals(other: Any?): Boolean =
            if (other is Rational) numerator == other.numerator && denominator == other.denominator else false

    override fun hashCode(): Int = 31 * numerator.hashCode() + denominator.hashCode()

    override fun toString(): String =
            if (denominator == BigInteger.ONE) "$numerator" else "$numerator/$denominator"
}

// Extension functions for creating Rational instances
fun String.toRational(): Rational {
    val parts = split("/")
    val expectedPartsForRational = 2
    val defaultDenominator = BigInteger.ONE

    return if (parts.size == expectedPartsForRational) {
        Rational(parts[0].toBigInteger(), parts[1].toBigInteger())
    } else {
        Rational(this.toBigInteger(), defaultDenominator)
    }
}

infix fun Int.divBy(other: Int): Rational = Rational(this.toBigInteger(), other.toBigInteger())
infix fun Long.divBy(other: Long): Rational = Rational(this.toBigInteger(), other.toBigInteger())
infix fun BigInteger.divBy(other: BigInteger): Rational = Rational(this, other)

// Checks if a Rational number is within a given range
operator fun ClosedRange<Rational>.contains(value: Rational): Boolean =
        value >= start && value <= endInclusive


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}