@D3D18
Feature: [D3D18]-[STORY] Parts have fields editable both at the generic definition level and at the instance definition level

    There is a button on the Inventory table to togle the 'Edit in Place' for a Part or Part Group. A Part group is just an artificial structure to
    define a set of Parts that have some set of common field values.
    There is another button to edit the properties of a single Part instance.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D18.01
    Scenario: [D3D18.01]-There is a new field on the Part that is the weight of plastic that needs to be used to build an instance.
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated
        Given the target is the panel of type "catalog"
        Given the target item the "part-container" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
        Then the target item has a field named "label" with label "ETIQUETA" and value "PLATAFORMA SLOT 1/32 - Guarda Tornillos"
        And the target item has a field named "description" with label "DESCRIPCION" and value "Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot"
        And the target item has a field named "buildTime" with label "TIEMPO" and value "45 min."
        And the target item has a field named "weight" with label "PLASTICO" and value "6 gr."

    # @D3D18.02
    # Scenario: [D3D18.02]-The Part Container group now has an edit button that toggles the editing of some properties for a set of Parts. All the parts that share the label belong to the same Part Group and are edited at the same time.
    #     Given there is a click on Feature "/INVENTARIO"
    #     Then the page "InventoryPage" is activated
    #     Given the target is the panel of type "catalog"
    #     Given the target item the "part-container" with id "52372bd9-76a3-4f65-926e-a50a896961c0"
    #     Then the target item has a actionable image named "edit-button"
    #     When the target item actionable image "edit-button" is clicked

    #     # Then new the target item text field named "description" is editable
    #     Then the field named "buildTime" of the target item is editable
    #     Then the target item input field named "weight" is editable
    #     Then the target item input field named "imagePath" is editable
    #     Then the target item input field named "modelPath" is editable
    #     Then the target item has a actionable image named "update-button"
    #     And the target panel has a input field named "description" with label "DESCRIPCION" and contents "Base para la plataforma de slot cars."
    #     And the target panel has a input field named "buildTime" with label "TIEMPO" and contents "30 min."
    #     And the target panel has a input field named "weight" with label "PESO" and contents "4 gr."
    #     And the target panel has a input field named "imagePath" with label "IMAGEN" and contents "https://ibb.co/3dGbsRh"
    #     And the target panel has a input field named "modelPath" with label "FICHERO IMPR." and contents "pieza3.sft"

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
