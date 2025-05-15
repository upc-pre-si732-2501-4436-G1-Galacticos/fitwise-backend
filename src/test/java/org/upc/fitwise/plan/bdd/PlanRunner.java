package org.upc.fitwise.plan.bdd;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.Suite;

import java.util.HashMap;
import java.util.Map;

@Suite
@Cucumber
public class PlanRunner {
    private static Map<String, String> cucumberProperties = new HashMap<>();

    @BeforeAll
    public static void beforeAll() {
        // Establece la propiedad cucumber.features a la ubicación correcta.
        cucumberProperties.put("cucumber.features", "src/test/resources/Features");
    }
}