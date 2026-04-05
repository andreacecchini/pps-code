error id: 5A220C98CD7E916C684F86A6675E9E6C
file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala
### java.lang.StringIndexOutOfBoundsException: Index 0 out of bounds for length 0

occurred in the presentation compiler.



action parameters:
offset: 166
uri: file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala
text:

```scala
package it.unibo.bankAccount

import it.unibo.{bankAccount => Euro}

object EurosOOP:
  case class Euro(private val cents: Int):
    def +(other: Euro): Euro = Euro(cents + o @@)

  object Euro:
    /** Factory method to build [[Euro]] from [[euros]] and [[cents]]. */
    def apply(euros: Int, cents: Int): Euro =
      require(euros >= 0 && (cents >= 0 && cents < 100))
      Euro(euros * 100 + cents)

    override def +(other: Euro): Euro = other match
      case Euro(c) => Euro(this.cents + c)

    override def toString(): String = s"E${cents / 100}.${cents % 100}"

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
  println(e1)
  println(e2)
  println(e3)

```


presentation compiler configuration:
Scala version: 3.8.1-bin-nonbootstrapped
Classpath:
<WORKSPACE>/.bloop/root/bloop-bsp-clients-classes/classes-Metals-DK62D2r9S-OEVUt0uU6dsg== [exists ], <HOME>/Library/Caches/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.11.2/semanticdb-javac-0.11.2.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.8.1/scala3-library_3-3.8.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/3.8.1/scala-library-3.8.1.jar [exists ], <WORKSPACE>/.bloop/root/bloop-bsp-clients-classes/classes-Metals-DK62D2r9S-OEVUt0uU6dsg==/META-INF/best-effort [missing ]
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
