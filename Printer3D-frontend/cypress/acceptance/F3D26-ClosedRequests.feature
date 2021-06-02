@F3D26
Feature: [F3D26]-This page lists the delivered customer requests.

    [STORY] The page of Closed Customer Requests will show delivered requests that have or not been paid.

    Background: Application landing page
        Given the application Printer3DManager
        Given the Feature "/PEDIDOS" is hovered
        When there is a click on Feature "/PEDIDOS CERRADOS"
        Then the page "Closed Requests Page" is activated
        When the application completes loading

    # - H A P P Y   P A T H
    # @F3D26.01
    # Scenario: [F3D26.01]-Validate the page structure. Two sections and the panel titles.
    #     Then the page "Closed Requests Page" has 3 sections
    #     Given the target is the panel of type "closed-requests"
    #     Then the target has the title "/PEDIDOS/CERRADOS"
    #     Given the target is the panel of type "request-details"
    #     Then the target has the title "/PEDIDOS/DETALLE"

    # @F3D26.02
    # Scenario: [F3D26.02]-This page has an action button panel that it is initially empty.
    #     Given the target is the panel of type "request-filter-buttons"

    # @F3D26.03
    # Scenario: [F3D26.03]-The Closed requests panel has closed Requests items.
    #     Given the target is the panel of type "closed-requests"
    #     Then the target has the title "/PEDIDOS/CERRADOS"
    #     And the target has 2 "request"

    # Scenario: [F3D26.03]-The Closed requests panel has the Requests that match the first panel filters.

    @F3D26.03
    Scenario: [F3D26.04]-There are two types of Requests. The PAID and the UNPAID requests. The already paid are on the final state CLOSED.
        Given the target is the panel of type "closed-requests"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        And field named "requestDate" with label "FECHA" has contents "Mar 3, 2021"
        And field named "label" with label "ETIQUETA" has contents "Primera parte pedido URBAN DIGIT."
        And field named "paid" with label "PAG." has contents "off"
        And field named "partCount" with label "NRO. PIEZAS" has contents "15"
        And field named "amount" with label "IMPORTE" has contents "125.00 €"
        And field named "total" with label "TOTAL+IVA" has contents "151.25 €"
        And the target item has a "orangered" tag
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And field named "requestDate" with label "FECHA" has contents "Jun 29, 2020"
        And field named "label" with label "ETIQUETA" has contents "Complete Slot Car Platform P01"
        And field named "paid" with label "PAG." has contents "on"
        And field named "partCount" with label "NRO. PIEZAS" has contents "9"
        And field named "amount" with label "IMPORTE" has contents "14.00 €"
        And field named "total" with label "TOTAL+IVA" has contents "16.94 €"
        And the target item has a "green" tag
# @D3D11.04
# Scenario: [D3D11.04]-Both types of Requests have the next list of fields.
#     Given the target is the panel of type "open-requests"
#     Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
#     And field named "requestDate" with label "FECHA" has contents "Apr 14, 2021"
#     And field named "label" with label "ETIQUETA" has contents "PEDIDO URBAN DIGIT 2021"
#     And field named "paid" with label "PAG." has contents "on"
#     And field named "partCount" with label "NRO. PIEZAS" has contents "15"
#     And field named "amount" with label "IMPORTE" has contents "125.00 €"
#     And field named "total" with label "TOTAL+IVA" has contents "151.25 €"

# @D3D11.05
# Scenario: [D3D11.05]-If the user selects a Request by clicking on it then the detailed Request information is shown on the right panel.
#     Given the target is the panel of type "open-requests"
#     Then the target has 3 "request"
#     Given the target the "request" with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a"
#     And field named "requestDate" with label "FECHA" has contents "Mar 3, 2021"
#     And field named "label" with label "ETIQUETA" has contents "Primera parte pedido URBAN DIGIT."
#     And field named "paid" with label "PAG." has contents "off"
#     And field named "partCount" with label "NRO. PIEZAS" has contents "2"
#     And field named "amount" with label "IMPORTE" has contents "30.00 €"
#     And field named "total" with label "TOTAL+IVA" has contents "36.30 €"
#     # - Click on a Request to display the details panel
#     Given the target is clicked
#     Then target is "selected"
#     Then the target is the panel of type "request-details"
#     Then the target has 1 "request"
#     Given the target the "request" with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a"
#     Then field named "requestDate" with label "FECHA" has contents "Mar 3, 2021"
#     And field named "customer" with label "DATOS COMPRADOR" has contents "Test Customer Name"
#     And field named "label" with label "ETIQUETA" has contents "Primera parte pedido URBAN DIGIT."
#     And field named "paid" with label "PAGADO" has contents "off"
#     And field named "partCount" with label "NRO. PIEZAS" has contents "2"
#     And field named "amount" with label "IMPORTE" has contents "30.00 €"
#     And field named "iva" with label "IVA" has contents "6.30 €"
#     And field named "total" with label "TOTAL" has contents "36.30 €"
#     # - Validate one of the request items
#     Given the target the "request-item" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
#     Then column named "missing" has contents "1"
#     And column named "quantity" has contents "2"
#     And column named "label" has contents "KIT X 1.32 - Rojo Rasta"
#     And column named "finishing" has contents "-/-"

# @D3D11.07
# Scenario: [D3D11.07]-The Request has a new field that shows the payment state.
#     Given the target is the panel of type "open-requests"
#     Given the target the "request" with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a"
#     And field named "paid" with label "PAG." has contents "off"
#     Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
#     And field named "paid" with label "PAG." has contents "on"
