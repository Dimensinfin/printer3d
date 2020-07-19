@D3D11
Feature: [D3D11]-[STORY] Create a new Feature to see the list of Open Requests. A request is a need from a customer of a
    set of parts. If the request cannot be completed with stock parts then it is left Open.

    Read the Requests from the backend. The requests can be found on two states.
    COMPLETED means that there are enough Parts on the stock to serve the Request. The it can be closed using the close button.
    OPEN is that there are not enough Parts. Missing Parts are scheduled on higher priority on the list of Jobs.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D11 @D3D11.01
    Scenario: [D3D11.01]-Check that the Feature open the right page and shows the required components.
        Given one instance of Dock
        Given there is a click on Feature "/PEDIDOS"
        Then the page "OpenRequestsPage" is activated
        And the page "OpenRequestsPage" has 2 panels
        Given the target is the panel of type "open-requests"
        Given the target is the panel of type "request-detail"

    @D3D11 @D3D11.02
    Scenario: [D3D11.02]-When the open requests page is activate there are two panels. The left one if for the list of Requests. The right one is empty.
        Given there is a click on Feature "/PEDIDOS"
        Then the V1OpenRequestsPage is activated
        And the V1OpenRequestsPage has 2 panels
        Given the target is the panel of type "open-request-list"
        Then the target panel has a title "/PEDIDOS/ABIERTOS"
        Then on the target panel there are one or more "request"
        Given the target item the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given a hover on the target item
        Given the target is the panel of type "request-details"
        Then the target panel has a title "/PEDIDOS/DETALLE"
        Then the target panel has 1 "request"

    @D3D11 @D3D11.03
    Scenario: [D3D11.03]-There are two types of Requests. The COMPLETED and the OPEN.
        Given there is a click on Feature "/PEDIDOS"
        Given the target is the panel of type "open-request-list"
        Given the target item the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Then the target item has a mark "completed-request"
        Given the target item the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        Then the target item has a mark "open-request"

    @D3D11 @D3D11.04
    Scenario: [D3D11.04]-Both types of Requests have the next list of fields.
        Given there is a click on Feature "/PEDIDOS"
        Given the target is the panel of type "open-request-list"
        Given the target item the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        And the target item has a field named "FECHA" with label "FECHA" and value "Jun 29, 2020"
        And the target item has a field named "ETIQUETA" with label "ETIQUETA" and value "Complete Slot Car Platform P02"
        And the target item has a field named "PIEZAS" with label "NRO. PIEZAS" and value "4"
        And the target item has a field named "IMPORTE" with label "IMPORTE" and value "42 €"

    @D3D11 @D3D11.05
    Scenario: [D3D11.05]-If the user selects a Request by hovering on it then the detailed Request information is shown on the right panel.
        Given there is a click on Feature "/PEDIDOS"
        Given the target is the panel of type "open-request-list"
        Given the target item the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        Given a hover on the target item
        Then the target is the panel of type "request-details"
        Then the target panel has 1 "request"
        Given the target item the "request-item" with id "ed36cdfb-e5ae-4275-a163-63b4be4d952c"
        Then the target item has a column named "FALTAN" with value "1"
        And the target item has a column named "PEDIDAS" with value "2"
        And the target item has a column named "ETIQUETA" with value "Boquilla Ganesha - Figura"
        And the target item has a column named "MATERIAL" with value "PLA"
        And the target item has a column named "COLOR" with value "VERDE MENTA"

    @D3D11.06
    Scenario: [D3D11.06]-During the Request processing when downloaded convert RequestV1 to RequestV2 for rendering. There is no difference on rendering except for Parts and Models on the Request Details.
        Given there is a click on Feature "/PEDIDOS"
        Then the page "OpenRequestsPage" is activated
        Given the target is the panel of type "open-requests"
        Then the target panel has 2 "request"
        Given the target item the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        And the target item has a field named "FECHA" with label "FECHA" and value "Jun 29, 2020"
        And the target item has a field named "ETIQUETA" with label "ETIQUETA" and value "Complete Slot Car Platform P02"
        And the target item has a field named "PIEZAS" with label "NRO. PIEZAS" and value "4"
        And the target item has a field named "IMPORTE" with label "IMPORTE" and value "42 €"

        Given a hover on the target item
        Then the target is the panel of type "request-details"
        Then the target panel has 1 "request"
        Given the target item the "request-item" with id "ed36cdfb-e5ae-4275-a163-63b4be4d952c"
        Then the target item has a column named "FALTAN" with value "1"
        And the target item has a column named "PEDIDAS" with value "2"
        And the target item has a column named "ETIQUETA" with value "Boquilla Ganesha - Figura"
        And the target item has a column named "MATERIAL" with value "PLA"
        And the target item has a column named "COLOR" with value "VERDE MENTA"
        Given the target item the "request-item" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a column named "FALTAN" with value "1"
        And the target item has a column named "PEDIDAS" with value "2"
        And the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Verde"
        And the target item has a column named "MATERIAL" with value "-"
        And the target item has a column named "COLOR" with value "-"
