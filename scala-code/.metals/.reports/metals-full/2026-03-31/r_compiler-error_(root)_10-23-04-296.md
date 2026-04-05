error id: C2C109613AC5E410A42D999A0616D2E2
file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala
### java.lang.IndexOutOfBoundsException: 0

occurred in the presentation compiler.



action parameters:
offset: 359
uri: file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala
text:

```scala
package it.unibo.bankAccount

object EurosOOP:
  trait Euro:
    // TODO: We are exposing cents, should we avoid that?
    // def cents: Int

    /** Sum [[this]] with [[other]]. */
    def +(other: Euro): Euro

  object Euro:
    private case class EuroImpl(cents: Int) extends Euro {
      override def +(other: Euro): Euro = other match
        case EuroImpl(c @@) =>

      override def toString(): String = s"E${cents / 100}.${cents % 100}"
    }

    /** Factory method to build [[Euro]] from [[euros]] and [[cents]]. */
    def apply(euros: Int, cents: Int): Euro =
      require(euros >= 0 && (cents >= 0 && cents < 100))
      EuroImpl(euros * 100 + cents)

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
  import EurosFP.*
  val e1: Euro = Euro(1, 50)
  val e2: Euro = Euro(2, 50)
  // TODO: Why those functions are visible if Euro companion object is not imported.
  val e3: Euro = e1 + e2
  println(e1.show)
  println(e2.show)
  println(e3.show)

```


presentation compiler configuration:
Scala version: 3.8.1-bin-nonbootstrapped
Classpath:
<WORKSPACE>/.bloop/root/bloop-bsp-clients-classes/classes-Metals-DK62D2r9S-OEVUt0uU6dsg== [exists ], <HOME>/Library/Caches/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.11.2/semanticdb-javac-0.11.2.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.8.1/scala3-library_3-3.8.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/3.8.1/scala-library-3.8.1.jar [exists ], <WORKSPACE>/.bloop/root/bloop-bsp-clients-classes/classes-Metals-DK62D2r9S-OEVUt0uU6dsg==/META-INF/best-effort [missing ]
Options:
-Xsemanticdb -sourceroot <WORKSPACE> -Ywith-best-effort-tasty




#### Error stacktrace:

```
scala.collection.LinearSeqOps.apply(LinearSeq.scala:134)
	scala.collection.LinearSeqOps.apply$(LinearSeq.scala:38)
	scala.collection.immutable.List.apply(List.scala:83)
	dotty.tools.pc.InferCompletionType$.inferType(InferExpectedType.scala:94)
	dotty.tools.pc.InferCompletionType$.inferType(InferExpectedType.scala:62)
	dotty.tools.pc.completions.Completions.advancedCompletions(Completions.scala:543)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:131)
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

java.lang.IndexOutOfBoundsException: 0
