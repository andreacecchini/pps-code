package it.unibo.moduleType

object LoggerModules:
  /** Logger module type. */
  trait LoggerModuleType:
    def log(s: String): Unit

  /** A [[LoggerModuleType]] implementation which prints in stdout. */
  object BasicLoggerModule extends LoggerModuleType:
    def log(s: String): Unit = println(s)

  /** A [[LoggerModuleType]] implementation which does not log. */
  object EmptyLoggerModule extends LoggerModuleType:
    def log(s: String): Unit = {}

  /** Default implementation if not provided. */
  given LoggerModuleType = BasicLoggerModule




