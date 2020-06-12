@D3D07
Feature: [D3D07]-Validate the Inventory version 2 features and page contents.

    The inventory is an information section that groups elements that are in stock. Availability means that they can be used
    to build things. Stock control may be used to decide if more things should be obtained.
    Inventory actions should allow to create, edit and see the differents elements that can be stocked.
    The Part list should be changed to a master-detail concept. When  the list is displayed only is displayed the main part
    information. If the Part is clicked then the Part expands and the detailed stock data is shown.

    Background: Start the application and move to the initial page
        Given the application Printer3DManager

    @D3D07 @D3D07.01
    Scenario: [D3D07.01]-Validate the structure of the Inventory Part List Page version 2.
        Given there is a click on Feature "/INVENTARIO"
        Then the V2InventoryPartListPage is activated
        And one instance of ViewerPanel
        And one or more instances of NodeContainer

    @D3D07 @D3D07.02
    Scenario: [D3D07.02]-Validate the contents of a Part render.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        Then the first NodeContainer contains a Part Container Render
        And on the v1-part-container component there is a field named "ETIQUETA" with class "partcontainer-label"
        And on the v1-part-container component there is a field named "DESCRIPCION" with class "partcontainer-description"
        And on the v1-part-container component there is a field named "TIEMPO" with class "partcontainer-buildTime"

    @D3D07 @D3D07.03
    Scenario: [D3D07.03]-When the user activates the Inventory Part List Page version 2 while the server download the data it shows a downloading panel.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        And one instance of ViewerPanel
        Then there is a loading panel displaying "Descargando Lista de Piezas..."

    @D3D07 @D3D07.04
    Scenario: [D3D07.04]-The Part render is an expandable element so the node container shows a right arrow.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        Then on the v1-part-container component there is a right arrow

    @D3D07 @D3D07.05
    Scenario: [D3D07.05]-If the arrow is clicked then the Part Container expands and shows the parts that match this same label.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        Then on the v1-part-container component there is a right arrow
        When the right arrow is clicked
        Then the container expands and there are one or more v1-part nodes
        And on the v1-part component there is a field named "MATERIAL" with class "part-material"
        And on the v1-part component there is a field named "COLOR" with class "part-color"
        And on the v1-part component there is a field named "STOCK" with class "part-stock"
        And on the v1-part component there is a field named "DISPONIBLE" with class "part-stockAvailable"
        And on the v1-part component there is a field named "COSTE" with class "part-cost"
        And on the v1-part component there is a field named "PRECIO" with class "part-price"
        And on the v1-part component there is a field named "ACTIVA" with class "part-active"

    # @D3D07 @D3D07.06
    # Scenario: [D3D07.06]-Active Parts show a green corner while inactive show it orange.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     Then on the v1-part-container component there is a right arrow
    #     And when the target Part Container is the second
    #     When the right arrow of the target Part Continer is clicked
    #     Then the container expands and there are one or more v1-part nodes
    #     And active Parts show a green corner
    #     And inactive Part show an orange corner

    @D3D07 @D3D07.07
    Scenario: [D3D07.07]-Active and inactive parts have a editor activation button at the left.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        Then on the v1-part-container component there is a right arrow
        When the right arrow is clicked
        Then the container expands and there are one or more v1-part nodes
        When the first v1-part is selected as the target
        Then the target Part shows an Edit button at the right

    @D3D07 @D3D07.08
    Scenario: [D3D07.08]-When the Edit Attributes button is clicked the Part display changes and some fields are now editable.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        Then on the v1-part-container component there is a right arrow
        When the right arrow is clicked
        Then the container expands and there are one or more v1-part nodes
        When the first v1-part is selected as the target
        Then the target Part shows an Edit button at the right
        When the target Part Edit button is clicked
        Then the field "STOCK" is editable
        And the field "DISPONIBLE" is editable
        And the field "COSTE" is editable
        And the field "PRECIO" is editable
        And the field "ACTIVA" is editable

    @D3D07 @D3D07.09
    Scenario: [D3D07.09]-When the part is put in editable mode the editable field contents are the same of the original part.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        Then on the v1-part-container component there is a right arrow
        When the right arrow is clicked
        Then the container expands and there are one or more v1-part nodes
        When the first v1-part is selected as the target
        Then the field "stock" stores the current value into "STOCK-STORE"
        Then the field "stockAvailable" stores the current value into "DISPONIBLE-STORE"
        Then the field "cost" stores the current value into "COSTE-STORE"
        Then the field "price" stores the current value into "PRECIO-STORE"
        Then the field "active" stores the current value into "ACTIVA-STORE"
        When the first v1-part is selected as the target
        Then the target Part shows an Edit button at the right
        When the target Part Edit button is clicked
        Then the field "stock" is editable and the content equals the stored value "STOCK-STORE"
        And the field "stockAvailable" is editable and the content equals the stored value "DISPONIBLE-STORE"
        And the field "cost" is editable and the content equals the stored value "COSTE-STORE"
        And the field "price" is editable and the content equals the stored value "PRECIO-STORE"
    # And the field "active" is editable and the content equals the stored value "ACTIVA-STORE"

    @D3D07 @D3D07.10
    Scenario: [D3D07.10]-When the part is put in editable mode there is a check mark button to save the Part changes to the repository.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        When the right arrow is clicked
        When the first v1-part is selected as the target
        Then the target Part shows an Edit button at the right
        When the target Part Edit button is clicked
        Then the target Part shows a Save button at the right

    @D3D07 @D3D07.11
    Scenario: [D3D07.11]-If this check mark button is clicked then the new values are persisted at the repository, a notification is shown and the edit state is exited.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        When the right arrow is clicked
        When the first v1-part is selected as the target
        When the target Part Edit button is clicked
        When the target Part Save button is clicked
        Then there is a Notification panel
        Then the new part contents are persisted to the backend
        And the edit state is exited

    @D3D07 @D3D07.12
    Scenario: [D3D07.12]-The the Part values edited and persisted match the current Part values.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        When the right arrow is clicked
        When the first v1-part is selected as the target
        When the target Part Edit button is clicked
        Then the target Part field "stock" is changed to "8"
        Then the target Part field "stockAvailable" is changed to "8"
        Then the target Part field "cost" is changed to "8"
        Then the target Part field "stock" stores the current value into "STOCK-STORE"
        Then the target Part field "stockAvailable" stores the current value into "DISPONIBLE-STORE"
        Then the target Part field "cost" stores the current value into "COSTE-STORE"
        Then the target Part field "price" stores the current value into "PRECIO-STORE"
        Then the target Part field "active" stores the current value into "ACTIVA-STORE"
        When the target Part Save button is clicked
        Then the target Part field "stock" equals the stored value "STOCK-STORE"
        Then the target Part field "stockAvailable" equals the stored value "DISPONIBLE-STORE"
        Then the target Part field "cost" equals the stored value "COSTE-STORE"
        Then the target Part field "price" equals the stored value "PRECIO-STORE"
