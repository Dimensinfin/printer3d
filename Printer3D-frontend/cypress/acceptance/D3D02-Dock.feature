@D3D02
Feature: [D3D02]-Test the dock contents and configuration.

    [STORY] The Dock contains the links to the feature result pages when it is related to a route interaction.
    [STORY] If the interaction is a Dialog then the click should open a new dialog and check the user interaction.
    [STORY] Other actions can be configured for each action name and can be any of the previous actions.
    [STORY] Add a new Feature button to show the page with the data download buttons.
    [STORY] There are hierarchical features that when hovered generate a list of final Features.
    [STORY] There are two pages for the Customer Requests. Each one accessible from a different Feature.
    [STORY] The creation functionalities should be stacked onto a new hierarchical functionality.

    Background: Activating the application and getting the Dock.
        Given the application Printer3DManager
        Given one instance of Dock
        When the application completes loading

    @D3D02.01
    Scenario: [D3D02.01]-Verify the list of features that should be present.
        Then there are 6 Features enabled
        # Then there are 0 Features disabled
        Then there is a Feature with label "/FILAMENTOS"
        Then there is a Feature with label "/INVENTARIO"
        Then there is a Feature with label "/PEDIDOS"
        Then there is a Feature with label "/TRABAJOS PND."
        Then there is a Feature with label "/NUEVOS DATOS"
        Then there is a Feature with label "/EXTRACCIONES"

    @D3D02.02.1
    Scenario: [D3D02.02.1]-Check that Features that open Dialogs have a corner in blue.
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVO FILAMENTO"
        When there is a click on Feature "/NUEVO FILAMENTO"
        Then the Feature with label "/NUEVO FILAMENTO" opens a Dialog
        Then there is a Feature with label "/NUEVO FILAMENTO"
        And the target Feature enabled state is "enabled"
        And the target Feature target type is "dialog"

    @D3D02.02.2
    Scenario: [D3D02.02.2]-Check that Features that open Dialogs have a corner in blue.
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVA PIEZA"
        When there is a click on Feature "/NUEVA PIEZA"
        Then the Feature with label "/NUEVA PIEZA" opens a Dialog
        Then there is a Feature with label "/NUEVA PIEZA"
        And the target Feature enabled state is "enabled"
        And the target Feature target type is "dialog"

    @D3D02.03.1
    Scenario: [D3D02.03.1]-Different types of features have different visuals. The ones that open a Dialog a blue corner. The ones that open editors a violet corner.
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVO MODELO"
        When there is a click on Feature "/NUEVO MODELO"
        Then the Feature with label "/NUEVO MODELO" opens a DropPage
        Then there is a Feature with label "/NUEVO MODELO"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "active"
        And the target Feature target type is "drop"

    @D3D02.03.2
    Scenario: [D3D02.03.2]-Different types of features have different visuals. The ones that open a Dialog a blue corner. The ones that open editors a violet corner.
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVO PEDIDO"
        When there is a click on Feature "/NUEVO PEDIDO"
        Then the Feature with label "/NUEVO PEDIDO" opens a DropPage
        Then there is a Feature with label "/NUEVO PEDIDO"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "active"
        And the target Feature target type is "drop"

    @D3D02.04
    Scenario: [D3D02.04]-When a new page is selected the Dock visual configuration for the active Feature changes.
        When there are 0 Features active
        When there is a click on Feature "/INVENTARIO"
        Then there are 1 Features active
        Then there is a Feature with label "/INVENTARIO"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "active"
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVO PEDIDO"
        When there is a click on Feature "/NUEVO PEDIDO"
        Then there are 2 Features active
        Then there is a Feature with label "/NUEVO PEDIDO"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "active"

    @D3D02.05
    Scenario: [D3D02.05] When the dialog closes the Feature that opens it should go back to inactive.
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVA PIEZA"
        And the Feature with label "/NUEVA PIEZA" is clicked the destination is the Dialog "New Part"
        Then there are 1 Features active
        Then there is a Feature with label "/NUEVA PIEZA"
        And the target Feature active state is "active"
        When there is a click on the "cancel-button" button of target dialog
        Then there are 0 Features active
        Given the Feature "/NUEVOS DATOS" is hovered
        Then there is a Feature with label "/NUEVA PIEZA"
        And the target Feature active state is "inactive"

    @D3D02.06
    Scenario: [D3D02.06]-Validate the elements that open a Dialog.
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVO FILAMENTO"
        When the Feature with label "/NUEVO FILAMENTO" is clicked the destination is the Dialog "New Coil"
        And there is a click on the "cancel-button" button of target dialog
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVA PIEZA"
        When the Feature with label "/NUEVA PIEZA" is clicked the destination is the Dialog "New Part"
        When there is a click on the "cancel-button" button of target dialog

    @D3D02.07
    Scenario: [D3D02.07]-Check that Features that open Pages do not have any top corner colored differently
        Then the Feature with label "/FILAMENTOs" opens a Page
        Then the Feature with label "/INVENTARIO" opens a Page
        Then the Feature with label "/PEDIDOS" opens a Page
        Then the Feature with label "/TRABAJOS PND." opens a Page

    # CIRCLECI - Failing because -SELLABLE-ITEMS- timeout
    @D3D02.08
    Scenario: [D3D02.08]-Check that the New Request feature points to the right page components.
        # - Validate the page structure and the loading sign
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVO PEDIDO"
        When the Feature with label "/NUEVO PEDIDO" is clicked the destination is the Page "NewRequestPage"
        And the loading panel shows "Clasificando Elementos..."
        When the loading panel completes
        And the page "NewRequestPage" has 2 panels
        Given the target is the panel of type "available-request-elements"
        Then  the target has the title "/STOCKS DISPONIBLES"
        And the target has variant "-SELLABLE-ITEMS-"
        Given the target is the panel of type "new-request"
        Then  the target has the title "/NUEVO PEDIDO/DEFINICION"

    @D3D02.09
    Scenario: [D3D02.09]-Check that the New Model feature points to the right page.
        # - Validate the page structure and the loading sign
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVO MODELO"
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
        Given the Feature "/PEDIDOS" is hovered
        Given there is a Feature with label "/PEDIDOS ABIERTOS"
        When the Feature with label "/PEDIDOS ABIERTOS" is clicked the destination is the Page "Open Requests Page"
        Then the Feature with label "/PEDIDOS ABIERTOS" opens a Page
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
        Given the Feature "/NUEVOS DATOS" is hovered
        Given there is a Feature with label "/NUEVA PIEZA"
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
        And the page "Production Jobs Page" has 2 sections
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

    @D3D02.15
    Scenario: [D3D02.15]-Check an enabled Feature and its graphical attributes.
        Then there is a Feature with label "/INVENTARIO"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "inactive"
        And the target Feature target type is "page"

    @D3D02.16
    Scenario: [D3D02.16]-If the user hovers over the Pedidos hierarchical Feature the list of features changes.
        When there are 6 Features enabled
        When the Feature "/PEDIDOS" is hovered
        When there are 8 Features enabled
        Then there is a Feature with label "/PEDIDOS ABIERTOS"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "inactive"
        And the target Feature target type is "page"
        Then there is a Feature with label "/PEDIDOS CERRADOS"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "inactive"
        And the target Feature target type is "page"

    @D3D02.17
    Scenario: [D3D02.17]-There are two pages for Requests. One for Open Requests.
        Given the Feature "/PEDIDOS" is hovered
        Given there is a Feature with label "/PEDIDOS ABIERTOS"
        When there is a click on Feature "/PEDIDOS ABIERTOS"
        Then the Feature with label "/PEDIDOS ABIERTOS" opens a Page
        And the page "Open Requests Page" has 2 sections
        Given the target is the panel of type "open-requests"
        Then  the target has the title "/PEDIDOS/ABIERTOS"
        Given the target is the panel of type "request-details"
        Then  the target has the title "/PEDIDOS/DETALLE"

    @D3D02.18
    Scenario: [D3D02.18]-There are two pages for Requests. Another for Closed Requests.
        Given the Feature "/PEDIDOS" is hovered
        Given there is a Feature with label "/PEDIDOS CERRADOS"
        When there is a click on Feature "/PEDIDOS CERRADOS"
        Then the Feature with label "/PEDIDOS ABIERTOS" opens a Page
        And the page "Closed Requests Page" has 3 sections
        Given the target is the panel of type "closed-requests"
        Then  the target has the title "/PEDIDOS/CERRADOS"
        Given the target is the panel of type "request-details"
        Then  the target has the title "/PEDIDOS/DETALLE"

    @D3D02.19
    Scenario: [D3D02.19]-If the user hovers over the Nuevos Datos hierarchical Feature the list of features changes.
        When there are 6 Features enabled
        When the Feature "/NUEVOS DATOS" is hovered
        When there are 10 Features enabled
        Then there is a Feature with label "/NUEVO FILAMENTO"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "inactive"
        And the target Feature target type is "dialog"
        Then there is a Feature with label "/NUEVA PIEZA"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "inactive"
        And the target Feature target type is "dialog"
        Then there is a Feature with label "/NUEVO MODELO"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "inactive"
        And the target Feature target type is "drop"
        Then there is a Feature with label "/NUEVO PEDIDO"
        And the target Feature enabled state is "enabled"
        And the target Feature active state is "inactive"
        And the target Feature target type is "drop"
