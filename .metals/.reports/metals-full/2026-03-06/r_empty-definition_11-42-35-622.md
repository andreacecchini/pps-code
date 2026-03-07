error id: file://<WORKSPACE>/scala-code/src/main/scala/it/unibo/sqrt/Sqrt.scala:
file://<WORKSPACE>/scala-code/src/main/scala/it/unibo/sqrt/Sqrt.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 226
uri: file://<WORKSPACE>/scala-code/src/main/scala/it/unibo/sqrt/Sqrt.scala
text:
```scala
package it.unibo.sqrt


def sqrt(x: Double): Double =
  def try (guess: Double, x: Double): Double =
    def isGuessEnough(guess: Double, x: Double): Double = ???
    def improve(guess: Double, x: Double) = ???
    if isGuessE@@nough(guess, x) than guess
    else try (improve(guess, x), x)
  try (1.0, x)

```


#### Short summary: 

empty definition using pc, found symbol in pc: 