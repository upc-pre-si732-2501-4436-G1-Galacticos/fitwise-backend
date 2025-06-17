Feature: Actualización de Plan Fitwise

  Scenario: Actualizar el título de un plan
    Given el usuario está autenticado
    And tiene un plan existente llamado "Mi Plan de Inicio"
    When edita el plan "Mi Plan de Inicio"
    And cambia el título a "Mi Plan Actualizado"
    And guarda los cambios
    Then el sistema debería mostrar un mensaje de éxito "Plan actualizado exitosamente"
    And el plan "Mi Plan Actualizado" debería estar listado con el nuevo título

  Scenario: Actualizar la dieta de un plan
    Given el usuario está autenticado
    And tiene un plan existente llamado "Mi Plan de Inicio" con la dieta "Keto para Principiantes"
    When edita el plan "Mi Plan de Inicio"
    And cambia la dieta a "Mediterránea"
    And guarda los cambios
    Then el sistema debería mostrar un mensaje de éxito "Plan actualizado exitosamente"
    And el plan "Mi Plan de Inicio" debería tener asociada la dieta "Mediterránea"

  Scenario: Agregar un tag a un plan
    Given el usuario está autenticado
    And tiene un plan existente llamado "Plan Avanzado" con los tags "Avanzado" y "Fuerza"
    When edita el plan "Plan Avanzado"
    And agrega el tag "Cardio"
    And guarda los cambios
    Then el sistema debería mostrar un mensaje de éxito "Plan actualizado exitosamente"
    And el plan "Plan Avanzado" debería tener los tags "Avanzado", "Fuerza" y "Cardio"

  Scenario: Eliminar un tag de un plan
    Given el usuario está autenticado
    And tiene un plan existente llamado "Plan Avanzado" con los tags "Avanzado", "Fuerza" y "Cardio"
    When edita el plan "Plan Avanzado"
    And elimina el tag "Fuerza"
    And guarda los cambios
    Then el sistema debería mostrar un mensaje de éxito "Plan actualizado exitosamente"
    And el plan "Plan Avanzado" debería tener los tags "Avanzado" y "Cardio"
