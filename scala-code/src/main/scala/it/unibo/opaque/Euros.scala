package it.unibo.opaque

object Euros:
  // opaque allows to hide the real implementation (the only one),
  // decoupling Euro from Int (they are not the same thing!)
  opaque type Euro = Int

  // A possible alternative, with Algebraic Data Type => Verbose!
  //  enum Euro:
  //    private case Amount(cents: Int)

  object Euro:
    /** building [[Euro]] from [[euro]] and [[cents]].ƒ
     * [[euro]] and [[cents]] must be positive and [[cents]] must be minor than [[100]].
     * */
    def fromEuroCents(euro: Int, cents: Int): Euro =
      require(euro >= 0 && cents >= 0 && cents < 100)
      euro * 100 + cents

    extension (e: Euro)
      def show: String = "E%d.%2d".format(e / 100, e % 100)

      infix def plus(other: Euro): Euro = e + other


@main def testEuros(): Unit =
  import Euros.*
  import Euro.*

  // val test: Euro = 150 // Should not compile! Euro is not an Int (externally)!
  val amount: Euro = fromEuroCents(1, 50)
  //  val negativeAmount: Euro = fromEuroCents(-1, 0)
  println(amount.show) // E1.50
  val amount2: Euro = fromEuroCents(1, 0)
  val amount3 = amount plus amount2
  println(amount3.show) // E2.50

