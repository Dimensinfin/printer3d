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
    @D3D18.01
    Scenario: [D3D18.01]-The Part Container has some fields and two buttons to activate the Edit In Place feature.
        Given the target is the panel of type "catalog"
        Given the target item the "part-container" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
        Then target has an actionable image named "edit-button"
        And actionable image named "edit-button" is "enabled"
        And target has an actionable image named "save-button"
        And actionable image named "save-button" is "disabled"

    @D3D18.02
    Scenario: [D3D18.02]-The Part Container group now has an edit button that toggles the editing of some properties for a set of Parts. All the parts that share the label belong to the same Part Group and are edited at the same time.
        Given the target is the panel of type "catalog"
        Given the target item the "part-container" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
        Then target has an actionable image named "edit-button"
        And actionable image named "edit-button" is "enabled"
        # - Click to activate edit state
        When target actionable image "edit-button" is clicked
        Then field named "description" is editable
        And field named "buildTime" is editable
        And field named "weight" is editable
        And field named "imagePath" is editable
        And field named "modelPath" is editable
        # - Click to deactivate edit state
        When target actionable image "edit-button" is clicked
        Then field named "description" is not editable
        And field named "buildTime" is not editable
        And field named "weight" is not editable
        And field named "imagePath" is not editable
        And field named "modelPath" is not editable

        @D3D18.03
    Scenario: [D3D18.03]-Validate the editable Part Container fields for constraints.
        # Then field named "imagePath" is editable
        # Then field named "modelPath" is editable
        # And target has an actionable image named "update-button"


        # Then the target item has a actionable image named "update-button"


        # Given there is a click on Feature "/INVENTARIO"
        # Then the page "InventoryPage" is activated
        # Given the target is the panel of type "catalog"
        # Given the target item the "part-container" with id "52372bd9-76a3-4f65-926e-a50a896961c0"
        # Then the target item has a actionable image named "edit-button"
        # When the target item actionable image "edit-button" is clicked

        # Then new the target item text field named "description" is editable
        And the target panel has a input field named "description" with label "DESCRIPCION" and contents "Base para la plataforma de slot cars."
        And the target panel has a input field named "buildTime" with label "TIEMPO" and contents "30 min."
        And the target panel has a input field named "weight" with label "PESO" and contents "4 gr."
        And the target panel has a input field named "imagePath" with label "IMAGEN" and contents "https://ibb.co/3dGbsRh"
        And the target panel has a input field named "modelPath" with label "FICHERO IMPR." and contents "pieza3.sft"



    # @D3D04.03
    # Scenario: [D3D04.03]-After a Part duplication when the new Part feature is clicked the form should be with the fields empty.
    #     Given one instance of Dock







    # DISABLED
    # @D3D18.01
    # Scenario: [D3D18.01]-There is a new field on the Part that is the weight of plastic that needs to be used to build an instance.
    #     Then the target item has a field named "label" with label "ETIQUETA" and value "PLATAFORMA SLOT 1/32 - Guarda Tornillos"
    #     And the target item has a field named "description" with label "DESCRIPCION" and value "Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot"
    #     And the target item has a field named "buildTime" with label "TIEMPO" and value "45 min."
    #     And the target item has a field named "weight" with label "PLASTICO" and value "6 gr."


    @D3D18.03
    Scenario: [D3D18.03]-Validate the contents of a Part Container. Now there are new fields and the Image and Model File should also be visible if not empty.
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated
        Given the target is the panel of type "catalog"
        Given the target item the "part-container" with id "52372bd9-76a3-4f65-926e-a50a896961c0"
        Then the target item has a field named "label" with label "ETIQUETA" and value "PLATAFORMA SLOT 1/32 - Base"
        And the target item has a field named "description" with label "DESCRIPCION" and value "Base para la plataforma de slot cars."
        And the target item has a field named "buildTime" with label "TIEMPO" and value "30 min."
        And the target item has a field named "weight" with label "PLASTICO" and value "4 gr."
        And the target item has a field named "imagePath" with label "IMAGEN" and value "https://ibb.co/3dGbsRh"
        And the target item has a field named "modelPath" with label "FICHERO IMPR." and value "pieza3.sft"

# PENDING REVIEW AND UPDATE
# @D3D07 @D3D07.13
# Scenario: [D3D07.13]-When the duplicate button of any Part is clicked then there is a new Duplicate Part dialog.
#     Given there is a click on Feature "/INVENTARIO"
#     Then the page "InventoryPage" is activated
#     Given the target is the panel of type "catalog"
#     Given the target item the "part-container" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
#     Then on the target panel there are none "part"
#     Then the target item is expandable
#     When the target item expand-collapse button is clicked
#     Then on the target panel there are "2" "part"
#     Given the target item the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
#     When the target Part Duplicate button is clicked
#     Then the "New Part" dialog opens and blocks the display
#     Given the target is the panel of type "New Part"
#     Then the target panel has a input field named "label" with label "ETIQUETA" and contents "Boquilla Ganesha - Embocadura"
#     Then the target panel has a textarea field named "description" with label "DESCRIPCIÓN" and contents "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables."
#     # Then the target panel has a select field named "material" with label "MATERIAL" and value "TPU"
#     Then the target panel has a input field named "cost" with label "COSTE FAB." and contents "0.45"
#     Then the target panel has a input field named "price" with label "PRECIO" and contents "1"
#     Then the target panel has a input field named "buildTime" with label "TIEMPO" and contents "20"
#     Then the target panel has a input field named "stockLevel" with label "STOCK DESEADO" and value "15"
#     Then the target panel has a input field named "stockAvailable" with label "STOCK ACTUAL" and value "0"
