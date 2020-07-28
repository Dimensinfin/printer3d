@D3D20
Feature: [D3D20]-[STORY] Describe the contents for the Inventory panel but just related to presentation and accounting.

    The inventory can contain Models and parts.
    Parts are aggregated by Part Groups that share the label so the listing will be easier to handle.
    If the cursor clicks over a Model then the model contents are displayed.
    If the user clicks a part Group then the contents are displayed.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated
        Given the target is the panel of type "catalog"

    # - H A P P Y   P A T H
    @D3D20.01
    Scenario: [D3D20.01]-The inventory contents has some Models and some Parts
        Then the target has the title "/CATALOGO PIEZAS Y MODELOS"
        Then the target has 2 "model"
        Then the target has 6 "part-container"

    @D3D20.02
    Scenario: [D3D20.02]-Validate the contents of a Model.
        # - Check the model contents visible on the catalog
        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then field named "label" with label "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Verde"
        And field named "partCount" with label "NUMERO PIEZAS" has contents "6"
        And field named "price" with label "PRECIO" has contents "15 €"
        And target has an actionable image named "edit-button"
        And actionable image named "edit-button" is "enabled"

    @D3D20.03
    Scenario: [D3D20.03]-Validate the contents for the Part Group.
        # - Check the Part Group contents
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        Then field named "label" with label "ETIQUETA" has contents "Boquilla Ganesha - Figura"
        And field named "description" with label "DESCRIPCION" has contents "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables."
        And field named "buildTime" with label "TIEMPO" has contents "90 min."
        And field named "weight" with label "PLASTICO" has contents "8 gr."
        And target has an actionable image named "edit-button"
        And actionable image named "edit-button" is "enabled"
        And target has an actionable image named "expand-button"

    @D3D20.04
    Scenario: [D3D20.04]-Validate a part when it is not being edited.
        # - Expand a Part Group to see the Parts
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        # - Check the Part contents for an ACTIVE Part.
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then the target item has a field named "material" with label "MATERIAL/COLOR" and value "PLA/ROSA"
        And the target item has a field named "stock" with label "STOCK" and value "5"
        And the target item has a field named "stockAvailable" with label "DISPONIBLE" and value "0"
        And the target item has a field named "cost" with label "COSTE" and value "1 €"
        And the target item has a field named "price" with label "PRECIO" and value "6 €"
        And the target item has a field named "active" with label "ACTIVA" and value "ACTIVA"
        And target has an actionable image named "edit-button"
        And target has an actionable image named "duplicate-button"
        And the target item has a disabled image named "save-disabled"
        # - Check the Part contents for an CANCELED Part.
        Given the target the "part" with id "4cf23190-d140-4681-93e5-2b2d02dfba39"
        Then the target item has a field named "material" with label "MATERIAL/COLOR" and value "PLA/VERDE TRANSPARENTE"
        And the target item has a field named "stock" with label "STOCK" and value "5"
        And the target item has a field named "stockAvailable" with label "DISPONIBLE" and value "4"
        And the target item has a field named "cost" with label "COSTE" and value "1 €"
        And the target item has a field named "price" with label "PRECIO" and value "6 €"
        And the target item has a field named "active" with label "ACTIVA" and value "FUERA PROD."
        And target has an actionable image named "edit-button"
        And target has an actionable image named "duplicate-button"
        And the target item has a disabled image named "save-disabled"

    @D3D20.05
    Scenario: [D3D20.05]-Validate the different color tagging for the Model and Parts states.
        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a "blueviolet" tag
        # - Expand a Part Group to see the Parts
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then the target item has a "greenyellow" tag
        Given the target the "part" with id "4cf23190-d140-4681-93e5-2b2d02dfba39"
        Then the target item has a "orangered" tag

    @D3D20.06
    Scenario: [D3D20.06]-Validate the input fields that should be displayed when the Edit Part is activated.
        # - Activate the Part editing
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        When target actionable image "edit-button" is clicked
        # - Validate edit part form fields
        Then the target item has a form field named "stock" with label "STOCK" and contents "5"
        And the target item has a form field named "stockAvailable" with label "DISPONIBLE" and contents "0"
        And the target item has a form field named "cost" with label "COSTE" and contents "1"
        And the target item has a form field named "price" with label "PRECIO" and contents "6"
        And the target item has a form field named "active" with label "ACTIVA" and contents "on"
        And form field named "stock" is "valid"
        And form field named "stockAvailable" is "valid"
        And form field named "cost" is "valid"
        And form field named "price" is "valid"
        # - Check that the save button is enabled
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        And actionable image named "save-button" is "enabled"

    @D3D20.07
    Scenario: [D3D20.07]-If any of the editable fields is invalidated then check that the save button is disabled.
        # - Activate the Part editing
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        When target actionable image "edit-button" is clicked
        # - Invalidated a field
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        # Then 1 is set on form field "stock"
        Then form field "stock" is cleared
        And form field named "stock" is "invalid"
        And actionable image named "save-button" is "disabled"

    @D3D20.08
    Scenario: [D3D20.08]-Validate the contents of a Model. If the Model is clicked then the Model expands to show the contents.
        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        When the target is clicked
        # - Check the model contents shown when expanded
        Then the target has a panel labeled "COMPOSICION" named "part-composition"
        Then the target has 3 "part-stack"
        Given the target the "part-stack" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
        Then column named "quantity" has contents "x 1"
        And column named "label" has contents "PLATAFORMA SLOT 1/32 - Guarda Tornillos"
        And column named "material" has contents "PLA/BLANCO"

    @D3D20.09
    Scenario: [D3D20.09]-Validate the contents of a Part.
        # Given there is a click on Feature "/INVENTARIO"
        # Then the page "InventoryPage" is activated
        # Given the target is the panel of type "catalog"
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        # - Validate the fields of a Part inside a part group
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then field named "material" with label "MATERIAL" has contents "PLA/ROSA"
        # And field named "color" with label "COLOR" has contents "ROSA"
        And field named "stock" with label "STOCK" has contents "5"
        And field named "stockAvailable" with label "DISPONIBLE" has contents "0"
        And field named "cost" with label "COSTE" has contents "1 €"
        And field named "price" with label "PRECIO" has contents "6 €"
        And field named "active" with label "ACTIVA" has contents "ACTIVA"

    @D3D20.10
    Scenario: [D3D20.10]-Active Parts show a green corner while inactive show it orange.
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        Given the target item is expandable
        When the target item is expanded
        Then the target panel has 6 "part"

        And active "part" shows a green corner
        And inactive "part" shows an orange corner

    # @D3D20.11
    #     Scenario: [D3D20.11]-If after editing a Model the uses closes the editing session instead of saving changes, when the model is edited again it should have the original values.

    @D3D20.11
    Scenario: [D3D20.11]-Validate the input fields limits and constraints
        # - Activate the Part editing
        Given editing state for Part "6939c6cc-297f-48ca-8f17-25fa18c3dbc7" on Part Container "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        # - Validate part fields for constraints
        #         Then field named "label" is tested for size constraints 3 and 50
        # And field named "weight" is tested for value constraints 1 to 100
        Then field named "cost" is tested for value constraints 0.01
        And field named "price" is tested for value constraints 0.01
        # And field named "buildTime" is tested for value constraints 1
        And field named "stock" is tested for value constraints 1 to 100
        And field named "stockAvailable" is tested for value constraints 0
# And field named "imagePath" is tested for max size of 100
# And field named "modelPath" is tested for max size of 100
# And field named "description" is tested for max size of 500


# Then the target item has a form field named "stock" with label "STOCK" and contents "5"
# And the target item has a form field named "stockAvailable" with label "DISPONIBLE" and contents "0"
# And the target item has a form field named "cost" with label "COSTE" and contents "1"
# And the target item has a form field named "price" with label "PRECIO" and contents "6"
# And the target item has a form field named "active" with label "ACTIVA" and contents "on"
# And form field named "stock" is "valid"
# And form field named "stockAvailable" is "valid"
# And form field named "cost" is "valid"
# And form field named "price" is "valid"
# # - Check that the save button is enabled
# Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
# And actionable image named "save-button" is "enabled"
