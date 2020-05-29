@D3D03
Feature: [D3D03]-Validate the Inventory features and page contents.

    The inventory is an information section that groups elements that sre available. Availability means that they cane be used
    to build things. Stock control may be used to decide if more things should be obtained.
    Inventory actions shluld allow to create, edit and see the differents elements that can be stocked.

    Background: Start the application and move to the initial page
        Given the application Printer3DManager

    # @D3D03 @D3D03.01
    # Scenario: [D3D03.01]-Validate the structure of the Inventory Part List Page.
    #     Given there is a click on Feature "/INVENTARIO"
    #     Then the InventoryPartListPage is activated
    #     And one instance of GridAngular
    #     And one or more instances of Row

    @D3D03 @D3D03.02
    Scenario: [D3D03.02]-Check that the Inventory Part List informs of the required fields.
        Given there is a click on Feature "/INVENTARIO"
        Then the InventoryPartListPage is activated
        And one instance of GridAngular
        Then there is a column with "etiqueta" data
        Then there is a column with "descripcion" data
        Then there is a column with "material" data
        Then there is a column with "color" data
        Then there is a column with "coste" data
        Then there is a column with "precio" data
        # Then there is a column with "stockRequerido" data
        # Then there is a column with "stockDisponible" data
        # Then there is a column with "active" data

    # @D3D03 @D3D03.03
    # Scenario: [D3D03.03]-Validate the structure of the Inventory Coil List Page.
    #     Given the InventoryCoilListPage is activated
    #     Given one instance of GridAngular
    #     Given one or more instances of Row

# @D3D03 @D3D03.04
# Scenario: [D3D03.02]-Check that the Inventory Part List informs of the required fields.
#     Given the InventoryPartListPage is activated
#     Given one instance of GridAngular
#     Then there is a column with Etiqueta data
#     Then there is a column with Descripcion data
