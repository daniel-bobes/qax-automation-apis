Feature: Consultar producto

  Scenario: Consultar producto correctamente
    Given la api esta disponible
    When consulto el producto con código 101
    Then el nombre debe ser "Laptop"