@D3D05
Feature: [D3D05]-Define the requirements for the New Coil dialog interactions

    Other of the key elements to work are the Coils. They are created on a Dialog.
    Describe and test the creation of a new Coil.

    Background: Dock Default Configuration setup
        Given the application Printer3DManager
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"

    # - H A P P Y   P A T H
    @D3D05.01
    Scenario: [D3D05.01]-If the New Coil Feature is clicked then we show the New Coil dialog.
        Then the "New Coil" dialog opens and blocks the display

    @D3D05.02
    Scenario: [D3D05.02]-A new coil dialog should have all the fields empty and invalid.
        Then the "New Coil" dialog opens and blocks the display
        # - Check the empty dialog contents and field states
        And the target dialog has a title "/INVENTARIO/NUEVO ROLLO"
        And the target panel has a form field named "material" with label "MATERIAL" and empty
        And the target panel has a form field named "color" with label "COLOR" and empty
        And the target panel has a form field named "weight" with label "PESO" and empty
        # - Check the field state
        And form field named "material" is "invalid"
        And form field named "color" is "invalid"
        And form field named "weight" is "invalid"

    @D3D05.03
    Scenario: [D3D05.03]-If we enter enough characters the fields change to 'valid'.
        Then the "New Coil" dialog opens and blocks the display
        # - Check the field contents size
        Then the target panel field "material" is tested for size constraints 2 and 16
        And the target panel field "color" is tested for size constraints 2 and 32
        And field named "weight" is tested for value constraints 1 to 2000

    @D3D05.04
    Scenario: [D3D05.04]-There are button to save and cancel the new coil. By default is any field is invalid the buttons are disabled.
        Then the "New Coil" dialog opens and blocks the display
        And the target panel button with name "repeat-button" has a label "Guardar y Repetir" and is "disabled"
        And the target panel button with name "submit-button" has a label "Guardar" and is "disabled"
        And the target panel button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D05.05
    Scenario: [D3D05.05]-If all the fields are filled with valid values the Save button activates.
        Then the "New Coil" dialog opens and blocks the display
        Given "PLA" is set on form field "material"
        And "AMARILLO" is set on form field "color"
        And 800 is set on form field "weight"
        Then the target panel button with name "repeat-button" has a label "Guardar y Repetir" and is "enabled"
        And the target panel button with name "submit-button" has a label "Guardar" and is "enabled"
        And the target panel button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D05.06
    Scenario: [D3D05.06]-If the New Coil Save button is clicked then the coil is stored at the repository and a notification is thrown.
        Then the "New Coil" dialog opens and blocks the display
        Given "PLA" is set on form field "material"
        And "AMARILLO" is set on form field "color"
        And 800 is set on form field "weight"
        Then the target panel button with name "submit-button" has a label "Guardar" and is "enabled"
        When  the button with name "submit-button" is clicked
        Then the coil is persisted at the backend
        And the dialog closes

    @D3D05.07
    Scenario: [D3D05.07]-If the New Coil Cancel button is clicked then dialog is closed.
        Then the "New Coil" dialog opens and blocks the display
        And the target panel button with name "cancel-button" has a label "Cancelar" and is "enabled"
        When  the button with name "cancel-button" is clicked
        Then the dialog closes

    @D3D05.08
    Scenario: [D3D06.08]-If the New Coil Save and Continue button is clicked then coil is persisted but the dialog is kept open and the contents updated.
        Then the "New Coil" dialog opens and blocks the display
        Given "PLA" is set on form field "material"
        And "AMARILLO" is set on form field "color"
        And 800 is set on form field "weight"
        Then the target panel button with name "repeat-button" has a label "Guardar y Repetir" and is "enabled"
        When the button with name "repeat-button" is clicked
        Then the coil is persisted at the backend
        And the target panel has a form field named "material" with label "MATERIAL" and contents "PLA"
        And the target panel has a form field named "color" with label "COLOR" and empty
        And the target panel has a form field named "weight" with label "PESO" and empty

    # - E X C E P T I O N S
    @D3D05.E.01
    Scenario: [D3D05.E.01]-If the user tryes to enter an string on the numeric fields it fails.
        Then the "New Coil" dialog opens and blocks the display
        And "800gr" is set on form field "weight"
        And the target panel has a form field named "weight" with label "PESO" and contents "800"
        And "gr800" is set on form field "weight"
        And the target panel has a form field named "weight" with label "PESO" and empty
    @D3D05.E.02
    Scenario: [D3D05.E.02]-The weight field does not accept negative values
        Then the "New Coil" dialog opens and blocks the display
        And "-801" is set on form field "weight"
        And the target panel has a form field named "weight" with label "PESO" and contents "1"
