@D3D02
Feature: [D3D02]-Test the dock contents and configuration.

    The Dock contains the links to the feature result pages when it is related to a route interaction.
    If the interaction is a Dialog then the click should open a new dialog and check the user interaction.
    Other actions can be configured for each action name and can be any of the previous actions.

    Background: Application landing page
        Given the application Printer3DManager

    @D3D02.01
    Scenario: [D3D02.01]-Verify the list of features that should be present.
        Given one instance of Dock
        Then there are 7 Features enabled
        Then there is a Feature with label "/ROLLOS"
        Then there is a Feature with label "/INVENTARIO"
        Then there is a Feature with label "/PEDIDOS"
        Then there is a Feature with label "/TRABAJOS PND."
        Then there is a Feature with label "/NUEVO ROLLO"
        Then there is a Feature with label "/NUEVA PIEZA"
        Then there is a Feature with label "/NUEVO MODELO"
        Then there is a Feature with label "/NUEVO PEDIDO"

    @D3D02.02
    Scenario: [D3D02.02]-Check that Features that open Dialogs have a corner in blue.
        Given one instance of Dock
        Then the Feature with label "/NUEVO ROLLO" opens a Dialog
        Then the Feature with label "/NUEVA PIEZA" opens a Dialog

    @D3D02.03
    Scenario: [D3D02.03]-Different types of features have different visuals. The ones that open a Dialog a blue corner. The ones that open editors a violet corner.
        Given one instance of Dock
        Then the Feature with label "/NUEVO MODELO" opens a DropPage
        Then the Feature with label "/NUEVO PEDIDO" opens a DropPage

    @D3D02.04
    Scenario: [D3D02.04]-When a new page is selected the Dock visual configuration for the active Feature changes.
        Given one instance of Dock
        When there are no Features active
        When there is a click on Feature "/INVENTARIO"
        And the target Feature "/INVENTARIO" changes to state "active"
        When there is a click on Feature "/NUEVA PIEZA"
        And the target Feature "/NUEVA PIEZA" changes to state "active"

    @D3D02.05
    Scenario: [D3D02.05] When the dialog closes the Feature that opens it should go back to inactive.
        Given one instance of Dock
        # When there is a click on Feature "/NUEVA PIEZA"
        And the Feature with label "/NUEVA PIEZA" is clicked the destination is the Dialog "new-part-dialog"
        And the target Feature "/NUEVA PIEZA" changes to state "active"
        When there is a click on the "cancel-button" button of target dialog
        And the target Feature "/NUEVA PIEZA" changes to state "inactive"

    @D3D02.06
    Scenario: [D3D02.06]-Validate the elements that open a Dialog.
        Given one instance of Dock
        When the Feature with label "/NUEVO ROLLO" is clicked the destination is the Dialog "new-coil-dialog"
        And there is a click on the "cancel-button" button of target dialog
        When the Feature with label "/NUEVA PIEZA" is clicked the destination is the Dialog "new-part-dialog"
        When there is a click on the "cancel-button" button of target dialog

    @D3D02.07
    Scenario: [D3D02.07]-Check that Features that open Pages do not have any top corner colored differently
        Given one instance of Dock
        Then the Feature with label "/ROLLOS" opens a Page
        Then the Feature with label "/INVENTARIO" opens a Page
        Then the Feature with label "/PEDIDOS" opens a Page
        Then the Feature with label "/TRABAJOS PND." opens a Page
