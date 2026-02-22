package it.unibo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link App}.
 */
class AppTest {
    @Test
    void testAppShouldContainGreeting() {
        final App app = new App();
        assertEquals("Welcome to Java-Dojo!", app.greeting());
    }
}
