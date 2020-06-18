@D3D11
Feature: [D3D11]-[STORY] Create a new Feature to see the list of Open Requests. A request is a need from a customer of a
    set of parts. If the request cannot be completed with stock parts then it is left Open.

    Read the Requests from the backend. The requests can be found on two states.
    COMPLETED means that there are enough Parts on the stock to serve the Request. The it can be closed using the request button.
    OPEN is that there are not enough Parts. Missing Parts are scheduled on higher priority on the list of Jobs.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D11 @D3D11.01
    Scenario: [D3D11.01]-Check that the Feature open the right page and shows the required components.
        Given one instance of Dock
        Then there is a Feature with label "/PEDIDOS"
        When the Feature with label "/PEDIDOS" is clicked the destination is the Page "v1-open-requests-page"

    @D3D11 @D3D11.02
    Scenario: [D3D11.02]-When the open requests page is activate there thare two panels. The left one if for the list of Requests. The right one is empty.
        Given there is a click on Feature "/PEDIDOS"
        Then the V1OpenRequestsPage is activated
        And the V1OpenRequestsPage has 2 panels
        Given the target panel is the panel named "open-request-list"
        Then the target panel has a title "/PEDIDOS/ABIERTOS"
Then on the target panel there are one or more "v1-request"
