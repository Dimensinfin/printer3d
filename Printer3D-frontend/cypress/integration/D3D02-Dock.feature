@D3D02
Feature: [D3D02]-Test the activities with the dock. First action is to move to target routes.

    The Dock contains the links to the feature result pages when it is related to a route interaction.
    If the interaction is a Dialog then the click should open a new dialog and check the user interaction.

    Background: Dock Default Configuration setup
        Given the Default Dock Configuration

    @D3D02 @D3D02.01
    Scenario: [D3D02.01]-If there is a click on a Feature that is a PAGE-ROUTE then the route changes to the configured destination.
        Given the DashboardPage is activated
        And one instance of Dock
        When there is a click on v1-feature-render with name "/INVENTORY"
        Then the target page is InventoryPartListPage

    @D3D02 @D3D02.02
    Scenario: [D3D02.02]-When a new page is selected the Dock visual configuration for the active Feature changes.
        Given the DashboardPage is activated
        And one instance of Dock
        Then there is 0 v1-feature-render active
        Then there is a "v1-feature-render" at index "0" with the next fields
            | label      | active |
            | /Inventory | false  |
        When there is a click on v1-feature-render with name "/INVENTORY"
        Then the target page is InventoryPartListPage
        And there is 1 v1-feature-render active
        Then there is a "v1-feature-render" at index "0" with the next fields
            | label      | active |
            | /Inventory | true   |

    @D3D02 @D3D02.03
    Scenario: [D3D02.03]-When there is a Feature active and the application is restarted the landing page is the one for the Feature selected.
        Given a Dock Configuration with Feature "/INVENTORY" selected
        When the application is started
        Then the target page is InventoryPartListPage
        And there is 1 v1-feature-render active
        Then there is a "v1-feature-render" at index "0" with the next fields
            | label      | active |
            | /Inventory | true   |

    # @D3D02 @D3D02.04
    # Scenario: [D3D02.04]-If there is a click on a Feature that is a PAGE-ROUTE then Dock presentation changes to show as active the Feature selected.
    # @D3D02 @D3D02.05
    # Scenario: [D3D02.05]-If the application route is changed manually the Feature list display should be adjusted to the current Route
