error id: file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala:
file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -EurosOOP.e3.cents.
	 -EurosOOP.e3.cents#
	 -EurosOOP.e3.cents().
	 -e3/cents.
	 -e3/cents#
	 -e3/cents().
	 -scala/Predef.e3.cents.
	 -scala/Predef.e3.cents#
	 -scala/Predef.e3.cents().
offset: 1421
uri: file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala
text:

```scala
package it.unibo.bankAccount

object EurosOOP:
  trait Euro:
    // TODO: We are exposing cents, should we avoid that?
    // def cents: Int

    /** Sum [[this]] with [[other]]. */
    def +(other: Euro): Euro

  object Euro:
    private case class EuroImpl(cents: Int) extends Euro {
      override def +(other: Euro): Euro = other match
        case EuroImpl(c) => EuroImpl(this.cents + c)

      override def toString(): String = s"E${cents / 100}.${cents % 100}"
    }

    /** Factory method to build [[Euro]] from [[euros]] and [[cents]]. */
    def apply(euros: Int, cents: Int): Euro =
      require(euros >= 0 && (cents >= 0 && cents < 100))
      EuroImpl(euros * 100 + cents)

end EurosOOP

object EurosFP:
  opaque type Euro = Int

  object Euro:
    /** Build an [[Euro]] from [[euros]] and [[cents]]. */
    def apply(euros: Int, cents: Int): Euro =
      require(euros >= 0 && (cents >= 0 && cents < 100))
      euros * 100 + cents

    extension (e: Euro)
      /** Sum [[e]] with [[other]]. */
      def +(other: Euro): Euro = e + other

      // [TODO]: Can't I have a toString?
      /** [[e]]' [[String]] representation. */
      def show: String = s"E${e / 100}.${e % 100}"
end EurosFP

@main def testEuros =
  import EurosOOP.*
  val e1: Euro = Euro(1, 50)
  val e2: Euro = Euro(2, 50)
  // TODO: Why those functions are visible if Euro companion object is not imported.
  val e3: Euro = e1 + e2
  e3.cen @@ ts
  println(e1)
  println(e2)
  println(e3)

```


#### Short summary: 

empty definition using pc, found symbol in pc: 
