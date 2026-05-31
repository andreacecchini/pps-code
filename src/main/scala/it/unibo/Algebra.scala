package it.unibo

import it.unibo.tailrec.gdc
import Algebra.Complexes.Complex.rect

/** A module for generic algebra. */
object Algebra:
  /** Relational Algebra.
    */
  object Rationals:
    // Rational ADT (one representation, hidden from the user)
    opaque type Rational = Long
    object Rational:
      // Factory
      def apply(num: Int, denom: Int): Rational =
        require(denom != 0, "Denom can not be zero")
        def pack(num: Int, denom: Int): Rational =
          (num.toLong << 32) | (denom.toLong & 0xffffffffL)
        val sign = if denom < 0 then -1 else 1
        val g = gdc(math.abs(num), math.abs(denom))
        pack(sign * num / g, sign * denom / g)
      // Selectors
      def unapply(r: Rational): Some[(Int, Int)] =
        Some((r >> 32).toInt, (r & 0xffffffffL).toInt)
      // Operations
      extension (r: Rational)
        def +(that: Rational): Rational = (r, that) match
          case (Rational(n1, d1), Rational(n2, d2)) =>
            Rational(n1 * d2 + n2 * d1, d1 * d2)
        def -(that: Rational): Rational = (r, that) match
          case (Rational(n1, d1), Rational(n2, d2)) =>
            Rational(n1 * d2 - n2 * d1, d1 * d2)
        def *(that: Rational): Rational = (r, that) match
          case (Rational(n1, d1), Rational(n2, d2)) =>
            Rational(n1 * n2, d1 * d2)
        def rec: Rational = r match
          case Rational(n, d) => Rational(d, n)
        def /(that: Rational): Rational =
          Rational.*(r)(that.rec)
        def unary_- : Rational = r match
          case Rational(n, d) => Rational(-n, d)
        def show: String = r match
          case Rational(n, d) => s"$n/$d"

  /** Complex algebra.
    */
  object Complexes:
    // Complex ADT (multiple representations at the same type)
    opaque type Complex = Rect | Polar
    private case class Rect(re: Double, im: Double)
    private case class Polar(mag: Double, ang: Double)
    object Complex:
      // Factories
      def rect(re: Double, im: Double): Complex = Rect(re, im)
      def polar(mag: Double, ang: Double): Complex = Polar(mag, ang)
      extension (z: Complex)
        // Selectors
        def real: Double = z match
          case Rect(re, _)   => re
          case Polar(m, ang) => m * math.cos(ang)
        def imag: Double = z match
          case Rect(_, im)   => im
          case Polar(m, ang) => m * math.sin(ang)
        def magnitude: Double = z match
          case Rect(re, im) => math.hypot(re, im)
          case Polar(m, _)  => m
        def angle: Double = z match
          case Rect(re, im)  => math.atan2(im, re)
          case Polar(_, ang) => ang
        // Operations
        def +(that: Complex): Complex =
          rect(z.real + that.real, z.imag + that.imag)
        def unary_- : Complex =
          rect(-z.real, -z.imag)
        def -(that: Complex): Complex =
          z + (-that)
        // ... (others operations may be more easily if polar representation is used.)
end Algebra
