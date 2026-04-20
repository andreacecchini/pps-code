package it.unibo;

/**
 * Main class.
 */
public class App {
    /**
     * Main method.
     *
     * @param args
     *         not used.
     */
    public static void main(final String... args) {
        final App app = new App();
        System.out.println(app.greeting());
    }

    /**
     *
     * @return a greeting message for the user.
     */
    public String greeting() {
        return "Welcome to Java-Dojo!";
    }
}
