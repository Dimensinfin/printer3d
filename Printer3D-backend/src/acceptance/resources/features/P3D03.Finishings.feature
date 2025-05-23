@P3D03 @Finishings
Feature: Process and compose the list of Finishings

    This is an especial response entity to collect all the distinct colors available for an specific type of material.

    Background:
        Given a clean Coils repository
        And the following Coils in my service
            | id                                   | material | tradeMark | color    | label    | weight | active |
            | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ESUN      | ROJO     | ROJO     | 500    | true   |
            | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | TPU      | ESUN      | BLANCO   | BLANCO   | 500    | true   |
            | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | ESUN      | NEGRO    | NEGRO    | 500    | true   |
            | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | ESUN      | GRIS     | GRIS     | 500    | false  |
            | 2e5dd268-0940-454a-918b-58b4dfd1a308 | PLA      | ESUN      | AMARILLO | AMARILLO | 500    | true   |

  # - H A P P Y   P A T H
    @P3D03.H @P3D03.01
    Scenario: [P3D03.01] When we receive a new Finishing request we collect all the records for active rolls and create the response.
        When the Get Finishings request is processed
        Then there is a valid response with return code of "200 OK"

    @P3D03.H @P3D03.02
    Scenario: [P3D03.02] The response has the material records ordered alphabetically.
        When the Get Finishings request is processed
        And the response has 2 material records
        And the material records are ordered alphabetically

    @P3D03.H @P3D03.03
    Scenario: [P3D03.03] The material finishing on the response has the colors sorted alphabetically.
        When the Get Finishings request is processed
        And the "PLA" material record has 1 colors
        And the list of colors  of material "TPU" is ordered alphabetically
