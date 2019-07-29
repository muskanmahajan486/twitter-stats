package no.dervis.gts.properties;

import java.util.Objects;

public class Env {

    public static String requireSystemProperty(String property) {
        return Objects.requireNonNull(System.getenv(property),
                "Required environment variable " + property + " is not set.");
    }

}
