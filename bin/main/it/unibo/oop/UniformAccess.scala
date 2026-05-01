package it.unibo.oop

object UniformAccess:
  class A:
    // public mutable field
    // exposing field as var does not break user code if we want to change it.
    private var _field = 10
    def field = _field
    def field_=(value: Int): Unit = { require(value >= 0); _field = value }
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
    def +(i: Int): A = { field = field + i; this }
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
  // left associative
  // ((((a+1)+2)+3)+4)
  a + 1 + 2 + 3 + 4 // a.+(1).+(2).+(3).+(4)
  // right associative
  // (4+(3+(2+(1+a))))
  4 +: 3 +: 2 +: 1 +: a // a.+:(1).+:(2).+:(3).+:(4)
