@D3D07
Feature: [D3D07]-Validate the Inventory version 2 features and page contents.

    The inventory is an information section that groups elements that are in stock. Availability means that they can be used
    to build things. Stock control may be used to decide if more things should be obtained.
    Inventory actions should allow to create, edit and see the differents elements that can be stocked.
    The Part list should be changed to a master-detail concept. When  the list is displayed only is displayed the main part
    information. If the Part is clicked then the Part expands and the detailed stock data is shown.
    With the addition of Models they should appear at the top of the inventory list.
    Models can show their contents when hovering over them.
    Parts are ordered by label, so they are the Models.

    Background: Application landing page
        Given the application Printer3DManager

    # @D3D07 @D3D07.01
    # Scenario: [D3D07.01]-Validate the structure of the Inventory Part List Page version 2.
    #     Given there is a click on Feature "/INVENTARIO"
    #     Then the page "InventoryPage" is activated
    #     And the loading panels shows "Clasificando Piezas..."
    #     When the loading panel completes
    #     Given the target is the panel of type "catalog"
    #     Then the target panel has a title "/CATALOGO PIEZAS Y MODELOS"
    #     Then the target panel has 2 "model"
    #     Then the target panel has 6 "part-container"

    # @D3D07 @D3D07.02
    # Scenario: [D3D07.02]-Validate the contents of a Model. If the Model is hovered then the Model expands to show the contents.
    #     Given there is a click on Feature "/INVENTARIO"
    #     Then the page "InventoryPage" is activated
    #     Given the target is the panel of type "catalog"
    #     Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
    #     Then the target item has a field named "label" with label "ETIQUETA" and value "PLATAFORMA SLOT 1/32 - Verde"
    #     And the target item has a field named "partCount" with label "NUMERO PIEZAS" and value "6"
    #     And the target item has a field named "price" with label "PRECIO" and value "15 €"

    #     When the mouse enter the target item
    #     Then the target item has a list named "part-composition" with 3 "part-stack"
    #     Given the target the "part-stack" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
    #     Then the target item has a column named "REQUERIDAS" with value "x 1"
    #     And the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Guarda Tornillos"
    #     And the target item has a column named "MATERIAL" with value "PLA"
    #     And the target item has a column named "COLOR" with value "BLANCO"

    @D3D07 @D3D07.03
    Scenario: [D3D07.03]-Validate the contents of a Part Container. If the Part container is clicked then it expands and shows the Parts with same label.
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated
        Given the target is the panel of type "catalog"
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        Then field named "label" with label "ETIQUETA" has contents "Boquilla Ganesha - Figura"
        And field named "description" with label "DESCRIPCION" has contents "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables."
        And field named "buildTime" with label "TIEMPO" has contents "90 min."
        Given the target item is expandable
        When the target item is expanded
        Given the target is the panel of type "catalog"
        Then the target has 6 "part"

    # @D3D07 @D3D07.04
    # Scenario: [D3D07.04]-Validate the contents of a Part.
    #     Given there is a click on Feature "/INVENTARIO"
    #     Then the page "InventoryPage" is activated
    #     Given the target is the panel of type "catalog"
    #     Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
    #     When the target item is expanded

    #     Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
    #     Then the target item has a field named "material" with label "MATERIAL" and value "PLA"
    #     And the target item has a field named "color" with label "COLOR" and value "ROSA"
    #     And the target item has a field named "stock" with label "STOCK" and value "5"
    #     And the target item has a field named "stockAvailable" with label "DISPONIBLE" and value "0"
    #     And the target item has a field named "cost" with label "COSTE" and value "1 €"
    #     And the target item has a field named "price" with label "PRECIO" and value "6 €"
    #     And the target item has a field named "active" with label "ACTIVA" and value "ACTIVA"

    # @D3D07 @D3D07.06
    # Scenario: [D3D07.06]-Active Parts show a green corner while inactive show it orange.
    #     Given there is a click on Feature "/INVENTARIO"
    #     Then the page "InventoryPage" is activated
    #     Given the target is the panel of type "catalog"
    #     Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
    #     Given the target item is expandable
    #     When the target item is expanded
    #     Then the target panel has 6 "part"

    #     And active "part" shows a green corner
    #     And inactive "part" shows an orange corner

    @D3D07 @D3D07.07
    Scenario: [D3D07.07]-Active and inactive parts have a editor activation button at the left.
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated
        Given the target is the panel of type "catalog"
        Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        When the target item is expanded
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then target has an actionable image named "edit-button"
# PENDING RECONSTRUCTION OF EDITABLES
# @D3D07 @D3D07.08
# Scenario: [D3D07.08]-When the Edit Attributes button is clicked the Part display changes and some fields are now editable.
#     Given there is a click on Feature "/INVENTARIO"
#     Then the page "InventoryPage" is activated
#     Given the target is the panel of type "catalog"
#     Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
#     When the target item is expanded
#     Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
#     Then the target item has a named "edit-button" button

#     Given the target item named button "edit-button" is clicked
#     And the target item field named "stock" is editable

#     # When the target Part Edit button is clicked
#     Then the field "STOCK" is editable
#     And the field "DISPONIBLE" is editable
#     And the field "COSTE" is editable
#     And the field "PRECIO" is editable
#     And the field "ACTIVA" is editable

# @D3D07 @D3D07.09
# Scenario: [D3D07.09]-When the part is put in editable mode the editable field contents are the same of the original part.
#     Given there is a click on Feature "/INVENTARIO"
#     Then the page "InventoryPage" is activated
#     Given the target is the panel of type "catalog"
#     Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
#     When the target item is expanded
#     Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
#     Then the field "stock" stores the current value into "STOCK-STORE"
#     Then the field "stockAvailable" stores the current value into "DISPONIBLE-STORE"
#     Then the field "cost" stores the current value into "COSTE-STORE"
#     Then the field "price" stores the current value into "PRECIO-STORE"
#     Then the field "active" stores the current value into "ACTIVA-STORE"
#     When the first v1-part is selected as the target
#     Then the target Part shows an Edit button at the right
#     When the target Part Edit button is clicked
#     Then the field "stock" is editable and the content equals the stored value "STOCK-STORE"
#     And the field "stockAvailable" is editable and the content equals the stored value "DISPONIBLE-STORE"
#     And the field "cost" is editable and the content equals the stored value "COSTE-STORE"
#     And the field "price" is editable and the content equals the stored value "PRECIO-STORE"

# @D3D07 @D3D07.10
# Scenario: [D3D07.10]-When the part is put in editable mode there is a check mark button to save the Part changes to the repository.
#     Given there is a click on Feature "/INVENTARIO"
#     When the V2InventoryPartListPage is activated
#     When the right arrow is clicked
#     When the first v1-part is selected as the target
#     Then the target Part shows an Edit button at the right
#     When the target Part Edit button is clicked
#     Then the target Part shows a Save button at the right

# @D3D07 @D3D07.11
# Scenario: [D3D07.11]-If this check mark button is clicked then the new values are persisted at the repository, a notification is shown and the edit state is exited.
#     Given there is a click on Feature "/INVENTARIO"
#     When the V2InventoryPartListPage is activated
#     When the right arrow is clicked
#     When the first v1-part is selected as the target
#     When the target Part Edit button is clicked
#     When the target Part Save button is clicked
#     Then there is a Notification panel
#     Then the new part contents are persisted to the backend
#     And the edit state is exited

# @D3D07 @D3D07.12
# Scenario: [D3D07.12]-The the Part values edited and persisted match the current Part values.
#     Given there is a click on Feature "/INVENTARIO"
#     When the V2InventoryPartListPage is activated
#     When the right arrow is clicked
#     When the first v1-part is selected as the target
#     When the target Part Edit button is clicked
#     Then the target Part field "stock" is changed to "8"
#     Then the target Part field "stockAvailable" is changed to "8"
#     Then the target Part field "cost" is changed to "8"
#     Then the target Part field "stock" stores the current value into "STOCK-STORE"
#     Then the target Part field "stockAvailable" stores the current value into "DISPONIBLE-STORE"
#     Then the target Part field "cost" stores the current value into "COSTE-STORE"
#     Then the target Part field "price" stores the current value into "PRECIO-STORE"
#     Then the target Part field "active" stores the current value into "ACTIVA-STORE"
#     When the target Part Save button is clicked
#     Then the target Part field "stock" equals the stored value "STOCK-STORE"
#     Then the target Part field "stockAvailable" equals the stored value "DISPONIBLE-STORE"
#     Then the target Part field "cost" equals the stored value "COSTE-STORE"
#     Then the target Part field "price" equals the stored value "PRECIO-STORE"
