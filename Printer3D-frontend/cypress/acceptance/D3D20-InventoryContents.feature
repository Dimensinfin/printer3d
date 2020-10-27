@D3D20
Feature: [D3D20]-[STORY] Describe the contents for the Inventory panel but just related to presentation and accounting.

    The inventory can contain Models and parts.
    Parts are aggregated by Part Groups that share the label so the listing will be easier to handle.
    If the cursor clicks over a Model then the model contents are displayed.
    If the user clicks a part Group then the contents are displayed.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/INVENTARIO"
        Then the page "Inventory Page" is activated
        Given the target is the panel of type "catalog"

    # -  I N V E N T O R Y   P A G E
    @D3D20.01
    Scenario: [D3D20.01.1]-The inventory contents has some Models and some Parts
        Then the target has the title "/CATALOGO PIEZAS Y MODELOS"
        Then the target has 1 "model"
        Then the target has 6 "part-container"

    @D3D20.01
    Scenario: [D3D20.01.2]-There is a checkbox to change the filter state.
        Then form field named "inactiveFilter" with label "Filtrar elementos inactivos." has contents "on"

    @D3D20.01
    Scenario: [D3D20.01.3]-If the filter is deactivate then the inactive elements are visible.
        Then form field named "inactiveFilter" with label "Filtrar elementos inactivos." has contents "on"
        When form checkbox named "inactiveFilter" is clicked
        And the loading panel completes
        # - Expand a container
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        Given the target is the panel of type "catalog"
        Then the target has 2 "model"
        Then the target has 6 "part-container"
        # - Count the Parts on the panel because they are not contained on the Part Container
        Then the target has 12 "part"

    # - M O D E L
    @D3D20.02
    Scenario: [D3D20.02.1]-Validate the contents of a Model.
        # - Check the model contents visible on the catalog
        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then field named "label" with label "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Verde"
        And field named "partCount" with label "NUMERO PIEZAS" has contents "6"
        And field named "price" with label "PRECIO" has contents "15 €"
        And target has an actionable image named "edit-button"
        And actionable image named "edit-button" is "enabled"
        And active Model shows a violet corner

    @D3D20.02
    Scenario: [D3D20.02.2]-Validate the different color tagging for the Model.
        Given the target is the panel of type "catalog"
        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a "blueviolet" tag
        Given the target is the panel of type "catalog"
        When form checkbox named "inactiveFilter" is clicked
        And the loading panel completes
        Given the target the "model" with id "197a3982-ad89-4987-b41e-392da1d376d9"
        Then the target item has a "red" tag

    @D3D20.02
    Scenario: [D3D20.02.3]-Validate the contents of a Model. If the Model is clicked then the Model expands to show the contents.
        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        When the target is clicked
        # - Check the model contents shown when expanded
        Then the target has a panel labeled "COMPOSICION" named "part-composition"
        Then the target has 3 "part-stack"
        Given the target the "part-stack" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
        Then column named "quantity" has contents "x 1"
        And column named "label" has contents "PLATAFORMA SLOT 1/32 - Guarda Tornillos"
        And column named "material" has contents "PLA/BLANCO"

    # - P A R T   G R O U P
    @D3D20.03
    Scenario: [D3D20.03.1]-Validate the contents for the Part Group.
        # - Check the Part Group contents
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        Then field named "label" with label "ETIQUETA" has contents "Boquilla Ganesha - Figura"
        And field named "description" with label "DESCRIPCION" has contents "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables."
        And field named "buildTime" with label "TIEMPO" has contents "90 min."
        And field named "weight" with label "PLASTICO" has contents "8 gr."
        And target has an actionable image named "edit-button"
        And actionable image named "edit-button" is "enabled"
        And target has an actionable image named "expand-button"

    @D3D20.03
    Scenario: [D3D20.03.2]-If a Part Container is expanded the number of Part on the Catalog changes.
        # - The initial count should be zero.
        Given the target is the panel of type "catalog"
        Then the target has no "part"
        # - Expand a container
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        Given the target item is expandable
        When the target item is expanded
        # - Count the Parts on the panel because they are not contained on the Part Container
        Given the target is the panel of type "catalog"
        Then the target has 4 "part"

    # - P A R T
    @D3D20.04
    Scenario: [D3D20.04.01]-Validate a part when it is not being edited. Validate an active Part
        # - Expand a Part Group to see the Parts
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        # - Check the Part contents for an ACTIVE Part.
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then field named "material" with label "MATERIAL/COLOR" has contents "PLA/ROSA"
        And field named "stock" with label "STOCK" has contents "5"
        And field named "stockAvailable" with label "DISPONIBLE" has contents "0"
        And field named "cost" with label "COSTE" has contents "1 €"
        And field named "price" with label "PRECIO" has contents "6 €"
        And field named "active" with label "ACTIVA" has contents "ACTIVA"
        And target has an actionable image named "edit-button"
        And target has an actionable image named "duplicate-button"
        And target has an actionable image named "save-disabled"

    @D3D20.04
    Scenario: [D3D20.04.02]-Validate a part when it is not being edited. Validate an inactive Part
        When form checkbox named "inactiveFilter" is clicked
        And the loading panel completes
        # - Expand a Part Group to see the Parts
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        # - Check the Part contents for an inactive Part.
        Given the target the "part" with id "4cf23190-d140-4681-93e5-2b2d02dfba39"
        Then field named "material" with label "MATERIAL/COLOR" has contents "PLA/VERDE TRANSPARENTE"
        And field named "stock" with label "STOCK" has contents "5"
        And field named "stockAvailable" with label "DISPONIBLE" has contents "4"
        And field named "cost" with label "COSTE" has contents "1 €"
        And field named "price" with label "PRECIO" has contents "6 €"
        And field named "active" with label "ACTIVA" has contents "FUERA PROD."
        And target has an actionable image named "edit-button"
        And target has an actionable image named "duplicate-button"
        And target has an actionable image named "save-disabled"

    @D3D20.04
    Scenario: [D3D20.04.03]-Validate a part when it is not being edited. Validate an inactive Part
        When form checkbox named "inactiveFilter" is clicked
        # - Expand a Part Group to see the Parts
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        # - Check the Part contents for an CANCELED Part.
        Given the target the "part" with id "4cf23190-d140-4681-93e5-2b2d02dfba39"
        Then field named "material" with label "MATERIAL/COLOR" has contents "PLA/VERDE TRANSPARENTE"
        And field named "stock" with label "STOCK" has contents "5"
        And field named "stockAvailable" with label "DISPONIBLE" has contents "4"
        And field named "cost" with label "COSTE" has contents "1 €"
        And field named "price" with label "PRECIO" has contents "6 €"
        And field named "active" with label "ACTIVA" has contents "FUERA PROD."
        And target has an actionable image named "edit-button"
        And target has an actionable image named "duplicate-button"
        And target has an actionable image named "save-disabled"

    @D3D20.05
    Scenario: [D3D20.05]-Validate the different color tagging for the Parts states.
        When form checkbox named "inactiveFilter" is clicked
        # - Expand a Part Group to see the Parts
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then the target item has a "greenyellow" tag
        And active "part" shows a green corner
        Given the target the "part" with id "4cf23190-d140-4681-93e5-2b2d02dfba39"
        Then the target item has a "orangered" tag
        And inactive "part" shows an orange corner

    @D3D20.09
    Scenario: [D3D20.09]-Validate the contents of a Part.
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        # - Validate the fields of a Part inside a part group
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then field named "material" with label "MATERIAL/COLOR" has contents "PLA/ROSA"
        And field named "stock" with label "STOCK" has contents "5"
        And field named "stockAvailable" with label "DISPONIBLE" has contents "0"
        And field named "cost" with label "COSTE" has contents "1 €"
        And field named "price" with label "PRECIO" has contents "6 €"
        And field named "active" with label "ACTIVA" has contents "ACTIVA"
