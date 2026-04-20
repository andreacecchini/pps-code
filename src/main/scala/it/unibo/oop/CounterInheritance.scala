package it.unibo.oop

object CounterInheritance:

  trait Counter:
    def value: Int
    def increment(): Unit

  // implements Counter
  class CounterImpl(protected var _value: Int) extends Counter:
    override def value: Int = _value
    def increment(): Unit = _value = value + 1

  // extends Counter
  trait DecrementCounter extends Counter:
    def decrement(): Unit

  // extends CounterImpl implements DecrementCounter
  class DecrementCounterImpl(initial: Int) extends CounterImpl(initial) with DecrementCounter:
    def decrement(): Unit = _value = value - 1

end CounterInheritance

@main def testCounterInheritance(): Unit =
  import CounterInheritance.*
  val initialValue = 0
  val c1: Counter = CounterImpl(initialValue)
  println:
    c1.value
  println:
    c1.increment()
    c1.value
  val c2: DecrementCounter = DecrementCounterImpl(initialValue)
  println:
    c2.value
  println:
    c2.increment()
    c2.decrement()
    c2.value
