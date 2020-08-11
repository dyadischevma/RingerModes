Feature: Enter login details

  @Test
  Scenario Outline: Successful login
    Given Application started
    When User click's on button add
    And User enters Regime's <name>
    And User choose <regime>
    And User clicks Save icon
    Then User see new Regime
    Examples:
      | name | regime |
      | test | NORMAL |