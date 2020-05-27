@D3D05
Feature: [D3D05]-Define the requirements fopr the Roll dialog.

    Added to the list of Parts there is a list of Rolls that are also stored at the inventory group endpoint.
    Rolls are required for building and define some of the characteristics of the part.

    Background: Dock Default Configuration setup
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    # @D3D05 @D3D05.01
    # Scenario: [D3D05.01]-If the Feature New Roll receives a click then we should show the New Roll Dialog.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVO ROLLO"
    #     Then the New Roll dialog opens and blocks the display

    # @D3D05 @D3D05.02
    # Scenario: [D3D05.02]-The new Roll dialog has an specific set of fields.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVO ROLLO"
    #     Then the New Roll dialog opens and blocks the display
    #     And there is a dialog title saying "/INVENTARIO/NUEVO ROLLO"
    #     And there is one field called "#roll-id" with the label "ID"
    #     And there is one field called "#roll-material" with the label "Material"
    #     And there is one field called "#roll-weight" with the label "Peso"

    # @D3D05 @D3D05.03
    # Scenario: [D3D05.03]-When the dialog is open the number and state of the buttons is the predicted.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVO ROLLO"
    #     Then the New Roll dialog opens and blocks the display
    #     And the button "GUARDAR" has the next properties
    #         | label         | state    |
    #         | Guardar Datos | disabled |
    #     And the button "CANCEL" has the next properties
    #         | label          | state   |
    #         | Go Back/Cancel | enabled |
    #     And the button "GUARDARYCONTINUAR" has the next properties
    #         | label             | state    |
    #         | Guardar y Repetir | disabled |

    # @D3D05 @D3D05.04
    # Scenario: [D3D05.04]-When all the required fields are filled with the right values the Save button activates.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVO ROLLO"
    #     Then the New Roll dialog opens and blocks the display
    #     And when all required fields have next values
    #         | material | color | peso |
    #         | PLA      | ROJO  | 750  |
    #     And the button "GUARDAR" has the next properties
    #         | label         | state    |
    #         | Guardar Datos | enabled |
    #     And the button "CANCEL" has the next properties
    #         | label          | state   |
    #         | Go Back/Cancel | enabled |
    #     And the button "GUARDARYCONTINUAR" has the next properties
    #         | label             | state    |
    #         | Guardar y Repetir | enabled |

    @D3D05 @D3D05.05
    Scenario: [D3D05.05]-If the New Roll Save button is clicked then the roll is stored at the repository and a notification is thrown.
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"
        Then the New Roll dialog opens and blocks the display
        And when all required fields have next values
            | material | color | peso |
            | PLA      | ROJO  | 750  |
        When there is a click on the "GUARDAR" button
        Then the roll is persisted at the backend
        And the dialog closes
