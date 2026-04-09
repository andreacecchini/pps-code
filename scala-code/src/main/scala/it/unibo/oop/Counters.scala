package it.unibo.oop

object Counters:
  /**
   * Represents a counter.
   * */
  trait Counter:
    /**
     * Increments the counter.
     * */
    def increment(): Unit

    /**
     * Returns the counter value.
     * */
    def value: Int

  /**
   * A basic [[Counter]] implementation.
   * */
  class CounterImpl extends Counter:
    private var _value: Int = 0

    override def increment(): Unit = _value = value + 1

    override def value: Int = _value

end Counters

@main def testCounter(): Unit =
  import Counters.*
  val counter: Counter = new CounterImpl
  println:
    counter.value
  println:
    counter.increment()
    counter.value
