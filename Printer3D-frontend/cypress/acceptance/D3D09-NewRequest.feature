@D3D09
Feature: [D3D09]-[STORY] Add a new Feature to create Requests. The request is filled by moving parts from the left Part Catalog to the right Request.

    Add a new Feature to create Requests.
    Requests have some test identification fiels and a Part drop area where to store the list of Parts.
    The request is filled by moving parts from the left Part Catalog to the right Request.
    Requests can have states to mean if a request is completed and served or if the requests is open waiting for missed Parts.
    Requests are prioritized. The intial algorithm should be the first come the first served.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D09 @D3D09.01
    Scenario: [D3D09.01]-Check that the new Features point to the right page components.
        Given one instance of Dock
        When the Feature with label "/PEDIDOS" is clicked the destination is the Page "OpenRequestsPage"
        When the Feature with label "/NUEVO PEDIDO" is clicked the destination is the Page "NewRequestPage"

    @D3D09 @D3D09.03
    Scenario: [D3D09.03]-The New Request page has two panels. The left Panel has a simple list of Parts that have at least one item. The right panel is the New Request.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        And the page "NewRequestPage" has 2 panels
        And the target page has one panel of type "available-parts" with variant "-REQUEST-PART-LIST-"
        And the target page has one panel of type "new-request"

    @D3D09 @D3D09.04
    Scenario: [D3D09.04]-The left panel has a simplified, one level list of Parts. The listing contains Parts with any number of copies on stock.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-parts"
        And the target panel has one or more "part"

    @D3D09 @D3D09.05
    Scenario: [D3D09.05]-The Parts visible on the New Request page have a defined list of fields.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-parts"
        Given the target item the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        And the target item has a field labeled "ETIQUETA" with value "Boquilla Ganesha - Embocadura"
        And the target item has a field labeled "MATERIAL" with value "TPU"
        And the target item has a field labeled "COLOR" with value "AZUL"
        And the target item has a field labeled "DISPONIBLE" with value "10"

    # @D3D09 @D3D09.06
    # Scenario: [D3D09.06]-The right panel is the Request definition panel. It should have the New Request fields and a place to drop Parts.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the page "NewRequestPage" is activated
    #     Given the target panel is the panel of type "v1-new-request-panel"
    #     Then the target panel has a title "/NUEVO PEDIDO/DEFINICION"
    #     Then the target panel has a field labeled "FECHA PEDIDO" named "requestDate" and not empty
    #     Then the target panel has a field labeled "ETIQUETA" named "label" and empty
    #     Then the target panel has a panel labeled "PIEZAS PEDIDO" named "requestParts" and with "0" elements

    # @D3D09 @D3D09.07
    # Scenario: [D3D09.07]-The Parts at the left panel can be dragged and deployed on the box at the right Panel.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the page "NewRequestPage" is activated
    #     Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
    #     Given the target Part is one labeled "Boquilla Ganesha - Figura"
    #     Then the target part is draggable with the contraint "PART"
    #     Given the target panel is the panel of type "v1-new-request-panel"
    #     Then the target panel has a place for drop with the constraint "JOB"

    # @D3D09 @D3D09.08
    # Scenario: [D3D09.08]-If we drag a Part to the right panel box the number of Parts associated to the Request increases.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the page "NewRequestPage" is activated
    #     Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
    #     Given the target Part is one labeled "Boquilla Ganesha - Figura"
    #     Given the target panel is the panel of type "v1-new-request-panel"
    #     Then the target panel has a panel labeled "PIEZAS PEDIDO" named "requestParts" and with "0" elements
    #     When the target Part is dragged to the drop panel "dropParts"
    #     Then the target panel has a panel labeled "PIEZAS PEDIDO" named "requestParts" and with "1" elements

    # @D3D09 @D3D09.09
    # Scenario: [D3D09.09]-The New request shows two buttons.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the page "NewRequestPage" is activated
    #     Given the target panel is the panel of type "v1-new-request-panel"
    #     Then the target panel has "2" buttons
    #     And the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
    #     And the target panel button with name "CANCEL" has a label "Cancelar" and is "enabled"

    # @D3D09 @D3D09.10
    # Scenario: [D3D09.10]-The New request shows two buttons. The save button is only active when all the fields are filled and there is at least one part on the list.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the page "NewRequestPage" is activated
    #     Given the target panel is the panel of type "v1-new-request-panel"
    #     Then the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
    #     Given the form "v1-new-request-panel" be the target form
    #     When "Pedido de Pruebas" is set on the target form field "label"
    #     Then the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
    #     Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
    #     Given the target Part is one labeled "Boquilla Ganesha - Figura"
    #     Given the target panel is the panel of type "v1-new-request-panel"
    #     When the target Part is dragged to the drop panel "dropParts"
    #     Then the target panel button with name "SAVE" has a label "Guardar" and is "enabled"

    # @D3D09 @D3D09.11
    # Scenario: [D3D09.11]-If the SAVE button is active and it is clicked then the Request is persisted on the backend, a notification shown and the page clears.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the page "NewRequestPage" is activated
    #     Given the target panel is the panel of type "v1-new-request-panel"
    #     Given the form "v1-new-request-panel" be the target form
    #     When "Pedido de Pruebas" is set on the target form field "label"
    #     Then the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
    #     Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
    #     Given the target Part is one labeled "Boquilla Ganesha - Figura"
    #     Given the target panel is the panel of type "v1-new-request-panel"
    #     When the target Part is dragged to the drop panel "dropParts"
    #     Then the target panel button with name "SAVE" has a label "Guardar" and is "enabled"
    #     When the target panel button with name "SAVE" is clicked
    #     Then the Request is persisted at the backend
    #     And there is a Notification panel
    #     And the active page is set to Dasboard
    #     And there are no Features active

    # @D3D09 @D3D09.12
    # Scenario: [D3D09.12]-If the CANCEL button is clicked then the page clears.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the page "NewRequestPage" is activated
    #     Given the target panel is the panel of type "v1-new-request-panel"
    #     When the target panel button with name "CANCEL" is clicked
    #     And the active page is set to Dasboard
    #     And there are no Features active

    @D3D09 @D3D09.13
    Scenario: [D3D09.13]-If the user moves a Part from the Part list to the drop area then a new Part is added to the Request.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-parts"
        And the drag source the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropParts"
        Then the target panel has 1 "part4-request"
        Given the target item the "part4-request" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Then the target item has a field named "REQUERIDAS" with value "1"
        Then the target item has a field named "ETIQUETA" with value "Boquilla Ganesha - Embocadura"
        Then the target item has a field named "MATERIAL" with value "TPU"
        Then the target item has a field named "COLOR" with value "AZUL"

    @D3D09 @D3D09.14
    Scenario: [D3D09.14]-If the user adds more than one instances of a Part model then if the Part is already on the list the required counter increments in 1.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-parts"
        And the drag source the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropParts"
        Then the target panel has 1 "part4-request"
        Given the target item the "part4-request" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Then the target item has a field named "REQUERIDAS" with value "1"
        When the drag source is dragged to the drop destination "dropParts"
        Then the target panel has 1 "part4-request"
        Given the target item the "part4-request" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Then the target item has a field named "REQUERIDAS" with value "2"

        Given the target panel is the panel of type "available-parts"
        Given the drag source the "part" with id "754e6cc5-1a8a-435b-8c17-956b2a8391a7"
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropParts"
        Then the target panel has 2 "part4-request"

    @D3D09 @D3D09.15
    Scenario: [D3D09.15]-If the user clicks on the Remove button of a Part assigned to a Request then the number of that type of Parts reduces in 1. The the count reaches 0 then the part is removed from the Report.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-parts"
        And the drag source the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropParts"
        Then the target panel has 1 "part4-request"
        Given the target item the "part4-request" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Then the target item has a field named "REQUERIDAS" with value "1"
        When the drag source is dragged to the drop destination "dropParts"
        Then the target panel has 1 "part4-request"
        Given the target item the "part4-request" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Then the target item has a field named "REQUERIDAS" with value "2"

        Given the target panel is the panel of type "available-parts"
        Given the drag source the "part" with id "754e6cc5-1a8a-435b-8c17-956b2a8391a7"
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropParts"
        Then the target panel has 2 "part4-request"

        Given the target item the "part4-request" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Then the target item has a field named "REQUERIDAS" with value "2"
        When the target item Remove button is clicked
        Then the target item has a field named "REQUERIDAS" with value "1"
        Then the target panel has 2 "part4-request"
        When the target item Remove button is clicked
        Then the target panel has 1 "part4-request"
        Given the target item the "part4-request" with id "754e6cc5-1a8a-435b-8c17-956b2a8391a7"
        When the target item Remove button is clicked
        Then the target panel has no "part4-request"
