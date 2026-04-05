error id: file://<WORKSPACE>/scala-code/src/main/scala/it/unibo/oopVsFp/Euros.scala:it/unibo/oopVsFp/Euros.Euro.EuroImpl#
file://<WORKSPACE>/scala-code/src/main/scala/it/unibo/oopVsFp/Euros.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol it/unibo/oopVsFp/Euros.Euro.EuroImpl#
empty definition using fallback
non-local guesses:

offset: 153
uri: file://<WORKSPACE>/scala-code/src/main/scala/it/unibo/oopVsFp/Euros.scala
text:
```scala
package it.unibo.oopVsFp

object Euros:
    trait Euro:
        def +(other: Euro): Euro        
    
    object Euro:
        private case class EuroImp@@l(cents: Int) extends Euro  {
          override def +(other: Euro): Euro = this.cents + other.cents
        }      
        def apply(euro: Int, cents: Int): Euro = ???
end Euros

@main def testEuros = 
    import Euros.* 
    val e1: Euro = Euro(1, 50)
    val e2: Euro = Euro(2, 50)
    val e3: Euro = e1 + e2
    println(e1)    
    println(e2)
    println(e3)
```


#### Short summary: 

empty definition using pc, found symbol in pc: 