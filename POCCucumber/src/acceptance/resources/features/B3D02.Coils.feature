@B3D02 @Coils
Feature: Manage the Coils on the Inventory information section

    @B3D02.H @B3D02.04
    Scenario: [B3D02.04] Under version 2 there is a new endpoint to get all Coils persisted at the repository as a list. The Coil data has also changed
#        Given a clean Coils repository
#        And the following Coils in my service
#            | id                                   | material | tradeMark | color    | label    | weight | active |
#            | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ESUN      | ROJO     | ROJO     | 500    | true   |
#            | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | TPU      | ESUN      | BLANCO   | BLANCO   | 500    | true   |
#            | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | ESUN      | NEGRO    | NEGRO    | 500    | true   |
#            | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | ESUN      | GRIS     | GRIS     | 500    | false  |
#            | 2e5dd268-0940-454a-918b-58b4dfd1a308 | PLA      | ESUN      | AMARILLO | AMARILLO | 500    | true   |
        When the Get Coils v2 request is processed
#        Then there is a valid response with return code of "200 OK"
#        And the list of Coils has 5 items
#        And the coil with id "49f72da1-051c-437c-b9d1-b81e298b156d" of the list of Coils has the next fields
#            | id                                   | material | tradeMark | color | label | weight | active |
#            | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | ESUN      | NEGRO | NEGRO | 500    | true   |
#        And the number of Active "false" Coils is 1
