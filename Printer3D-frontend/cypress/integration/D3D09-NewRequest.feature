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
        When the Feature with label "/PEDIDOS" is clicked the destination is the Page "v1-open-requests-page"
        When the Feature with label "/NUEVO PEDIDO" is clicked the destination is the Page "v1-new-request-page"

    @D3D09 @D3D09.03
    Scenario: [D3D09.03]-The New Request page has two panels. The left Panel has a simple list of Parts that have at least one item. The right panel is the New Request.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the V1NewRequestPage is activated
        And the V1NewRequestPage has 2 panels
        And the left panel on page V1NewRequestPage is a "v1-available-parts-panel"
        And the left panel on page V1NewRequestPage has variant "-REQUEST-PART-LIST-"
        And the right panel on page V1NewRequestPage is a "v1-new-request-panel"

    @D3D09 @D3D09.04
    Scenario: [D3D09.04]-The left panel has a simplified, one level list of Parts. The listing contains Parts with any number of copies on stock.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the V1NewRequestPage is activated
        Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
        Then the target panel has a "viewer-panel"
        And the target panel has one or more "part"

    @D3D09 @D3D09.05
    Scenario: [D3D09.05]-The Parts visible on the New Request page have a defined list of fields.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the V1NewRequestPage is activated
        Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
        Given the target Part is one labeled "Boquilla Ganesha - Figura"
        And on the target Part there is a field named "ETIQUETA" with field name "label"
        And on the target Part there is a field named "MATERIAL" with field name "material"
        And on the target Part there is a field named "COLOR" with field name "color"
        And on the target Part there is a field named "DISPONIBLE" with field name "stockAvailable"

    @D3D09 @D3D09.06
    Scenario: [D3D09.06]-The right panel is the Request definition panel. It should have the New Request fields and a place to drop Parts.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the V1NewRequestPage is activated
        Given the target panel is the panel of type "v1-new-request-panel"
        Then the target panel has a title "/NUEVO PEDIDO/DEFINICION"
        Then the target panel has a field labeled "FECHA PEDIDO" named "requestDate" and not empty
        Then the target panel has a field labeled "ETIQUETA" named "label" and empty
        Then the target panel has a panel labeled "PIEZAS PEDIDO" named "requestParts" and with "0" elements

    @D3D09 @D3D09.07
    Scenario: [D3D09.07]-The Parts at the left panel can be dragged and deployed on the box at the right Panel.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the V1NewRequestPage is activated
        Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
        Given the target Part is one labeled "Boquilla Ganesha - Figura"
        Then the target part is draggable with the contraint "PART"
        Given the target panel is the panel of type "v1-new-request-panel"
        Then the target panel has a place for drop with the contraint "PART"

    @D3D09 @D3D09.08
    Scenario: [D3D09.08]-If we drag a Part to the right panel box the number of Parts associated to the Request increases.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the V1NewRequestPage is activated
        Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
        Given the target Part is one labeled "Boquilla Ganesha - Figura"
        Given the target panel is the panel of type "v1-new-request-panel"
        Then the target panel has a panel labeled "PIEZAS PEDIDO" named "requestParts" and with "0" elements
        When the target Part is dragged to the drop panel "dropParts"
        Then the target panel has a panel labeled "PIEZAS PEDIDO" named "requestParts" and with "1" elements

    @D3D09 @D3D09.09
    Scenario: [D3D09.09]-The New request shows two buttons.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the V1NewRequestPage is activated
        Given the target panel is the panel of type "v1-new-request-panel"
        Then the target panel has "2" buttons
        And the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
        And the target panel button with name "CANCEL" has a label "Cancelar" and is "enabled"

    @D3D09 @D3D09.10
    Scenario: [D3D09.10]-The New request shows two buttons. The save button is only active when all the fields are filled and there is at least one part on the list.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the V1NewRequestPage is activated
        Given the target panel is the panel of type "v1-new-request-panel"
        Then the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
        Given the form "v1-new-request-panel" be the target form
        When "Pedido de Pruebas" is set on the target form field "label"
        Then the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
        Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
        Given the target Part is one labeled "Boquilla Ganesha - Figura"
        Given the target panel is the panel of type "v1-new-request-panel"
        When the target Part is dragged to the drop panel "dropParts"
        Then the target panel button with name "SAVE" has a label "Guardar" and is "enabled"

    @D3D09 @D3D09.11
    Scenario: [D3D09.11]-If the SAVE button is active and it is clicked then the Request is persisted on the backend, a notification shown and the page clears.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the V1NewRequestPage is activated
        Given the target panel is the panel of type "v1-new-request-panel"
        Given the form "v1-new-request-panel" be the target form
        When "Pedido de Pruebas" is set on the target form field "label"
        Then the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
        Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
        Given the target Part is one labeled "Boquilla Ganesha - Figura"
        Given the target panel is the panel of type "v1-new-request-panel"
        When the target Part is dragged to the drop panel "dropParts"
        Then the target panel button with name "SAVE" has a label "Guardar" and is "enabled"
        When the target panel button with name "SAVE" is clicked
        Then the Request is persisted at the backend
        And there is a Notification panel
        And the active page is set to Dasboard
        And there are no Features active

    # @D3D09 @D3D09.12
    # Scenario: [D3D09.12]-If the CANCEL button is clicked then the page clears.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the V1NewRequestPage is activated
    #     Given the target panel is the panel of type "v1-new-request-panel"
    #     When the target panel button with name "CANCEL" is clicked
    #     And the active page is set to Dasboard
    #     And there are no Features active
