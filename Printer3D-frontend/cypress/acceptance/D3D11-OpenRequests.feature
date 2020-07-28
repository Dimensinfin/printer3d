@D3D11
Feature: [D3D11]-[STORY] Create a new Feature to see the list of Open Requests. A request is a need from a customer of a
    set of parts. If the request cannot be completed with stock parts then it is left Open.

    Read the Requests from the backend. The requests can be found on two states.
    COMPLETED means that there are enough Parts on the stock to serve the Request. The it can be closed using the close button.
    OPEN is that there are not enough Parts. Missing Parts are scheduled on higher priority on the list of Jobs.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/PEDIDOS"
        Then the page "Open Requests Page" is activated

    # - H A P P Y   P A T H
    @D3D11 @D3D11.02
    Scenario: [D3D11.02]-If the cursor hovers over a Request on the left panel then the Request details is shown on the right.
        And the page "Open Requests Page" has 2 panels
        # - Validate the structure for the Requests panel
        Given the target is the panel of type "open-requests"
        Then the target has the title "/PEDIDOS/ABIERTOS"
        And the target has 2 "request"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        # - Hover over a Request to diaply the details panel
        Given a hover on the target
        Given the target is the panel of type "request-details"
        Then the target has the title "/PEDIDOS/DETALLE"
        Then the target has 1 "request"

    @D3D11 @D3D11.03
    Scenario: [D3D11.03]-There are two types of Requests. The COMPLETED and the OPEN.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Then the target item has a mark "completed-request"
        Given the target the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        Then the target item has a mark "open-request"

    @D3D11 @D3D11.04
    Scenario: [D3D11.04]-Both types of Requests have the next list of fields.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        And field named "FECHA" with label "FECHA" has contents "Jun 29, 2020"
        And field named "ETIQUETA" with label "ETIQUETA" has contents "Complete Slot Car Platform P02"
        And field named "PIEZAS" with label "NRO. PIEZAS" has contents "4"
        And field named "IMPORTE" with label "IMPORTE" has contents "42 €"

    @D3D11 @D3D11.05
    Scenario: [D3D11.05]-If the user selects a Request by hovering on it then the detailed Request information is shown on the right panel.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        Given a hover on the target
        Then the target is the panel of type "request-details"
        Then the target has 1 "request"
        Given the target the "request-item" with id "ed36cdfb-e5ae-4275-a163-63b4be4d952c"
        Then column named "missing" has contents "1"
        And column named "quantity" has contents "2"
        And column named "label" has contents "Boquilla Ganesha - Figura"
        And column named "finishing" has contents "PLA/VERDE MENTA"

    @D3D11.06
    Scenario: [D3D11.06]-During the Request processing when downloaded convert RequestV1 to RequestV2 for rendering. There is no difference on rendering except for Parts and Models on the Request Details.
        Then the page "OpenRequestsPage" is activated
        Given the target is the panel of type "open-requests"
        Then the target has 2 "request"
        Given the target the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        And field named "FECHA" with label "FECHA" has contents "Jun 29, 2020"
        And field named "ETIQUETA" with label "ETIQUETA" has contents "Complete Slot Car Platform P02"
        And field named "PIEZAS" with label "NRO. PIEZAS" has contents "4"
        And field named "IMPORTE" with label "IMPORTE" has contents "42 €"

        Given a hover on the target
        Then the target is the panel of type "request-details"
        Then the target has 1 "request"
        Given the target the "request-item" with id "ed36cdfb-e5ae-4275-a163-63b4be4d952c"
        Then column named "missing" has contents "1"
        And column named "quantity" has contents "2"
        And column named "label" has contents "Boquilla Ganesha - Figura"
        And column named "finishing" has contents "PLA/VERDE MENTA"
        Given the target the "request-item" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then column named "missing" has contents "1"
        And column named "quantity" has contents "2"
        And column named "label" has contents "PLATAFORMA SLOT 1/32 - Verde"
        And column named "finishing" has contents "-/-"
