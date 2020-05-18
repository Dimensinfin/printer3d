@D3D03
Feature: [D3D03]-Validate the Inventory features and page contents.

    If the user interact with Inventory features that activate the routes the destination are new pages that should be validated against the application requirements. If the Inventory feature set is selected then we should list the full Inventory Parts on the respository.

    @D3D03 @D3D03.01
    Scenario: [D3D03.01]-Validate the contents and structure of the Inventory Part List Page.
        Given the InventoryPartListPage is activated
        Given one instance of GridAngular
        Given one or more instances of Row
        Then there is a "grid-row" at index "1" with the next fields
            | label            | description                                                                                                                                    | buildTime | affinity | stockLevel | colours | active |
            | Covid-19 Key - A | Llavero para evitar tocar manillas y pulsadores durante la campaña de Covi-19. Modelo A que es el más simple en un solo color y sin refuerzos. | 60        | OFF      | 2          | ALL     | true   |
