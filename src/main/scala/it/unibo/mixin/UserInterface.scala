package it.unibo.mixin

/**
 * We would like to create a UserInterface library providing widgets
 * to be used in GUI.
 * An example of widget is a Button that when clicked executes
 * a series of actios and updates the gui.
 */

object UserInterface:
  /** Represents a generic widget in the GUI. */
  trait Widget

  /** Observer in Observer pattern. */
  trait Observer[State]:
    def receiveUpdate(state: State): Unit

  /**
   * Subject in Observer pattern.
   * This trait is a mixin, that is, it needs to be mixed in with a concrete
   * [[State]] object.
   * It adds the observers notification behavior to each object that is observed.
   */
  trait Subject[State]:
    this: State =>
    private var observers: Vector[Observer[State]] = Vector.empty
    def addObserver(observer: Observer[State]): Unit =
      observers.synchronized { observers :+= observer }
    def notifyObservers(): Unit = observers foreach ( _.receiveUpdate(this) )

  /**
   * Represents a gui element with the ability to be clickable.
   * It is a mixin, with the ability to add clickable behavior to a gui widget.
   * */
  trait Clickable:
    def click(): Unit = updateUI()
    protected def updateUI(): Unit

  /**
   * Add subject functionality to a [[Clickable]].
   * It is Stackable trait, it wraps Clickable mixin to add observable behavior on clicks.
   * */
  trait ObservableClicks extends Clickable with Subject[Clickable]:
    abstract override def click(): Unit =
      super.click()
      notifyObservers()

  /** A Button is a [[Widget]] with the ability to be [[Clickable]]/ */
  abstract class Button extends Widget with Clickable

  /** A simple labelled [[Button]]. */
  class LabelledButton(initialLabel: String) extends Button:
    private var _label = initialLabel
    def label: String = _label
    override protected def updateUI(): Unit = _label = "Clicked!"

end UserInterface

import UserInterface.*

/** A simple observer for [[Clickable]] widgets,
  * with the ability to count how many times the [[Clickable]] has been clicked.*/
class ClickCounter extends Observer[Clickable]:
  var count = 0
  override def receiveUpdate(state: Clickable): Unit = count += 1

@main def testUserInterface(): Unit =
  val button = new LabelledButton("Click me!") with ObservableClicks
  val counter = ClickCounter()
  button.addObserver(counter)
  (1 to 5) foreach (_ => button.click())
  println(s"Button has been clicked ${counter.count} times")