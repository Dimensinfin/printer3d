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
        When the application completes loading

    # - H A P P Y   P A T H
    @D3D11 @D3D11.02
    Scenario: [D3D11.02]-If the cursor clicks on a Request on the left panel then the Request details is shown on the right.
        And the page "Open Requests Page" has 2 panels
        # - Validate the structure for the Requests panel
        Given the target is the panel of type "open-requests"
        Then the target has the title "/PEDIDOS/ABIERTOS"
        And the target has 3 "request"
        Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
        # - Click on a Request to display the details panel
        Given the target is clicked
        Then target is "selected"
        Given the target is the panel of type "request-details"
        Then the target has the title "/PEDIDOS/DETALLE"
        Then the target has 1 "request"

    @D3D11 @D3D11.03
    Scenario: [D3D11.03]-There are two types of Requests. The COMPLETED and the OPEN.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Then the target item has a mark "completed-request"
        Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
        Then the target item has a mark "open-request"

    @D3D11 @D3D11.04
    Scenario: [D3D11.04]-Both types of Requests have the next list of fields.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
        And field named "requestDate" with label "FECHA" has contents "Apr 14, 2021"
        And field named "label" with label "ETIQUETA" has contents "PEDIDO URBAN DIGIT 2021"
        And field named "partCount" with label "NRO. PIEZAS" has contents "15"
        And field named "amount" with label "IMPORTE" has contents "125 €"

    @D3D11 @D3D11.05
    Scenario: [D3D11.05]-If the user selects a Request by clicking on it then the detailed Request information is shown on the right panel.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
        # - Click on a Request to display the details panel
        Given the target is clicked
        Then target is "selected"
        Then the target is the panel of type "request-details"
        Then the target has 1 "request"
        Given the target the "request-item" with id "72168412-af2d-4969-b1ea-362ea10370f2"
        Then column named "missing" has contents "1"
        And column named "quantity" has contents "5"
        And column named "label" has contents "CARCASA SUPERIOR ''URBAN DIGIT''"
        And column named "finishing" has contents "PLA/BLACK"

    @D3D11.06
    Scenario: [D3D11.06]-During the Request processing when downloaded convert RequestV1 to RequestV2 for rendering. There is no difference on rendering except for Parts and Models on the Request Details.
        Given the target is the panel of type "open-requests"
        Then the target has 3 "request"
        Given the target the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        And field named "requestDate" with label "FECHA" has contents "Jun 29, 2020"
        And field named "label" with label "ETIQUETA" has contents "Complete Slot Car Platform P02"
        And field named "partCount" with label "NRO. PIEZAS" has contents "2"
        And field named "amount" with label "IMPORTE" has contents "30 €"
        # - Click on a Request to display the details panel
        Given the target is clicked
        Then target is "selected"
        Then the target is the panel of type "request-details"
        Then the target has 1 "request"
        Given the target the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        Then column named "missing" has contents "1"
        And column named "quantity" has contents "2"
        And column named "label" has contents "KIT X 1.32 - Rojo Rasta"
        And column named "finishing" has contents "-/-"
