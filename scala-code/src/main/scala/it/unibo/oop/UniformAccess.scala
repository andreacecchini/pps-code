package it.unibo.oop

object UniformAccess:
  class A:
    // public mutable field
    var field = 10
    // public method
    def meth(s: String): String = s + 10
    // can use symbols as method names
    def ***(s: String): String = s + 10
    // getter
    def prop: Int = field
    // setter
    def prop_=(value: Int): Unit = field = value
    // indexer-get (val el = arr[idx])
    def apply(index: Int): String = "indexer-get"
    // indexer-set (arr[idx] = el)
    def update(index: Int, s: String): Unit = println("indexer-set")
    // math-like operator
    def +:(i: Int): A = { field = field + i; this }
end UniformAccess

@main def testUniformAccess(): Unit =
  import UniformAccess.*
  // A.apply()
  val a = A()
  // access field
  a.field
  // property {get; set}
  a.prop
  a.prop = 5
  // method names
  a.meth("s")
  a *** "s" // a.***("s")
  // indexer {get; set}
  val el = a(1) // a.apply(1)
  a(1) = el // a.update(1, el)
