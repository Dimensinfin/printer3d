@D3D05
Feature: [D3D05]-Define the requirements for the New Coil dialog interactions

    Other of the key elements to work are the Coils. They are created on a Dialog.
    Describe and test the creation of a new Coil.

    Background: Dock Default Configuration setup
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D05.01
    Scenario: [D3D05.01]-If the New Coil Feature is clicked then we show the New Coil dialog.
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"
        Then the "New Coil" dialog opens and blocks the display

    @D3D05.02
    Scenario: [D3D05.02]-A new coil dialog should have all the fields empty and invalid.
        Given there is a click on Feature "/NUEVO ROLLO"
        Then the "New Coil" dialog opens and blocks the display
        # - Check the empty dialog contents and field states
        And the target dialog has a title "/INVENTARIO/NUEVO ROLLO"
        And the target panel has a form field named "material" with label "MATERIAL" and empty
        And the target panel has a form field named "color" with label "COLOR" and empty
        And the target panel has a form field named "weight" with label "PESO" and empty
        # - Check the field state
        And the target panel input field named "material" is "invalid"
        And the target panel input field named "color" is "invalid"
        And the target panel input field named "weight" is "invalid"

    @D3D05.03
    Scenario: [D3D05.03]-If we enter enough characters the fields change to 'valid'.
        Given there is a click on Feature "/NUEVO ROLLO"
        Then the "New Coil" dialog opens and blocks the display
        # - Check the field contents size
        Then the target panel field "material" is tested for size constraints 2 and 16
        And the target panel field "color" is tested for size constraints 2 and 32
    # And the target panel field "weight" is tested for value constraints 1 and 2000

    # - H A P P Y   P A T H
    # @D3D05 @D3D05.01
    # Scenario: [D3D05.01]-If the Feature New Coil receives a click then we should show the New Coil Dialog.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVO ROLLO"
    #     Then the New Coil dialog opens and blocks the display

    # @D3D05 @D3D05.02
    # Scenario: [D3D05.02]-The New Coil dialog has an specific set of fields.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVO ROLLO"
    #     Then the New Coil dialog opens and blocks the display
    #     And there is a dialog title saying "/INVENTARIO/NUEVO ROLLO"
    #     And there is one field called "#coil-id" with the label "ID"
    #     And there is one field called "#coil-material" with the label "Material"
    #     And there is one field called "#coil-weight" with the label "Peso"

    # NEED REVIEW
    @D3D05 @D3D05.03
    Scenario: [D3D05.03]-When the dialog is open the number and state of the buttons is the predicted.
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"
        Then the "New Coil" dialog opens and blocks the display
        And the button "submit-button" has the next properties
            | label         | state    |
            | Guardar Datos | disabled |
        And the button "CANCEL" has the next properties
            | label          | state   |
            | Go Back/Cancel | enabled |
        And the button "GUARDARYCONTINUAR" has the next properties
            | label             | state    |
            | Guardar y Repetir | disabled |

    @D3D05 @D3D05.04
    Scenario: [D3D05.04]-When all the required fields are filled with the right values the Save button activates.
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"
        Then the "New Coil" dialog opens and blocks the display
        And when all required fields have next values
            | material | color | peso |
            | PLA      | ROJO  | 750  |
        And the button "GUARDAR" has the next properties
            | label         | state   |
            | Guardar Datos | enabled |
        And the button "CANCEL" has the next properties
            | label          | state   |
            | Go Back/Cancel | enabled |
        And the button "GUARDARYCONTINUAR" has the next properties
            | label             | state   |
            | Guardar y Repetir | enabled |

    @D3D05 @D3D05.05
    Scenario: [D3D05.05]-If the New Coil Save button is clicked then the coil is stored at the repository and a notification is thrown.
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"
        Then the "New Coil" dialog opens and blocks the display
        And when all required fields have next values
            | material | color | peso |
            | PLA      | ROJO  | 750  |
        When there is a click on the "GUARDAR" button
        Then the coil is persisted at the backend
        And the dialog closes

    @D3D05 @D3D05.06
    Scenario: [D3D05.06]-If the New Coil Cancel button is clicked then dialog is closed.
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"
        Then the "New Coil" dialog opens and blocks the display
        When there is a click on the "CLOSE" button
        Then the dialog closes

    @D3D06 @D3D06.07
    Scenario: [D3D06.07]-If the New Coil Save and Continue button is clicked then coil is persisted but the dialog is kept open and the contents updated.
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"
        Then the "New Coil" dialog opens and blocks the display
        And when all required fields have next values
            | material | color | peso |
            | PLA      | ROJO  | 750  |
        When there is a click on the "GUARDARYCONTINUAR" button
        Then the coil is persisted at the backend
        And form fields have the next values
            | material | color | weight |
            | PLA      |       |        |

    # - E X C E P T I O N S
    @D3D05.E @D3D05.E.01
    Scenario: [D3D05.E.01]-If the user tryes to enter an string on the numeric fields it fails.
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"
        Then the "New Coil" dialog opens and blocks the display
        And when all required fields have next values
            | peso  |
            | 750gr |
        And there is one field called "weight" with the content "750"
