error id: 5A220C98CD7E916C684F86A6675E9E6C
file://<WORKSPACE>/src/main/scala/it/unibo/bankAccount/BankAccounts.scala
### java.lang.StringIndexOutOfBoundsException: Index 0 out of bounds for length 0

occurred in the presentation compiler.



action parameters:
offset: 2087
uri: file://<WORKSPACE>/src/main/scala/it/unibo/bankAccount/BankAccounts.scala
text:
```scala
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
    def show(b: BankAccount): String = s"Amount: ${lo@@(b.balance)}"

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

```


presentation compiler configuration:
Scala version: 3.8.1-bin-nonbootstrapped
Classpath:
<WORKSPACE>/.bloop/root/bloop-bsp-clients-classes/classes-Metals-7LJWf7oQQEipt6U8LiGKfA== [exists ], <HOME>/Library/Caches/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.11.2/semanticdb-javac-0.11.2.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.8.1/scala3-library_3-3.8.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/3.8.1/scala-library-3.8.1.jar [exists ], <WORKSPACE>/.bloop/root/bloop-bsp-clients-classes/classes-Metals-7LJWf7oQQEipt6U8LiGKfA==/META-INF/best-effort [missing ]
Options:
-Xsemanticdb -sourceroot <WORKSPACE> -Ywith-best-effort-tasty




#### Error stacktrace:

```
java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:55)
	java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:52)
	java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:213)
	java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:210)
	java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:98)
	java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
	java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
	java.base/java.lang.String.checkIndex(String.java:4917)
	java.base/java.lang.StringLatin1.charAt(StringLatin1.java:46)
	java.base/java.lang.String.charAt(String.java:1616)
	scala.meta.internal.metals.Fuzzy.matchesName(Fuzzy.scala:293)
	scala.meta.internal.metals.Fuzzy.$anonfun$2(Fuzzy.scala:76)
	scala.meta.internal.metals.Fuzzy.$anonfun$adapted$2(Fuzzy.scala:76)
	scala.meta.internal.metals.Fuzzy.loopDelimiters$1(Fuzzy.scala:100)
	scala.meta.internal.metals.Fuzzy.genericMatches(Fuzzy.scala:127)
	scala.meta.internal.metals.Fuzzy.matches(Fuzzy.scala:77)
	dotty.tools.pc.completions.Completions.fuzzyMatcher$lzyINIT1$$anonfun$1(Completions.scala:119)
	dotty.tools.dotc.interactive.Completion$Completer.dotty$tools$dotc$interactive$Completion$Completer$$include(Completion.scala:672)
	dotty.tools.dotc.interactive.Completion$Completer$$anon$5.applyOrElse(Completion.scala:703)
	dotty.tools.dotc.interactive.Completion$Completer$$anon$5.applyOrElse(Completion.scala:702)
	scala.collection.immutable.List.collect(List.scala:261)
	scala.collection.immutable.List.collect(List.scala:254)
	dotty.tools.dotc.interactive.Completion$Completer.accessibleMembers(Completion.scala:704)
	dotty.tools.dotc.interactive.Completion$Completer.scopeCompletions$lzyINIT1$$anonfun$1(Completion.scala:422)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:633)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:337)
	dotty.tools.dotc.core.Contexts$Context$$anon$2.foreach(Contexts.scala:136)
	dotty.tools.dotc.interactive.Completion$Completer.scopeCompletions$lzyINIT1(Completion.scala:412)
	dotty.tools.dotc.interactive.Completion$Completer.scopeCompletions(Completion.scala:402)
	dotty.tools.dotc.interactive.Completion$.computeCompletions(Completion.scala:259)
	dotty.tools.dotc.interactive.Completion$.rawCompletions(Completion.scala:93)
	dotty.tools.pc.completions.Completions.enrichedCompilerCompletions(Completions.scala:123)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:148)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:139)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:197)
	scala.meta.internal.pc.CompilerAccess.withSharedCompiler(CompilerAccess.scala:149)
	scala.meta.internal.pc.CompilerAccess.$anonfun$1(CompilerAccess.scala:93)
	scala.meta.internal.pc.CompilerAccess.onCompilerJobQueue$$anonfun$1(CompilerAccess.scala:210)
	scala.meta.internal.pc.CompilerJobQueue$Job.run(CompilerJobQueue.scala:153)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1095)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:619)
	java.base/java.lang.Thread.run(Thread.java:1447)
```
#### Short summary: 

java.lang.StringIndexOutOfBoundsException: Index 0 out of bounds for length 0