@D3D18
Feature: [D3D18]-[STORY] Parts have fields editable both at the generic definition level and at the instance definition level

    There is a button on the Inventory table to togle the 'Edit in Place' for a Part or Part Group. A Part group is just an artificial structure to
    define a set of Parts that have some set of common field values.
    There is another button to edit the properties of a single Part instance.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated

    # - H A P P Y   P A T H
    # @D3D18.01
    # Scenario: [D3D18.01]-The Part Container has some fields and two buttons to activate the Edit In Place feature.
    #     Given the target is the panel of type "catalog"
    #     Given the target the "part-container" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
    #     Then target has an actionable image named "edit-button"
    #     And actionable image named "edit-button" is "enabled"
    #     And target has an actionable image named "save-button"
    #     And actionable image named "save-button" is "disabled"

    # @D3D18.02
    # Scenario: [D3D18.02]-The Part Container group now has an edit button that toggles the editing of some properties for a set of Parts. All the parts that share the label belong to the same Part Group and are edited at the same time.
    #     Given the target is the panel of type "catalog"
    #     Given the target the "part-container" with id "52372bd9-76a3-4f65-926e-a50a896961c0"
    #     Then target has an actionable image named "edit-button"
    #     And actionable image named "edit-button" is "enabled"
    #     # - Click to activate edit state
    #     When target actionable image "edit-button" is clicked
    #     # Then field named "description" is editable
    #     Given the target the "part-container" with id "52372bd9-76a3-4f65-926e-a50a896961c0"
    #     And field named "buildTime" is editable
    #     And field named "weight" is editable
    #     And field named "imagePath" is editable
    #     And field named "modelPath" is editable
    #     # - Click to deactivate edit state
    #     When target actionable image "edit-button" is clicked
    #     Given the target the "part-container" with id "52372bd9-76a3-4f65-926e-a50a896961c0"
    #     Then field named "description" is not editable
    #     And field named "buildTime" is not editable
    #     And field named "weight" is not editable
    #     And field named "imagePath" is not editable
    #     And field named "modelPath" is not editable

    # @D3D18.03
    # Scenario: [D3D18.03]-Validate the editable Part Container fields for constraints.
    #     Given an editable Part Container with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
    #     Then field named "buildTime" is tested for value constraints 1
    #     And field named "weight" is tested for value constraints 1 to 100
    #     And field named "imagePath" is tested for max size of 100
    #     And field named "modelPath" is tested for max size of 100
    #     And field named "description" is tested for max size of 500

    # @D3D18.04
    # Scenario: [D3D18.04]-Validate the contents of a Part Container. Now there are new fields and the Image and Model File should also be visible if not empty.
    #     Given the target is the panel of type "catalog"
    #     Given the target the "part-container" with id "52372bd9-76a3-4f65-926e-a50a896961c0"
    #     Then field named "label" with label "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Base"
    #     And field named "description" with label "DESCRIPCION" has contents "Base para la plataforma de slot cars."
    #     And field named "buildTime" with label "TIEMPO" has contents "30 min."
    #     And field named "weight" with label "PLASTICO" has contents "4 gr."
    #     And field named "imagePath" with label "IMAGEN" has contents "https://ibb.co/3dGbsRh"
    #     And field named "modelPath" with label "FICHERO IMPR." has contents "pieza3.sft"

    # @D3D04.03
    # Scenario: [D3D04.03]-After a Part duplication when the new Part feature is clicked the form should be with the fields empty.
    #     Given one instance of Dock








    # PENDING REVIEW AND UPDATE
    @D3D18.05
    Scenario: [D3D18.05]-When the duplicate button of any Part is clicked then there is a new Duplicate Part dialog.
        # - Expand the Part Container
        Given the target is the panel of type "catalog"
        Then the target has no "part"
        Given the target the "part-container" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
        Then target has an actionable image named "expand-button"
        # - Click the expand button
        When target actionable image "expand-button" is clicked
        Given the target is the panel of type "catalog"
        Then the target has 2 "part"
        Given the target the "part" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
        Then target has an actionable image named "duplicate-button"
        # - Click the duplicate and validate the New Part Dialog
        When target actionable image "duplicate-button" is clicked
        Then the "New Part" dialog opens and blocks the display
        # - Validate the contents for the New Part dialog
        And form field named "label" with label "ETIQUETA" has contents "Boquilla Ganesha - Embocadura"
        And form field named "description" with label "DESCRIPCION" has contents "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables."
        And form field named "material" with label "MATERIAL" has contents "TPU"
        And form field named "color" with label "COLOR" is empty
        And form field named "weight" with label "PESO" has contents "8"
        And form field named "cost" with label "COSTE FAB." has contents "0.45"
        And form field named "price" with label "PRECIO" has contents "1"
        And form field named "buildTime" with label "TIEMPO" has contents "20"
        And form field named "stock" with label "STOCK DESEADO" has contents "15"
        And form field named "stockAvailable" with label "STOCK ACTUAL" has contents "0"
        And form field named "imagePath" with label "IMAGEN" is empty
        And form field named "modelPath" with label "FICHERO MODELO" is empty
        # - Check the state of the buttons
        And the button with name "repeat-button" has a label "Guardar y Repetir" and is "disabled"
        And the button with name "submit-button" has a label "Guardar" and is "disabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"

# @D3D18.05
#     Scenario: [D3D18.05]-When the duplicated New part disalog is open and the color fileld then the save buttons activate.
# Given a duplicated New Part from Part id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"




#     Given the target is the panel of type "New Part"
#     Then the target panel has a input field named "label" with label "ETIQUETA" and contents "Boquilla Ganesha - Embocadura"
#     Then the target panel has a textarea field named "description" with label "DESCRIPCIÓN" and contents "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables."
#     # Then the target panel has a select field named "material" with label "MATERIAL" and value "TPU"
#     Then the target panel has a input field named "cost" with label "COSTE FAB." and contents "0.45"
#     Then the target panel has a input field named "price" with label "PRECIO" and contents "1"
#     Then the target panel has a input field named "buildTime" with label "TIEMPO" and contents "20"
#     Then the target panel has a input field named "stockLevel" with label "STOCK DESEADO" and value "15"
#     Then the target panel has a input field named "stockAvailable" with label "STOCK ACTUAL" and value "0"
