error id: E2F11E88F30CFA6EF5246034855D4F79
file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala
### java.lang.AssertionError: assertion failed: TypeBounds(TypeRef(ThisType(TypeRef(NoPrefix,module class scala)),class Nothing),TypeRef(ThisType(TypeRef(NoPrefix,module class scala)),class Any))

occurred in the presentation compiler.



action parameters:
offset: 1076
uri: file://<WORKSPACE>/src/main/scala/it/unibo/oopVsFp/Euros.scala
text:

```scala
package it.unibo.bankAccount

object EurosOOP:
  case class EuroImpl(cents: Int) extends Euro {

    object Euro:
      override def +(other: Euro): Euro = other match
        case EuroImpl(c) => EuroImpl(this.cents + c)

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
  import EurosOOP.*
  val e1: Euro = Euro @@ (1, 50)
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
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:10)
	dotty.tools.dotc.core.Types$TypeBounds.<init>(Types.scala:5729)
	dotty.tools.dotc.core.Types$RealTypeBounds.<init>(Types.scala:5806)
	dotty.tools.dotc.core.Types$TypeBounds$.apply(Types.scala:5847)
	dotty.tools.dotc.core.Types$TypeBounds.derivedTypeBounds(Types.scala:5737)
	dotty.tools.dotc.core.Types$ApproximatingTypeMap.derivedTypeBounds(Types.scala:6756)
	dotty.tools.dotc.core.Types$TypeMap.mapOver(Types.scala:6457)
	dotty.tools.dotc.core.TypeOps$AsSeenFromMap.apply(TypeOps.scala:112)
	dotty.tools.dotc.core.TypeOps$AsSeenFromMap.apply(TypeOps.scala:70)
	scala.collection.immutable.List.loop$3(List.scala:466)
	scala.collection.immutable.List.mapConserve(List.scala:491)
	dotty.tools.dotc.core.Types$TypeMap.mapOverLambda(Types.scala:6341)
	dotty.tools.dotc.core.TypeOps$AsSeenFromMap.apply(TypeOps.scala:106)
	dotty.tools.dotc.core.TypeOps$.asSeenFrom(TypeOps.scala:56)
	dotty.tools.dotc.core.Types$Type.asSeenFrom(Types.scala:1190)
	dotty.tools.dotc.core.Denotations$SingleDenotation.derived$1(Denotations.scala:1107)
	dotty.tools.dotc.core.Denotations$SingleDenotation.computeAsSeenFrom(Denotations.scala:1134)
	dotty.tools.dotc.core.Denotations$SingleDenotation.computeAsSeenFrom(Denotations.scala:1087)
	dotty.tools.dotc.core.Denotations$PreDenotation.asSeenFrom(Denotations.scala:137)
	dotty.tools.dotc.core.SymDenotations$ClassDenotation.findMember(SymDenotations.scala:2233)
	dotty.tools.dotc.core.Types$Type.go$1(Types.scala:829)
	dotty.tools.dotc.core.Types$Type.findMember(Types.scala:1019)
	dotty.tools.dotc.core.Types$Type.memberBasedOnFlags(Types.scala:802)
	dotty.tools.dotc.core.Types$Type.member(Types.scala:786)
	dotty.tools.dotc.core.Types$Type.allMembers$$anonfun$1(Types.scala:1177)
	scala.runtime.function.JProcedure2.apply(JProcedure2.java:15)
	scala.runtime.function.JProcedure2.apply(JProcedure2.java:10)
	dotty.tools.dotc.core.Types$.dotty$tools$dotc$core$Types$Type$$_$memberDenots$$anonfun$1(Types.scala:1065)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.BitmapIndexedSetNode.foreach(HashSet.scala:954)
	scala.collection.immutable.HashSet.foreach(HashSet.scala:187)
	dotty.tools.dotc.core.Types$Type.memberDenots(Types.scala:1065)
	dotty.tools.dotc.core.Types$Type.allMembers(Types.scala:1177)
	dotty.tools.pc.AutoImportsProvider.applyInClass$1(AutoImportsProvider.scala:59)
	dotty.tools.pc.AutoImportsProvider.correctInTreeContext$1(AutoImportsProvider.scala:60)
	dotty.tools.pc.AutoImportsProvider.$anonfun$6(AutoImportsProvider.scala:119)
	scala.collection.immutable.List.noneIn$1(List.scala:509)
	scala.collection.immutable.List.filterCommon(List.scala:575)
	scala.collection.immutable.List.filter(List.scala:496)
	dotty.tools.pc.AutoImportsProvider.autoImports(AutoImportsProvider.scala:118)
	dotty.tools.pc.ScalaPresentationCompiler.autoImports$$anonfun$1(ScalaPresentationCompiler.scala:323)
	scala.meta.internal.pc.CompilerAccess.withSharedCompiler(CompilerAccess.scala:149)
	scala.meta.internal.pc.CompilerAccess.withNonInterruptableCompiler$$anonfun$1(CompilerAccess.scala:133)
	scala.meta.internal.pc.CompilerAccess.onCompilerJobQueue$$anonfun$1(CompilerAccess.scala:210)
	scala.meta.internal.pc.CompilerJobQueue$Job.run(CompilerJobQueue.scala:153)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1095)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:619)
	java.base/java.lang.Thread.run(Thread.java:1447)
```
#### Short summary: 

java.lang.AssertionError: assertion failed: TypeBounds(TypeRef(ThisType(TypeRef(NoPrefix,module class scala)),class Nothing),TypeRef(ThisType(TypeRef(NoPrefix,module class scala)),class Any))
