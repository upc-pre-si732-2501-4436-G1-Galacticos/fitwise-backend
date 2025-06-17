Feature: Creación de Plan Fitwise

  Scenario: Crear un plan con dieta y entrenamiento
    Given el usuario está autenticado
    And ha seleccionado la opción para crear un nuevo plan
    When ingresa el título "Mi Plan de Inicio"
    And selecciona la dieta "Keto para Principiantes"
    And selecciona el entrenamiento "Cuerpo Completo Nivel 1"
    And guarda el plan
    Then el sistema debería mostrar un mensaje de éxito "Plan creado exitosamente"
    And el plan "Mi Plan de Inicio" debería estar listado en sus planes
    And el plan "Mi Plan de Inicio" debería tener asociada la dieta "Keto para Principiantes"
    And el plan "Mi Plan de Inicio" debería tener asociado el entrenamiento "Cuerpo Completo Nivel 1"

  Scenario: Intentar crear un plan sin título
    Given el usuario está autenticado
    And ha seleccionado la opción para crear un nuevo plan
    When intenta guardar el plan sin ingresar un título
    Then el sistema debería mostrar un mensaje de error "El título es requerido"
    And el plan no debería ser creado

  Scenario: Crear un plan con tags
    Given el usuario está autenticado
    And ha seleccionado la opción para crear un nuevo plan
    When ingresa el título "Plan Avanzado"
    And selecciona la dieta "Paleo"
    And selecciona el entrenamiento "Fuerza Máxima"
    And selecciona los tags "Avanzado" y "Fuerza"
    And guarda el plan
    Then el sistema debería mostrar un mensaje de éxito "Plan creado exitosamente"
    And el plan "Plan Avanzado" debería tener los tags "Avanzado" y "Fuerza"