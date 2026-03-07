package it.unibo.factorial

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class FactorialTest:
  @ParameterizedTest
  @CsvSource(Array(
    "0, 1",
    "1, 1",
    "6, 720"
  ))
  def canComputeFactorial(n: Int, expected: Int): Unit = 
    assertEquals(expected, factorial(n))
