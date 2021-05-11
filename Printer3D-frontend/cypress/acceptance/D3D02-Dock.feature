@D3D02
Feature: [D3D02]-Test the dock contents and configuration.

    The Dock contains the links to the feature result pages when it is related to a route interaction.
    If the interaction is a Dialog then the click should open a new dialog and check the user interaction.
    Other actions can be configured for each action name and can be any of the previous actions.
    [STORY] Add a new Feature button to show the page with the data download buttons.
    [STORY] There are hierarchical features that when hovered generate a list of final Features.

    Background: Activating the application and getting the Dock.
        Given the application Printer3DManager
        Given one instance of Dock
        When the application completes loading

    @D3D02.01
    Scenario: [D3D02.01]-Verify the list of features that should be present.
        Then there are 9 Features enabled
        Then there is a Feature with label "/FILAMENTOS"
        Then there is a Feature with label "/INVENTARIO"
        Then there is a Feature with label "/PEDIDOS"
        Then there is a Feature with label "/TRABAJOS PND."
        Then there is a Feature with label "/NUEVO FILAMENTO"
        Then there is a Feature with label "/NUEVA PIEZA"
        Then there is a Feature with label "/NUEVO MODELO"
        Then there is a Feature with label "/NUEVO PEDIDO"
        Then there is a Feature with label "/EXTRACCIONES"

    @D3D02.02
    Scenario: [D3D02.02]-Check that Features that open Dialogs have a corner in blue.
        Then the Feature with label "/NUEVO FILAMENTO" opens a Dialog
        Then the Feature with label "/NUEVA PIEZA" opens a Dialog

    @D3D02.03
    Scenario: [D3D02.03]-Different types of features have different visuals. The ones that open a Dialog a blue corner. The ones that open editors a violet corner.
        Then the Feature with label "/NUEVO MODELO" opens a DropPage
        Then the Feature with label "/NUEVO PEDIDO" opens a DropPage

    @D3D02.04
    Scenario: [D3D02.04]-When a new page is selected the Dock visual configuration for the active Feature changes.
        When there are no Features active
        When there is a click on Feature "/INVENTARIO"
        And the target Feature "/INVENTARIO" changes to state "active"
        When there is a click on Feature "/NUEVA PIEZA"
        And the target Feature "/NUEVA PIEZA" changes to state "active"

    @D3D02.05
    Scenario: [D3D02.05] When the dialog closes the Feature that opens it should go back to inactive.
        And the Feature with label "/NUEVA PIEZA" is clicked the destination is the Dialog "New Part"
        And the target Feature "/NUEVA PIEZA" changes to state "active"
        When there is a click on the "cancel-button" button of target dialog
        And the target Feature "/NUEVA PIEZA" changes to state "inactive"

    @D3D02.06
    Scenario: [D3D02.06]-Validate the elements that open a Dialog.
        When the Feature with label "/NUEVO FILAMENTO" is clicked the destination is the Dialog "New Coil"
        And there is a click on the "cancel-button" button of target dialog
        When the Feature with label "/NUEVA PIEZA" is clicked the destination is the Dialog "New Part"
        When there is a click on the "cancel-button" button of target dialog

    @D3D02.07
    Scenario: [D3D02.07]-Check that Features that open Pages do not have any top corner colored differently
        Then the Feature with label "/FILAMENTOs" opens a Page
        Then the Feature with label "/INVENTARIO" opens a Page
        Then the Feature with label "/PEDIDOS" opens a Page
        Then the Feature with label "/TRABAJOS PND." opens a Page

    # CIRCLECI - Failing because -SELLABLE-ITEMS- timeout
    # @D3D02.08
    # Scenario: [D3D02.08]-Check that the New Request feature points to the right page components.
    #     # - Validate the page structure and the loading sign
    #     When the Feature with label "/NUEVO PEDIDO" is clicked the destination is the Page "NewRequestPage"
    #     And the loading panel shows "Clasificando Elementos..."
    #     When the loading panel completes
    #     And the page "NewRequestPage" has 2 panels
    #     Given the target is the panel of type "available-request-elements"
    #     Then  the target has the title "/STOCKS DISPONIBLES"
    #     And the target has variant "-SELLABLE-ITEMS-"
    #     Given the target is the panel of type "new-request"
    #     Then  the target has the title "/NUEVO PEDIDO/DEFINICION"

    @D3D02.09
    Scenario: [D3D02.09]-Check that the New Model feature points to the right page.
        # - Validate the page structure and the loading sign
        When the Feature with label "/NUEVO MODELO" is clicked the destination is the Page "NewModelPage"
        Then the Feature with label "/NUEVO MODELO" opens a DropPage
        And the page "NewModelPage" has 2 panels
        Given the target is the panel of type "available-parts"
        Then  the target has the title "/PIEZAS/DISPONIBLES"
        Given the target is the panel of type "new-model"
        Then  the target has the title "/NUEVO MODELO/DEFINICION"

    @D3D02.10
    Scenario: [D3D02.10]-Check that the Open Requests feature open the right page and shows the required components.
        # - Validate the page structure and the loading sign
        When the Feature with label "/PEDIDOS" is clicked the destination is the Page "Open Requests Page"
        Then the Feature with label "/PEDIDOS" opens a Page
        And the loading panel shows "Clasificando Pedidos..."
        When the loading panel completes
        Then the target Feature "/PEDIDOS" changes to state "active"
        And the page "Open Requests Page" has 2 panels
        Given the target is the panel of type "open-requests"
        Then  the target has the title "/PEDIDOS/ABIERTOS"
        Given the target is the panel of type "request-details"
        Then  the target has the title "/PEDIDOS/DETALLE"

    @D3D02.11
    Scenario: [D3D02.11]-Check the open of the New Part dialog.
        Then the Feature with label "/NUEVA PIEZA" opens a Dialog
        When the Feature with label "/NUEVA PIEZA" is clicked the destination is the Dialog "New Part"
        And the target Feature "/NUEVA PIEZA" changes to state "active"
        Then the dialog has the title "/INVENTARIO/NUEVA PIEZA"

    @D3D02.12
    Scenario: [D3D02.12]-Check that the Pending Jobs Feature opens the right page and shows the required titles.
        # - Validate the page structure and the loading sign
        When the Feature with label "/TRABAJOS PND." is clicked the destination is the Page "Production Jobs Page"
        Then the Feature with label "/TRABAJOS PND." opens a Page
        And the loading panel shows "Clasificando Trabajos..."
        When the loading panel completes
        Then the target Feature "/TRABAJOS PND." changes to state "active"
        And the page "Production Jobs Page" has 2 panels
        Given the target is the panel of type "jobs-list"
        Then  the target has the title "/TRABAJOS/PENDIENTES"
        Given the target is the panel of type "machines"
        Then  the target has the title "/MAQUINAS"

    @D3D02.13
    Scenario: [D3D02.13]-Check that the Inventory Feature opens the right page and shows the required titles.
        When the Feature with label "/INVENTARIO" is clicked the destination is the Page "Inventory Page"
        Then the Feature with label "/INVENTARIO" opens a Page
        And the loading panel shows "Clasificando Piezas..."
        When the loading panel completes
        Then the target Feature "/INVENTARIO" changes to state "active"
        And the page "Inventory Page" has 2 panels
        Given the target is the panel of type "catalog"
        Then the target has the title "/CATALOGO PIEZAS Y MODELOS"
        Then the target has 4 "project"
        Then the target has 8 "part-container"

    @D3D02.14
    Scenario: [D3D02.14]-Check that the Extractions Feature opens the right page and contains the right number of extractions.
        When the Feature with label "/EXTRACCIONES" is clicked the destination is the Page "Extractions Page"
        Then the Feature with label "/EXTRACCIONES" opens a Page
        When the page "Extractions Page" is activated
        Then  the target has the title "/EXTRACCIONES DE DATOS"
        Then the target has 1 "extraction"
