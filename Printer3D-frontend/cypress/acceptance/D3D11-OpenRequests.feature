@D3D11
Feature: [D3D11]-[STORY] Create a new Feature to see the list of Open Requests. A request is a need from a customer of a
    set of parts. If the request cannot be completed with stock parts then it is left Open.

    Read the Requests from the backend. The requests can be found on two states.
    COMPLETED means that there are enough Parts on the stock to serve the Request. The it can be closed using the request button.
    OPEN is that there are not enough Parts. Missing Parts are scheduled on higher priority on the list of Jobs.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    # @D3D11 @D3D11.01
    # Scenario: [D3D11.01]-Check that the Feature open the right page and shows the required components.
    #     Given one instance of Dock
    #     Then there is a Feature with label "/PEDIDOS"
    #     When the Feature with label "/PEDIDOS" is clicked the destination is the Page "v1-open-requests-page"

    @D3D11 @D3D11.02
    Scenario: [D3D11.02]-When the open requests page is activate there thare two panels. The left one if for the list of Requests. The right one is empty.
        Given there is a click on Feature "/PEDIDOS"
        Then the V1OpenRequestsPage is activated
        And the V1OpenRequestsPage has 2 panels
        Given the target panel is the panel named "open-request-list"
        Then the target panel has a title "/PEDIDOS/ABIERTOS"
        Then on the target panel there are one or more "v1-request"
        Given the target item the "v1-request" with id "8674832e-8377-4e30-ab01-d6468a312012"
        Given a hover on the target item
        Given the target panel is the panel named "request-details"
        Then the target panel has a title "/PEDIDOS/DETALLE"
        Then on the target panel there is one "v1-request"

    # @D3D11 @D3D11.03
    # Scenario: [D3D11.03]-There are two types of Requests. The COMPLETED and the OPEN.
    #     Given there is a click on Feature "/PEDIDOS"
    #     Given the target panel is the panel named "open-request-list"
    #     Given the target item the "v1-request" with id "8674832e-8377-4e30-ab01-d6468a312012"
    #     Then the target item has a mark "completed-request"
    #     Given the target item the "v1-request" with id "9903926b-e786-4fb2-8e8e-68960ebebb7a"
    #     Then the target item has a mark "open-request"

    # @D3D11 @D3D11.04
    # Scenario: [D3D11.04]-Both types of Requests have the next list of fields.
    #     Given there is a click on Feature "/PEDIDOS"
    #     Given the target panel is the panel named "open-request-list"
    #     Given the target item the "v1-request" with id "8674832e-8377-4e30-ab01-d6468a312012"
    #     Then the target item has a field labeled "FECHA" with value "Jun 18, 2020"
    #     Then the target item has a field labeled "ETIQUETA" with value "Proveedor de llaves"
    #     Then the target item has a field labeled "PIEZAS" with value "2"
    #     Then the target item has a field labeled "IMPORTE" with value "4 €"

    @D3D11 @D3D11.05
    Scenario: [D3D11.05]-If the user selects a Request by hovering on it then the detailed Request information is shown on the right panel.
        Given there is a click on Feature "/PEDIDOS"
        Given the target panel is the panel named "open-request-list"
        Given the target item the "v1-request" with id "8674832e-8377-4e30-ab01-d6468a312012"
        Given a hover on the target item
        Then the target panel is the panel named "request-details"
        Then on the target panel there is one "v1-request"
        Given the target item the "v1-request" with id "8674832e-8377-4e30-ab01-d6468a312012"
        Then the target item has a field labeled "FECHA" with value "Jun 18, 2020"
        Then the target item has a field labeled "ETIQUETA" with value "Proveedor de llaves"
        Then the target item has a field labeled "PIEZAS" with value "2"
        Then the target item has a field labeled "IMPORTE" with value "4 €"
