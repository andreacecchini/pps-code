package it.unibo.oop

object Lamps:
  /**
   * Represents a smart lamp.
   * */
  class Lamp(private var _state: Boolean): // primary constructor, `on` is not a field.
    private val firstUse: java.util.Date = new java.util.Date() // java interoperability.
    // floating operations.
    println("primary constructor of " + this)

    /**
     * Auxiliary constructor.
     * */
    def this() =
      // MUST call the primary.
      this(false)
      println("auxiliary constructor of " + this)

    /**
     * Checks if [[Lamp]] is on.
     * */
    def isOn: Boolean = _state

    /**
     * Toggle the [[Lamp]].
     * */
    def toggle(): Unit = _state = !isOn

    // overriding toString method.
    override def toString: String = s"Lamp $_state; first use $firstUse; identity ${super.toString}"
end Lamps

@main def testLamp(): Unit =
  import Lamps.*

  def useLamp(using lamp: Lamp): Unit =
    println:
      s"Lamp is ${if lamp.isOn then "on" else "off"}"
    println:
      lamp.toggle()
      s"Lamp is ${if lamp.isOn then "on" else "off"}"

  val initialState = false
  // Calling primary constructor.
  val lamp: Lamp = Lamp(initialState) // new is not mandatory => apply factory method.
  useLamp(using lamp)
  // Calling auxiliary constructor.
  val lamp2: Lamp = Lamp() // new is not mandatory => apply factory method.
  useLamp(using lamp2)


