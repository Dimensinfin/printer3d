@P3D02 @Coils
Feature: Manage the Coils on the Inventory information section

  As the backend service
  should be able to persist, retrieve and update Coil instances.

  # - H A P P Y   P A T H
  @P3D02.H @P3D02.01
  Scenario: [P3D02.01] When we receive a new Coil request, validate that all the fields are valid and that it gets persisted at the repository.
    Given a new UUID named as ID
    Given the next New Coil request
      | id         | material | color  | label  | weight | active |
      | <world:id> | PLA      | BLANCO | BLANCO | 500    | true   |
    When the New Coil request is processed
    Then there is a valid response with return code of "201 CREATED"
    And the Coil repository has a record for new Coil with the next fields
      | id         | material | color  | label  | weight | active |
      | <world:id> | PLA      | BLANCO | BLANCO | 500    | true   |

  @P3D02.H @P3D02.03
  Scenario: [P3D02.03] A new endpoint allows to change the weight for an existing Coil to adjust for usage deviations.
    Given a clean Coil repository table
    And the following Coils in my service
      | id                                   | material | color    | label    | weight |
      | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ROJO     | ROJO     | 500    |
      | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | TPU      | BLANCO   | BLANCO   | 500    |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO    | NEGRO    | 500    |
      | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | GRIS     | GRIS     | 500    |
      | 2e5dd268-0940-454a-918b-58b4dfd1a308 | PLA      | AMARILLO | AMARILLO | 500    |
    Given the next Update Coil request
      | id                                   | weight |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | 700    |
    When the Update Coil request is processed
    Then there is a valid response with return code of "200 OK"
    When the Get Coils v2 request is processed
    And the list of Coils has 5 items
    And the coil with id "49f72da1-051c-437c-b9d1-b81e298b156d" of the list of Coils has the next fields
      | id                                   | material | color | label | weight |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO | NEGRO | 700    |

  @P3D02.H @P3D02.04
  Scenario: [P3D02.04] Under version 2 there is a new endpoint to get all Coils persisted at the repository as a list. The Coil data has also changed
    Given a clean Coil repository table
    And the following Coils in my service
      | id                                   | material | color    | label    | weight | active |
      | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ROJO     | ROJO     | 500    | true   |
      | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | TPU      | BLANCO   | BLANCO   | 500    | true   |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO    | NEGRO    | 500    | true   |
      | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | GRIS     | GRIS     | 500    | false  |
      | 2e5dd268-0940-454a-918b-58b4dfd1a308 | PLA      | AMARILLO | AMARILLO | 500    | true   |
    When the Get Coils v2 request is processed
    Then there is a valid response with return code of "200 OK"
    And the list of Coils has 5 items
    And the coil with id "49f72da1-051c-437c-b9d1-b81e298b156d" of the list of Coils has the next fields
      | id                                   | material | color | label | weight | active |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO | NEGRO | 500    | true   |
    And the number of Active "false" Coils is 1

  @P3D02.H @P3D02.05
  Scenario: [P3D02.05] Validate that not all the new Coil fields should be set up to have a valid coil. There are default values.
    Given a new UUID named as ID
    Given the next New Coil request
      | id         | material | color  |
      | <world:id> | PLA      | BLANCO |
    When the New Coil request is processed
    Then there is a valid response with return code of "201 CREATED"
    And the Coil repository has a record for new Coil with the next fields
      | id         | material | color  | label  | weight | active |
      | <world:id> | PLA      | BLANCO | BLANCO | 750    | true   |

  @P3D02.H @P3D02.06
  Scenario: [P3D02.06] The Color Set can have a different value from the color label.
    Given a new UUID named as ID
    Given the next New Coil request
      | id         | material | color       | label  |
      | <world:id> | PLA      | BLANCO ROTO | BLANCO |
    When the New Coil request is processed
    Then there is a valid response with return code of "201 CREATED"
    And the Coil repository has a record for new Coil with the next fields
      | id         | material | color       | label  | weight | active |
      | <world:id> | PLA      | BLANCO ROTO | BLANCO | 750    | true   |

  @P3D02.H @P3D02.07
  Scenario: [P3D02.07] The color label for a Coil can now be changed. Validate that this is possible. Weigth is always mandatory.
    Given a clean Coil repository table
    And the following Coils in my service
      | id                                   | material | color    | label    | weight | active |
      | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ROJO     | ROJO     | 500    | true   |
      | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | TPU      | BLANCO   | BLANCO   | 500    | true   |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO    | NEGRO    | 500    | true   |
      | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | GRIS     | GRIS     | 500    | false  |
      | 2e5dd268-0940-454a-918b-58b4dfd1a308 | PLA      | AMARILLO | AMARILLO | 500    | true   |
    Given the next Update Coil request
      | id                                   | weight | label |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | 500    | GRIS  |
    When the Update Coil request is processed
    Then there is a valid response with return code of "200 OK"
    When the Get Coils v2 request is processed
    And the list of Coils has 5 items
    And the coil with id "49f72da1-051c-437c-b9d1-b81e298b156d" of the list of Coils has the next fields
      | id                                   | material | color | label | weight | active |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | GRIS  | NEGRO | 500    | true   |

  @P3D02.H @P3D02.08
  Scenario: [P3D02.08] The active flag for a Coil can now be changed. Validate that this is possible. Weigth is always mandatory.
    Given a clean Coil repository table
    And the following Coils in my service
      | id                                   | material | color    | label    | weight | active |
      | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ROJO     | ROJO     | 500    | true   |
      | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | TPU      | BLANCO   | BLANCO   | 500    | true   |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO    | NEGRO    | 500    | true   |
      | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | GRIS     | GRIS     | 500    | false  |
      | 2e5dd268-0940-454a-918b-58b4dfd1a308 | PLA      | AMARILLO | AMARILLO | 500    | true   |
    Given the next Update Coil request
      | id                                   | weight | active |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | 600    | false  |
    When the Update Coil request is processed
    Then there is a valid response with return code of "200 OK"
    When the Get Coils v2 request is processed
    And the list of Coils has 5 items
    And the coil with id "49f72da1-051c-437c-b9d1-b81e298b156d" of the list of Coils has the next fields
      | id                                   | material | color | label | weight | active |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO | NEGRO | 600    | false  |
    And the number of Active "false" Coils is 2
