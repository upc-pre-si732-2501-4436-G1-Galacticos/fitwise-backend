package org.upc.fitwise.plan.bdd.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@ActiveProfiles("test") // Usa el perfil de prueba para evitar afectar la base de datos de desarrollo
@Transactional  // Para que cada escenario se ejecute en una transacción y se revierta al final
public class PlanCreationSteps {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private Map<String, Object> scenarioContext = new HashMap<>(); // Para compartir datos entre pasos
    @Autowired
    private ObjectMapper objectMapper;

    @Given("el usuario está autenticado")
    public void elUsuarioEstaAutenticado() {
        // Inicializa MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        // En una aplicación real, aquí iría la lógica para simular la autenticación.
        // Por ejemplo, podrías agregar un header de autorización simulado.
        scenarioContext.put("isAuthenticated", true); // Usamos el contexto para indicar que el usuario está autenticado
    }

    @Given("ha seleccionado la opción para crear un nuevo plan")
    public void haSeleccionadoLaOpcionParaCrearUnNuevoPlan() {
        // Aquí podrías simular la navegación a la página de creación de planes, si fuera necesario.
        // En este ejemplo, simplemente establecemos una variable en el contexto.
        scenarioContext.put("creatingPlan", true);
    }

    @When("ingresa el título {string}")
    public void ingresaElTitulo(String titulo) {
        // Guarda el título en el contexto para usarlo en pasos posteriores.
        scenarioContext.put("planTitle", titulo);
    }

    @When("selecciona la dieta {string}")
    public void seleccionaLaDieta(String dieta) {
        // Guarda la dieta seleccionada.  En una app real, buscarías el ID por el nombre.
        scenarioContext.put("planDiet", dieta);
        scenarioContext.put("dietId", 1); //simulando que la dieta con nombre "Keto para Principiantes" tiene ID 1
    }

    @When("selecciona el entrenamiento {string}")
    public void seleccionaElEntrenamiento(String entrenamiento) {
        // Guarda el entrenamiento seleccionado. En una app real, buscarías el ID.
        scenarioContext.put("planWorkout", entrenamiento);
        scenarioContext.put("workoutId", 2);  //simulando que el workout "Cuerpo Completo Nivel 1" tiene ID 2
    }

    @When("guarda el plan")
    public void guardaElPlan() throws Exception {
        // Simula la acción de guardar el plan a través de una llamada a la API
        // Usa el título, dieta y entrenamiento guardados en el contexto.

        if (!(Boolean) scenarioContext.get("isAuthenticated")) {
            throw new IllegalStateException("El usuario no está autenticado"); //Esto no debería pasar, pero lo comprueba
        }
        if (!(Boolean) scenarioContext.get("creatingPlan")) {
            throw new IllegalStateException("No se ha seleccionado la opción de crear plan");  //Esto no debería pasar, pero lo comprueba
        }

        Map<String, Object> planData = new HashMap<>();
        planData.put("title", scenarioContext.get("planTitle"));
        planData.put("dietId", scenarioContext.get("dietId"));
        planData.put("workoutId", scenarioContext.get("workoutId"));
        // Para simplificar, no estamos enviando tags en este ejemplo.  Si los tuvieras, los agregarías aquí.

        String planJson = objectMapper.writeValueAsString(planData);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(planJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();  //Obtenemos el MvcResult para poder acceder a la respuesta

        String responseContent = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseContent, Map.class);
        scenarioContext.put("createdPlanId", responseMap.get("id")); // Guardamos el ID del plan creado para usarlo en el Then
        scenarioContext.put("responseMessage", responseMap.get("message")); //guardamos el mensaje

    }

    @Then("el sistema debería mostrar un mensaje de éxito {string}")
    public void elSistemaDeberiaMostrarUnMensajeDeExito(String mensaje) {
        // Verifica que el mensaje de éxito se muestra (esto podría depender de la UI o API)
        assertEquals(mensaje, scenarioContext.get("responseMessage"));
    }

    @Then("el plan {string} debería estar listado en sus planes")
    public void elPlanDeberiaEstarListadoEnSusPlanes(String nombrePlan) throws Exception {
        // Verifica que el plan se encuentra en la lista de planes del usuario
        // Esto implica hacer otra llamada a la API para obtener la lista de planes del usuario
        mockMvc.perform(MockMvcRequestBuilders.get("/api/plans"))  // Suponiendo que hay un endpoint para obtener los planes
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].title").value(containsString(nombrePlan))); //verifica que el titulo está en la respuesta
    }

    @Then("el plan {string} debería tener asociada la dieta {string}")
    public void elPlanDeberiaTenerAsociadaLaDieta(String nombrePlan, String nombreDieta) throws Exception {
        // Verifica la asociación de la dieta con el plan, usando el ID del plan creado.
        Long planId = (Long) scenarioContext.get("createdPlanId");  //obtenemos el ID del plan guardado
        mockMvc.perform(MockMvcRequestBuilders.get("/api/plans/" + planId))  // Suponiendo un endpoint para obtener un plan por ID
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.diet.title").value(nombreDieta)); //verifica el nombre de la dieta
    }

    @Then("el plan {string} debería tener asociado el entrenamiento {string}")
    public void elPlanDeberiaTenerAsociadoElEntrenamiento(String nombrePlan, String nombreEntrenamiento) throws Exception {
        // Similar a la dieta, verifica el entrenamiento asociado.
        Long planId = (Long) scenarioContext.get("createdPlanId");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/plans/" + planId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.workout.name").value(nombreEntrenamiento)); //verifica el nombre del workout
    }

}
