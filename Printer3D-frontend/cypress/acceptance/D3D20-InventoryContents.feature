@D3D20
Feature: [D3D20]-[STORY] Describe the contents for the Inventory panel but just related to presentation and accounting.

    The inventory can contain Models and parts.
    Parts are aggregated by Part Groups that share the label so the listing will be easier to handle.
    If the cursor hovers over a Model then the model contents are displayed.
    If the user clicks a part Group then the contents are displayed.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated
        Given the target panel is the panel of type "catalog"

    # - H A P P Y   P A T H
    @D3D20.01
    Scenario: [D3D20.01]-The inventory contents has some Models and some Parts
        Then the target panel has a title "/CATALOGO PIEZAS Y MODELOS"
        Then the target panel has 2 "model"
        Then the target panel has 6 "part-container"

    @D3D20.02
    Scenario: [D3D20.02]-Validate the contents of a Model.
        # - Check the model contents visible on the catalog
        Given the target item the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a field named "label" with label "ETIQUETA" and value "PLATAFORMA SLOT 1/32 - Verde"
        And the target item has a field named "partCount" with label "NUMERO PIEZAS" and value "6"
        And the target item has a field named "price" with label "PRECIO" and value "15 €"
        And    the target item has a actionable image named "edit-button"

    @D3D20.03
    Scenario: [D3D20.03]-Validate the contents for the Part Group.
        # - Check the Part Group contents
        Given the target item the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        Then the target item has a field named "label" with label "ETIQUETA" and value "Boquilla Ganesha - Figura"
        And the target item has a field named "description" with label "DESCRIPCION" and value "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables."
        And the target item has a field named "buildTime" with label "TIEMPO" and value "90 min."
        And the target item has a field named "weight" with label "PLASTICO" and value "8 gr."
        And the target item has a actionable image named "edit-button"
        And the target item has a actionable image named "expand-button"

    @D3D20.04
    Scenario: [D3D20.04]-Validate a part when it is not being edited.
        # - Expand a Part Group to see the Parts
        Given the target item the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        # - Check the Part contents for an ACTIVE Part.
        Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then the target item has a field named "material" with label "MATERIAL/COLOR" and value "PLA/ROSA"
        And the target item has a field named "stock" with label "STOCK" and value "5"
        And the target item has a field named "stockAvailable" with label "DISPONIBLE" and value "0"
        And the target item has a field named "cost" with label "COSTE" and value "1 €"
        And the target item has a field named "price" with label "PRECIO" and value "6 €"
        And the target item has a field named "active" with label "ACTIVA" and value "ACTIVA"
        And the target item has a actionable image named "edit-button"
        And the target item has a actionable image named "duplicate-button"
        And the target item has a disabled image named "save-disabled"
        # - Check the Part contents for an CANCELED Part.
        Given the target item the "part" with id "4cf23190-d140-4681-93e5-2b2d02dfba39"
        Then the target item has a field named "material" with label "MATERIAL/COLOR" and value "PLA/VERDE TRANSPARENTE"
        And the target item has a field named "stock" with label "STOCK" and value "5"
        And the target item has a field named "stockAvailable" with label "DISPONIBLE" and value "4"
        And the target item has a field named "cost" with label "COSTE" and value "1 €"
        And the target item has a field named "price" with label "PRECIO" and value "6 €"
        And the target item has a field named "active" with label "ACTIVA" and value "FUERA PROD."
        And the target item has a actionable image named "edit-button"
        And the target item has a actionable image named "duplicate-button"
        And the target item has a disabled image named "save-disabled"

    @D3D20.05
    Scenario: [D3D20.05]-Validate the different color tagging for the Model and Parts states.
        Given the target item the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a "blueviolet" tag
        # - Expand a Part Group to see the Parts
        Given the target item the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then the target item has a "greenyellow" tag
        Given the target item the "part" with id "4cf23190-d140-4681-93e5-2b2d02dfba39"
        Then the target item has a "orangered" tag

    @D3D20.06
    Scenario: [D3D20.06]-Validate the input fields that should be displayed when the Edit Part is activated.
        # - Activate the Part editing
        Given the target item the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        When the target item actionable image "edit-button" is clicked
        # - Validate edit part form fields
        Then the target item has a form field named "stock" with label "STOCK" and contents "5"
        And the target item has a form field named "stockAvailable" with label "DISPONIBLE" and contents "0"
        And the target item has a form field named "cost" with label "COSTE" and contents "1"
        And the target item has a form field named "price" with label "PRECIO" and contents "6"
        And the target item has a form field named "active" with label "ACTIVA" and contents "on"
        And the target panel input field named "stock" is "valid"
        And the target panel input field named "stockAvailable" is "valid"
        And the target panel input field named "cost" is "valid"
        And the target panel input field named "price" is "valid"
        # - Check that the save button is enabled
        Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        And the actionable image named "save-button" is "enabled"

    @D3D20.07
    Scenario: [D3D20.07]-If any of the editable fields is invalidated then check that the save button is disabled.
        # - Activate the Part editing
        Given the target item the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        When the target item actionable image "edit-button" is clicked
        # - Invalidated a field
        Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        # Then 1 is set on form field "stock"
        Then form field "stock" is cleared
        And the target panel input field named "stock" is "invalid"
        And the actionable image named "save-button" is "disabled"
