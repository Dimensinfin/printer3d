
@D3D04
Feature: [D3D04]-Define the requirements for the New Part dialog interactions

    One of the key elements to work are the Parts. They are created on a Dialog that has two entries. The first is a Feature button that creates a new Part.
    The second is duplicating a previous existing Part inheriting some but not all of the field values.
    Describe and test the creation of a new Part.
    Describe and test the duplication of an existing part creating a new Part.

    Background: Dock Default Configuration setup
        Given the application Printer3DManager
        Given there is a click on Feature "/NUEVA PIEZA"
        Then the "New Part" dialog opens and blocks the display

    # - H A P P Y   P A T H
    @D3D04.02
    Scenario: [D3D04.02]-A new part dialog should have most of the fields empty. There are predefined values for the stock, weight and material.
        And the target has the title "/INVENTARIO/NUEVA PIEZA"
        And form field named "label" with label "ETIQUETA" is empty
        And form field named "description" with label "DESCRIPCIÓN" is empty
        And form field named "material" with label "MATERIAL" has contents "PLA"
        And form field named "color" with label "COLOR" is empty
        And form field named "weight" with label "PESO" has contents "1"
        And form field named "cost" with label "COSTE FAB." is empty
        And form field named "price" with label "PRECIO" is empty
        And form field named "buildTime" with label "TIEMPO" is empty
        And form field named "stock" with label "STOCK DESEADO" has contents "1"
        And form field named "stockAvailable" with label "DISPONIBLES" has contents "0"
        And form field named "imagePath" with label "IMAGEN" is empty
        And form field named "modelPath" with label "FICHERO MODELO" is empty

    @D3D04.03
    Scenario: [D3D04.03]-An empty Part dialog has some of the fields mandatory and invalid because they are empty. Also the Save buttons are disabled.
        # - Check the state of the fields
        Then form field named "label" is "invalid"
        And form field named "description" is "indiferent"
        And form field named "material" is "valid"
        And form field named "color" is "invalid"
        And form field named "weight" is "valid"
        And form field named "cost" is "invalid"
        And form field named "price" is "invalid"
        And form field named "buildTime" is "invalid"
        And form field named "stock" is "indiferent"
        And form field named "stockAvailable" is "indiferent"
        And form field named "imagePath" is "indiferent"
        And form field named "modelPath" is "indiferent"
        # - Check the state of the buttons
        And the button with name "repeat-button" has a label "Guardar y Repetir" and is "disabled"
        And the button with name "submit-button" has a label "Guardar" and is "disabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D04.04
    Scenario: [D3D04.04]-If we enter enough characters the label field changes to 'valid'.
        # - Check the field contents size
        Then field named "label" is tested for size constraints 3 and 50
        And field named "weight" is tested for value constraints 1 to 100
        And field named "cost" is tested for numeric constraints 0.01
        And field named "price" is tested for numeric constraints 0.01
        And field named "buildTime" is tested for value constraints 1
        And field named "stock" is tested for value constraints 1 to 15
        And field named "stockAvailable" is tested for value constraints 0
        And field named "imagePath" is tested for max size of 100
        And field named "modelPath" is tested for max size of 100
        And field named "description" is tested for max size of 500

    @D3D04.05
    Scenario: [D3D04.05]-If all the mandatory fields are filled then the Save buttons activate.
         # - Fill all the mandatory fields
        Given "-ETIQUETA-" is set on form field "label"
        And "PLA" is set on form field "material"
        And "AMARILLO" is set on form field "color"
        And 1 is set on form field "cost"
        And 2 is set on form field "price"
        And 3 is set on form field "buildTime"
        # - Check the state of the buttons
        And the button with name "repeat-button" has a label "Guardar y Repetir" and is "enabled"
        And the button with name "submit-button" has a label "Guardar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D04.06
    Scenario: [D3D04.06]-If the New Part Save button is clicked then the part is stored at the repository and a notification is shown.
        # - Fields fields to validate the form.
        Given a valid New Part
        # - Check the state of the buttons
        And the button with name "repeat-button" has a label "Guardar y Repetir" and is "enabled"
        And the button with name "submit-button" has a label "Guardar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"
        # - Click on the Save button
        When the button with name "submit-button" is clicked
        Then the part is persisted at the backend
        And there is a "success" Notification panel
        And the dialog closes

    @D3D04.07
    Scenario: [D3D04.07]-If the New Part Save and Continue button is clicked then the part is stored at the repository and a notification is shown but the dialog remains open and some fields updated.
        # - Fields fields to validate the form.
        Given a valid New Part
        # - Check the state of the buttons
        And the button with name "repeat-button" has a label "Guardar y Repetir" and is "enabled"
        And the button with name "submit-button" has a label "Guardar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"
        # - Click on the Save button
        When the button with name "repeat-button" is clicked
        Then the part is persisted at the backend
        And there is a "success" Notification panel
        And form field named "color" is "invalid"

    @D3D04.08
    Scenario: [D3D04.08]-If the New Part Cancel button is clicked then dialog is closed.
        # - Fields fields to validate the form.
        Given a valid New Part
        # - Check the state of the buttons
        And the button with name "repeat-button" has a label "Guardar y Repetir" and is "enabled"
        And the button with name "submit-button" has a label "Guardar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"
        # - Click on the Save button
        When the button with name "cancel-button" is clicked
        Then the dialog closes

    @D3D04.09
    Scenario: [D3D04.09]-If the material selector is changed then the list of colors also changes.
        # - Fields fields to validate the form.
        Given a valid New Part
        When "PLA" is set on form field "material"
        Then the Color dropdown has 12 values
        When "TPU" is set on form field "material"
        Then the Color dropdown has 4 values
