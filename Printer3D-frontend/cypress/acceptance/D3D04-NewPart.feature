
@D3D04
Feature: [D3D04]-Define the requirements for the New Part dialog interactions

    One of the key elements to work are the Parts. They are created on a Dialog that has two entries. The first is a Feature button that creates a new Part.
    The second is duplicating a previous existing Part inheriting some but not all of the field values.
    Describe and test the creation of a new Part.
    Describe and test the duplication of an existing part creating a new Part.

    Background: Dock Default Configuration setup
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    # @D3D04.01
    # Scenario: [D3D04.01]-If the Feature New Part receives a click then we should show the New Part Dialog.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVA PIEZA"
    #     Then the "New Part" dialog opens and blocks the display

    # @D3D04.02
    # Scenario: [D3D04.02]-A new part dialog should have most of the fields empty. There are predefined values for the stock, weight and material.
    #     Given there is a click on Feature "/NUEVA PIEZA"
    #     Then the "New Part" dialog opens and blocks the display
    #     And the target dialog has a title "/INVENTARIO/NUEVA PIEZA"
    #     And the target panel has a form field named "label" with label "ETIQUETA" and empty
    #     And the target panel has a form field named "description" with label "DESCRIPCIÓN" and empty
    #     And the target panel has a form field named "material" with label "MATERIAL" and contents "PLA"
    #     And the target panel has a form field named "color" with label "COLOR" and empty
    #     And the target panel has a form field named "weight" with label "PESO" and contents "1"
    #     And the target panel has a form field named "cost" with label "COSTE FAB." and empty
    #     And the target panel has a form field named "price" with label "PRECIO" and empty
    #     And the target panel has a form field named "buildTime" with label "TIEMPO" and empty
    #     And the target panel has a form field named "stock" with label "STOCK DESEADO" and contents "1"
    #     And the target panel has a form field named "stockAvailable" with label "DISPONIBLES" and contents "0"
    #     And the target panel has a form field named "imagePath" with label "IMAGEN" and empty
    #     And the target panel has a form field named "modelPath" with label "FICHERO MODELO" and empty

    # @D3D04.03
    # Scenario: [D3D04.03]-An empty Part dialog has some of the fields mandatory and invalid because they are empty. Also the Save buttons are disabled.
    #     Given there is a click on Feature "/NUEVA PIEZA"
    #     Then the "New Part" dialog opens and blocks the display
    #     # - Check the state of the fields
    #     Then the target panel input field named "label" is "invalid"
    #     And the target panel input field named "description" is "indiferent"
    #     And the target panel input field named "material" is "valid"
    #     And the target panel input field named "color" is "invalid"
    #     And the target panel input field named "weight" is "valid"
    #     And the target panel input field named "cost" is "invalid"
    #     And the target panel input field named "price" is "invalid"
    #     And the target panel input field named "buildTime" is "invalid"
    #     And the target panel input field named "stock" is "indiferent"
    #     And the target panel input field named "stockAvailable" is "indiferent"
    #     And the target panel input field named "imagePath" is "indiferent"
    #     And the target panel input field named "modelPath" is "indiferent"
    #     # - Check the state of the buttons
    #     And the target panel button with name "repeat-button" has a label "Guardar y Repetir" and is "disabled"
    #     And the target panel button with name "submit-button" has a label "Guardar" and is "disabled"
    #     And the target panel button with name "cancel-button" has a label "Cancelar" and is "enabled"
# 
    # @D3D04.04
    # Scenario: [D3D04.04]-If we enter enough characters the label field changes to 'valid'.
    #     Given there is a click on Feature "/NUEVA PIEZA"
    #     Then the "New Part" dialog opens and blocks the display
    #     # - Check the field contents size
    #     Then the target panel field "label" is tested for size constraints 3 and 50
    #     Then the target panel field "cost" is tested for value constraints
    #     Then the target panel field "price" is tested for value constraints
    #     Then the target panel field "buildTime" is tested for value constraints

    @D3D04.05
    Scenario: [D3D04.05]-If all the mandatory fields are filled then the Save buttons activate.
        Given there is a click on Feature "/NUEVA PIEZA"
        Then the "New Part" dialog opens and blocks the display
        # - Fill all the mandatory fields
        Given "-ETIQUETA-" is set on form field "label"
        And "AMARILLO" is set on form field "color"
        And 1 is set on form field "cost"
        And 2 is set on form field "price"
        And 3 is set on form field "buildTime"
        #     # - Check the state of the buttons
        And the target panel button with name "repeat-button" has a label "Guardar y Repetir" and is "enabled"
        And the target panel button with name "submit-button" has a label "Guardar" and is "enabled"
        And the target panel button with name "cancel-button" has a label "Cancelar" and is "enabled"

# @D3D04.03
# Scenario: [D3D04.03]-After a Part duplication when the new Part feature is clicked the form should be with the fields empty.
#     Given one instance of Dock

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
