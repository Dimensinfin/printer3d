@D3D09
Feature: [D3D09]-[STORY] Add a new Feature to create Requests. The request is filled by moving parts from the left Part Catalog to the right Request.

    Add a new Feature to create Requests.
    The request is filled by moving parts from the left Part Catalog to the right Request.
    Requests can have states to mean if a request is completed and served or if the requests is open waiting for missed Parts.
    Requests are prioritized. The intial algorithm should be the first come the first served.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D09 @D3D09.01
    Scenario: [D3D09.01]-Check that the new Features point to the right page components.
        Given one instance of Dock
        When the Feature with label "/PEDIDOS" is clicked the destination is the Page "v1-request-list-page"
        When the Feature with label "/NUEVO PEDIDO" is clicked the destination is the Dialog "v1-new-request-dialog"
