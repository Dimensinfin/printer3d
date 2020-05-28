@P3D02 @Rolls
Feature: Manage the Rolls on the Inventory information section

  As the backend service
  should be able to persist, retrieve and update Roll instances.

  # - H A P P Y   P A T H
  @P3D02.H @P3D02.01
  Scenario: [P3D02.01] When we receive a new Roll request, validate that all the fields are valid and that it gets persisted at the repository.
    Given a new UUID named as ID
    Given the next New Roll request
      | id         | material | color  | weight |
      | <world:id> | PLA      | BLANCO | 500    |
    When the New Roll request is processed
    Then there is a valid response with return code of "201 CREATED"
    And the Roll repository has a record for new Roll with the next fields
      | id         | material | color  | weight |
      | <world:id> | PLA      | BLANCO | 500    |
