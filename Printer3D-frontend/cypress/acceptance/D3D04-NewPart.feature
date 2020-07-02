
@D3D04
Feature: [D3D04]-Define the requirements for the New Part dialog interactions

    If the user interacts with Inventory features that affect the Parts there should be requirements to control the
    presentation, contents and interaction flow. Parts cannot be eliminated but some of their fields are editable
    once the part is constructed.
    This feature will deal with the requirements when a new Part is created.

    Background: Dock Default Configuration setup
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D04.01
    Scenario: [D3D04.01]-If the Feature New Part received a click then we should show the New Part Dialog.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the "New Part" dialog opens and blocks the display

    @D3D04.02
    Scenario: [D3D04.02]-A new part dialog should have some fields empty. There are predefined values for the stock
        Given there is a click on Feature "/NUEVA PIEZA"
        Then the "New Part" dialog opens and blocks the display
        And the target panel has a title "/INVENTARIO/NUEVA PIEZA"
        And the target panel has an input field named "label" with label "ETIQUETA" and empty
        # And the target panel has an input field named "description" with label "DESCRIPCIÓN" and empty
        # And the target panel has an input field named "material" with label "MATERIAL" and empty
        # And the target panel has an input field named "color" with label "COLOR" and empty
        And the target panel has an input field named "weight" with label "PESO" and contents "1"
        And the target panel has an input field named "cost" with label "COSTE FAB." and empty
        And the target panel has an input field named "price" with label "PRECIO" and empty
        And the target panel has an input field named "buildTime" with label "TIEMPO" and empty

    @D3D04.03
    Scenario: [D3D04.03]-After a Part duplication when the new Part feature is clicked the form should be with the fields empty.
        Given one instance of Dock
     # @D3D04 @D3D04.05
    # Scenario: [D3D04.05]-A new part dialog should have some fields with default values
    #     Given there is a click on Feature "/NUEVA PIEZA"
    #     Then the New Part dialog opens and blocks the display
    #     And the New Part dialog input field "material" should not be empty
    #     And the New Part dialog input field "color" should be "INDEFINIDO"
    #     And the New Part dialog input field "stockLevel" should be "1"
    #     And the New Part dialog input field "stockAvailable" should be "0"

    # @D3D04 @D3D04.06
    # Scenario: [D3D04.06]-A New Part dialog should have two buttons. One to save the new part and another to cancel the operation.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVA PIEZA"
    #     Then the New Part dialog opens and blocks the display
    #     And the button "SAVE" has the next properties
    #         | label         | state    |
    #         | Guardar Datos | disabled |
    #     And the button "CANCEL" has the next properties
    #         | label          | state   |
    #         | Go Back/Cancel | enabled |

    # @D3D04 @D3D04.07
    # Scenario: [D3D04.07]-When all the required fields are filled with the right values the Save button activates.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVA PIEZA"
    #     Then the New Part dialog opens and blocks the display
    #     And "Pieza de Prueba 1" is set on the New Part dialog input field "label"
    #     And "PLA" is set on the New Part dialog dropdown field "material"
    #     And "PLATEADO" is set on the New Part dialog dropdown field "color"
    #     And "35" is set on the New Part dialog input field "buildTime"
    #     And "0.8" is set on the New Part dialog input field "cost"
    #     And "3" is set on the New Part dialog input field "price"
    #     And "3" is set on the New Part dialog input field "stockLevel"
    #     And the button "SAVE" has the next properties
    #         | label         | state   |
    #         | Guardar Datos | enabled |
    #     And the button "CANCEL" has the next properties
    #         | label          | state   |
    #         | Go Back/Cancel | enabled |

    # @D3D04 @D3D04.08
    # Scenario: [D3D04.08]-If the New Part Save button is clicked then the part is stored at the repository and a notification is thrown.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVA PIEZA"
    #     Then the New Part dialog opens and blocks the display
    #     And "Pieza de Prueba 1" is set on the New Part dialog input field "label"
    #     And "PLA" is set on the New Part dialog dropdown field "material"
    #     And "PLATEADO" is set on the New Part dialog dropdown field "color"
    #     And "35" is set on the New Part dialog input field "buildTime"
    #     And "0.8" is set on the New Part dialog input field "cost"
    #     And "3" is set on the New Part dialog input field "price"
    #     And "3" is set on the New Part dialog input field "stockLevel"
    #     When there is a click on the "GUARDAR" button
    #     Then the part is persisted at the backend
    #     And the dialog closes

    # @D3D04 @D3D04.09
    # Scenario: [D3D04.09]-If the New Part Cancel button is clicked then dialog is closed.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVA PIEZA"
    #     Then the New Part dialog opens and blocks the display
    #     And "Pieza de Prueba 1" is set on the New Part dialog input field "label"
    #     And "PLA" is set on the New Part dialog dropdown field "material"
    #     And "PLATEADO" is set on the New Part dialog dropdown field "color"
    #     And "35" is set on the New Part dialog input field "buildTime"
    #     And "0.8" is set on the New Part dialog input field "cost"
    #     And "3" is set on the New Part dialog input field "price"
    #     And "3" is set on the New Part dialog input field "stockLevel"
    #     When there is a click on the "CLOSE" button
    #     Then the dialog closes

    # # @D3D04 @D3D04.10
    # # Scenario: [D3D04.10]-If the New Part Save and Continue button is clicked then part is saved but the dialog is kept open and the contents updated.
    # #     Given one instance of Dock
    # #     When there is a click on Feature "/NUEVA PIEZA"
    # #     Then the New Part dialog opens and blocks the display
    # #     And "Pieza de Prueba 1" is set on the New Part dialog input field "label"
    # #     And "PLA" is set on the New Part dialog dropdown field "material"
    # #     And "PLATEADO" is set on the New Part dialog dropdown field "color"
    # #     And "35" is set on the New Part dialog input field "buildTime"
    # #     And "0.8" is set on the New Part dialog input field "cost"
    # #     And "3" is set on the New Part dialog input field "price"
    # #     And "3" is set on the New Part dialog input field "stockLevel"
    # #     When there is a click on the "GUARDAR-NUEVO" button
    # #     Then the part is persisted at the backend
    # #     And the New Part dialog input field "color" should be "INDEFINIDO"

    # # @D3D04 @D3D04.11
    # # Scenario: [D3D04.11] The dialog Material field should be populated with the list of material types from the backend list.
    # #     Given one instance of Dock
    # #     When there is a click on Feature "/NUEVA PIEZA"
    # #     Then the New Part dialog opens and blocks the display
    # #     And there is a call to the backend to get the Finishings
    # #     And the Material dropdown has 3 elements
    # #     And form fields have the next values
    # #         | material |
    # #         | PLA      |
    # #     And the Color dropdown has 12 values

    # @D3D04 @D3D04.12
    # Scenario: [D3D04.12]-The New Part dialog has an specific set of fields with length validation.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVA PIEZA"
    #     Then the New Part dialog opens and blocks the display
    #     And there is a dialog title saying "/INVENTARIO/NUEVA PIEZA"
    #     When the target form is the New Part dialog
    #     And on target input form there is a field named "ETIQUETA" with name "label" and content up to "50" characters
