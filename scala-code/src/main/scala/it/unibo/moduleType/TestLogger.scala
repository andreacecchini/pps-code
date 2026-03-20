package it.unibo.moduleType


@main def testLoggers(): Unit =
  import LoggerModules.*

  def loggedCall[A, B](name: String, f: A => B, a: A)(using LoggerModule: LoggerModuleType): B =
    import LoggerModule.*
    log(s"calling function $name with input $a")
    f(a)

  // Injecting module implementation
  // println(loggedCall("sin", math.sin, 0.5)(using BasicLoggerModule))
  // println(loggedCall("sin", math.sin, 0.5)(using EmptyLoggerModule))
  // Importing context
  println:
    loggedCall("sin", math.sin, 0.5)
