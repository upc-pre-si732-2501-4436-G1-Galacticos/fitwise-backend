Feature: Listado de Planes Fitwise

  Scenario: Listar todos los planes del usuario
    Given el usuario está autenticado
    And tiene varios planes creados: "Plan A", "Plan B", y "Plan C"
    When el usuario visualiza la lista de sus planes
    Then el sistema debería mostrar una lista que incluye "Plan A", "Plan B", y "Plan C"

  Scenario: Listar planes con un tag específico
    Given el usuario está autenticado
    And tiene varios planes creados, algunos con el tag "Nutrición"
    When el usuario filtra la lista de planes por el tag "Nutrición"
    Then el sistema debería mostrar solo los planes que tienen el tag "Nutrición"