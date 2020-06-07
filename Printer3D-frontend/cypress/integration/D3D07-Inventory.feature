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
        Then the first NodeContainer contains a Part Render
        And on the v1-part-render component there is a field named "label"

    @D3D07 @D3D07.03
    Scenario: [D3D07.03]-When the user activates the Inventory Part List Page version 2 while the server download the data it shows a downloading panel.
        Given there is a click on Feature "/INVENTARIO"
        When the V2InventoryPartListPage is activated
        And one instance of ViewerPanel
Then there is a loading panel displaying ""
