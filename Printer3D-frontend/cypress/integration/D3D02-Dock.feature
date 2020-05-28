@D3D02
Feature: [D3D02]-Test the activities with the dock.

    The Dock contains the links to the feature result pages when it is related to a route interaction.
    If the interaction is a Dialog then the click should open a new dialog and check the user interaction.
    Other actions can be configured for each action name and can be any of the previous actions.

    # Background: Dock Default Configuration setup
    #     Given the application Printer3DManager

    @D3D02 @D3D02.01
    Scenario: [D3D02.01]-If there is a click on a Feature that is a PAGE-ROUTE then the route changes to the configured destination.
        Given the DashboardPage is activated
        And one instance of Dock
        When there is a click on Feature "/INVENTARIO"
        Then the target page is InventoryPartListPage

    @D3D02 @D3D02.02
    Scenario: [D3D02.02]-When a new page is selected the Dock visual configuration for the active Feature changes.
        Given the DashboardPage is activated
        And one instance of Dock
        Then there are no Features active
        When there is a click on Feature "/NUEVA PIEZA"
        And the Feature "/NUEVA PIEZA" changes to state "active"

    @D3D02 @D3D02.04
    Scenario: [D3D02.04]-Validate the list and presentation of the created Features.
        Given the DashboardPage is activated
        And one instance of Dock
        Then there are 4 Features

    @D3D02 @D3D02.05
    Scenario: [D3D02.05] When the dialog closes the Feature that open it should go back to inactive.
        Given the DashboardPage is activated
        And one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        And the Feature "/NUEVA PIEZA" changes to state "active"
        When there is a click on the "CLOSE" button
        And the Feature "/NUEVA PIEZA" changes to state "inactive"
