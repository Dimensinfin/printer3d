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
    # @D3D09 @D3D09.01
    # Scenario: [D3D09.01]-Check that the new Features point to the right page components.
    #     Given one instance of Dock
    #     When the Feature with label "/PEDIDOS" is clicked the destination is the Page "v1-request-list-page"
    #     When the Feature with label "/NUEVO PEDIDO" is clicked the destination is the Page "v1-new-request-page"

    # @D3D09 @D3D09.03
    # Scenario: [D3D09.03]-The New Request page has two panels. The left Panel has a simple list of Parts that have at least one item. The right panel is the New Request.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the V1NewRequestPage is activated
    #     And the V1NewRequestPage has 2 panels
    #     And the left panel on page V1NewRequestPage is a "v1-available-parts-panel"
    #     And the left panel on page V1NewRequestPage has variant "-REQUEST-PART-LIST-"
    #     And the right panel on page V1NewRequestPage is a "v1-new-request-panel"

    # @D3D09 @D3D09.04
    # Scenario: [D3D09.04]-The left panel has a simplified, one level list of Parts. The listing contains Parts with any number of copies on stock.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the V1NewRequestPage is activated
    #     Given the target panel is the panel with variant "-REQUEST-PART-LIST-"
    #     Then the target panel has a "viewer-panel"
    #     And the target panel has one or more "v1-part"

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
