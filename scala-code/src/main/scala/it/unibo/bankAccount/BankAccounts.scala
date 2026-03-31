package it.unibo.bankAccount

trait Showable[A]:
  def show(a: A): String

def log[A: Showable](a: A): Unit = println(summon[Showable[A]].show(a))

object Euros:
  // Type
  opaque type Euro = Int
  // Operation
  extension (e: Euro)
    def +(other: Euro): Euro = e + other
    def -(other: Euro): Euro = e - other
    def *(factor: Double): Euro = (e * factor).toInt
    def <=(other: Euro): Boolean = euroOrdering.lteq(e, other)
    def >(other: Euro): Boolean = !e.<=(other)

  object Euro:
    // Constructor
    def apply(euros: Int, cents: Int): Euro =
      require(euros >= 0 && cents >= 0 && cents < 100, "Invalid euro amount")
      euros * 100 + cents
    def zero: Euro = apply(0, 0)
  // Givens
  given euroOrdering: Ordering[Euro] = Ordering.Int
  given euroShow: Showable[Euro] with
    def show(e: Euro): String = s"E${e / 100}.${e % 100}"

end Euros

object Banks:
  import Euros.{*, given}

  trait BankAccount:
    def balance: Euro
    def deposit(amount: Euro): Unit
    def withdraw(amount: Euro): Unit

  object BankAccount:
    def goldAccount(): BankAccount =
      val gold: FeeCalculator = _ => Euro.zero
      SimpleBankAccount(using gold)

    def silverAccount(): BankAccount =
      val silver: FeeCalculator = {
        case amount if amount > Euro(100, 0) => amount * 0.1
        case _                               => Euro.zero
      }
      SimpleBankAccount(using silver)

    def bronzeAccount(): BankAccount =
      val bronze: FeeCalculator = _ * 0.1
      SimpleBankAccount(using bronze)

  private type FeeCalculator = Euro => Euro

  private class SimpleBankAccount(using fee: FeeCalculator) extends BankAccount:
    private var _balance: Euro = Euro.zero

    def balance: Euro = _balance

    def deposit(amount: Euro): Unit =
      _balance = balance + amount

    def withdraw(amount: Euro): Unit =
      val feeAmount = fee(amount)
      require((amount + feeAmount) <= balance, "Insufficient funds")
      _balance = balance - amount - feeAmount

  given Showable[BankAccount] with
    def show(b: BankAccount): String = s"Amount: ${euroShow.show(b.balance)}"

end Banks

@main def testEuros(): Unit =
  import Euros.*

  val e0 = Euro.zero
  val e1 = Euro(1, 50)
  val e2 = Euro(2, 50)
  val e3 = e1 + e2
  log(e0) // E0.00
  log(e1) // E1.50
  log(e2) // E2.50
  log(e3) // E4.00

@main def testBank(): Unit =
  import Banks.*
  import Euros.*
  import BankAccount.*

  val a: BankAccount = bronzeAccount()
  log:
    a // Amount: E0.00
  log:
    a.deposit(Euro(100, 0))
    a // Amount: E100.00
  log:
    a.withdraw(Euro(90, 0))
    a // Amount: E1.00  (10% fee applied)
