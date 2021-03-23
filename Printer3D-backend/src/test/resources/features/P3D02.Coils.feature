@P3D02 @Coils
Feature: Manage the Coils on the Inventory information section

  As the backend service
  should be able to persist, retrieve and update Coil instances.

  # - H A P P Y   P A T H
  @P3D02.H @P3D02.01
  Scenario: [P3D02.01] When we receive a new Coil request, validate that all the fields are valid and that it gets persisted at the repository.
    Given a new UUID named as ID
    Given the next New Coil request
      | id         | material | color  | weight |
      | <world:id> | PLA      | BLANCO | 500    |
    When the New Coil request is processed
    Then there is a valid response with return code of "201 CREATED"
    And the Coil repository has a record for new Coil with the next fields
      | id         | material | color  | weight |
      | <world:id> | PLA      | BLANCO | 500    |

#  @P3D02.H @P3D02.02
#  Scenario: [P3D02.02] There is and endpoint to get all Coils persisted at the repository.
#    Given a clean Coil repository table
#    And the following Coils in my service
#      | id                                   | material | color    | weight |
#      | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ROJO     | 500    |
#      | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | TPU      | BLANCO   | 500    |
#      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO    | 500    |
#      | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | GRIS     | 500    |
#      | 2e5dd268-0940-454a-918b-58b4dfd1a308 | PLA      | AMARILLO | 500    |
#    When the Get Coils request is processed
#    Then there is a valid response with return code of "200 OK"
#    And the list of Coils has "5" items
#    And the item "1" of the list of Coils has the next fields
#      | id                                   | material | color | weight |
#      | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ROJO  | 500    |

  @P3D02.H @P3D02.03
  Scenario: [P3D02.03] A new endpoint allows to change the weight for an existing Coil to adjust for usage deviations.
    Given a clean Coil repository table
    And the following Coils in my service
      | id                                   | material | color    | colorSet | weight |
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
    When the Get Coils request is processed
    And the list of Coils has 5 items
    And the coil with id "49f72da1-051c-437c-b9d1-b81e298b156d" of the list of Coils has the next fields
      | id                                   | material | color | colorSet | weight |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO | NEGRO    | 700    |

  @P3D02.H @P3D02.04
  Scenario: [P3D02.04] Under version  there is a new endpoint to get all Coils persisted at the repository as a list. The Coil data has also changed
    Given a clean Coil repository table
    And the following Coils in my service
      | id                                   | material | color    | colorSet | weight | active |
      | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ROJO     | ROJO     | 500    | true   |
      | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | TPU      | BLANCO   | BLANCO   | 500    | true   |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO    | NEGRO    | 500    | true   |
      | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | GRIS     | GRIS     | 500    | false  |
      | 2e5dd268-0940-454a-918b-58b4dfd1a308 | PLA      | AMARILLO | AMARILLO | 500    | true   |
    When the Get Coils v2 request is processed
    Then there is a valid response with return code of "200 OK"
    And the list of Coils has 5 items
    And the coil with id "49f72da1-051c-437c-b9d1-b81e298b156d" of the list of Coils has the next fields
      | id                                   | material | color | colorSet | weight | active |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO | NEGRO    | 500    | true   |
    And the number of Active "false" Coils is 1