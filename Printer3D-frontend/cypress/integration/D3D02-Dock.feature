@D3D02
Feature: [D3D02]-Test the dock contents and configuration.

    The Dock contains the links to the feature result pages when it is related to a route interaction.
    If the interaction is a Dialog then the click should open a new dialog and check the user interaction.
    Other actions can be configured for each action name and can be any of the previous actions.

    Background: Start the application and move to the initial page
        Given the application Printer3DManager

    @D3D02 @D3D02.01
    Scenario: [D3D02.01]-Verify the list of features that should be present.
        Given one instance of Dock
        Then thre are 5 Features active
        Then there is a Feature with label "/ROLLOS"
        Then there is a Feature with label "/INVENTARIO"
        Then there is a Feature with label "/TRABAJOS PND."
        Then there is a Feature with label "/NUEVO ROLLO"
        Then there is a Feature with label "/NUEVA PIEZA"

    @D3D02 @D3D02.02
    Scenario: [D3D02.02]-Check that Features that open Dialogs have a corner in blue.
        Given one instance of Dock
        Then the Feature with label "/NUEVO ROLLO" opens a Dialog
        Then the Feature with label "/NUEVA PIEZA" opens a Dialog
        Then the Feature with label "/ROLLOS" opens a Page
        Then the Feature with label "/INVENTARIO" opens a Page
        Then the Feature with label "/TRABAJOS PND." opens a Page

    @D3D02 @D3D02.03
    Scenario: [D3D02.03]-Validate the elements open when there is a click on each of the Features.
        Given one instance of Dock
        When the Feature with label "/ROLLOS" is clicked the destination is "/inventory/coillist"
        When the Feature with label "/INVENTARIO" is clicked the destination is "/inventory/partlist"

    @D3D02 @D3D02.04
    Scenario: [D3D02.04]-When a new page is selected the Dock visual configuration for the active Feature changes.
        Given one instance of Dock
        When there are no Features active
        When there is a click on Feature "/NUEVA PIEZA"
        And the Feature "/NUEVA PIEZA" changes to state "active"
        When there is a click on Feature "/INVENTARIO"
        And the Feature "/INVENTARIO" changes to state "active"

    @D3D02 @D3D02.05
    Scenario: [D3D02.05] When the dialog closes the Feature that opens it should go back to inactive.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        And the Feature "/NUEVA PIEZA" changes to state "active"
        When there is a click on the "CLOSE" button
        And the Feature "/NUEVA PIEZA" changes to state "inactive"
