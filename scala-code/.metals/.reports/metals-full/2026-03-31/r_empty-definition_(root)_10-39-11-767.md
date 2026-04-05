error id: file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala:it/unibo/oopVsFp/Euros#
file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol it/unibo/oopVsFp/Euros#
empty definition using fallback
non-local guesses:

offset: 106
uri: file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala
text:

```scala
package it.unibo.bankAccount

trait Euros:
  type Euro
  extension (e: Euro) def +(other: Euro): Euro

end Euro @@ s

object EurosOOP:
  /** Euro type.
   */
  case class Euro(private val cents: Int):
    /** Sum current [[Euro]] with [[other]].
     */
    def +(other: Euro): Euro = Euro(cents + other.cents)

    override def toString(): String = s"E${cents / 100}.${cents % 100}"

  object Euro:
    /** Factory method to build [[Euro]] from [[euros]] and [[cents]].
     */
    def apply(euros: Int, cents: Int): Euro =
      require(euros >= 0 && (cents >= 0 && cents < 100))
      Euro(euros * 100 + cents)

end EurosOOP

object EurosFP:
  /** Euro Type.
   */
  opaque type Euro = Int

  object Euro:
    /** Build an [[Euro]] from [[euros]] and [[cents]].
     */
    def apply(euros: Int, cents: Int): Euro =
      require(euros >= 0 && (cents >= 0 && cents < 100))
      euros * 100 + cents

    extension (e: Euro)
      /** Sum [[e]] with [[other]].
       */
      def +(other: Euro): Euro = e + other

      /** [[e]]' [[String]] representation.
       */
      def show: String = s"E${e / 100}.${e % 100}"
end EurosFP

@main def testEuros =
  import EurosOOP.*
  val e1: Euro = Euro(1, 50)
  val e2: Euro = Euro(2, 50)
  // TODO: Why those functions are visible if Euro companion object is not imported.
  val e3: Euro = e1 + e2
  println(e1)
  println(e2)
  println(e3)

```


#### Short summary: 

empty definition using pc, found symbol in pc: 
