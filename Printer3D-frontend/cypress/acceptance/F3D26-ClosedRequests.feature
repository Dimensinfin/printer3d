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
    @F3D26.01
    Scenario: [F3D26.01]-Validate the page structure. Two sections and the panel titles.
        Then the page "Closed Requests Page" has 3 sections
        Given the target is the panel of type "closed-requests"
        Then the target has the title "/PEDIDOS/CERRADOS"
        Given the target is the panel of type "request-details"
        Then the target has the title "/PEDIDOS/DETALLE"

    @F3D26.02
    Scenario: [F3D26.02]-This page has an action button panel that it is initially empty.
        Given the target is the panel of type "request-filter-buttons"

    @F3D26.03
    Scenario: [F3D26.03]-The Closed requests panel has closed Requests items.
        Given the target is the panel of type "closed-requests"
        Then the target has the title "/PEDIDOS/CERRADOS"
        And the target has 2 "request"

    @F3D26.04
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

    @F3D26.05
    Scenario: [F3D26.05]-The Closed Requests Details panel on initialization is empty.
        Given the target is the panel of type "request-details"
        Then the target has 0 "request"

    @F3D26.06
    Scenario: [F3D26.06]-If the user selects a Request by clicking on it then the detailed Request information is shown on the right panel.
        Given the target is the panel of type "closed-requests"
        Then the target has 2 "request"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        # - Click on a Request to display the details panel
        Given the target is clicked
        Then target is "selected"
        Then the target is the panel of type "request-details"
        Then the target has 1 "request"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        Then field named "requestDate" with label "FECHA" has contents "Mar 3, 2021"
        And field named "customer" with label "DATOS COMPRADOR" has contents "Test Customer Name"
        And field named "label" with label "ETIQUETA" has contents "Primera parte pedido URBAN DIGIT."
        And field named "paid" with label "PAGADO" has contents "off"
        And field named "partCount" with label "NRO. PIEZAS" has contents "15"
        And field named "amount" with label "IMPORTE" has contents "125.00 €"
        And field named "iva" with label "IVA" has contents "26.25 €"
        And field named "total" with label "TOTAL" has contents "151.25 €"
        # - Validate one of the request items
        Given the target the "request-item" with id "ce7462e2-692c-4ce3-a871-35e909d039f0"
        Then column named "missing" has contents "-"
        And column named "quantity" has contents "5"
        And column named "label" has contents "CARCASA INFERIOR ''URBAN DIGIT''"
        And column named "finishing" has contents "PLA/BLACK"

    @F3D26.07
    Scenario: [F3D26.07]-Unpaid Customer requests have a PAY button.
        Given the target is the panel of type "closed-requests"
        Then the target has 2 "request"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        # - Click on a Request to display the details panel
        Given the target is clicked
        Then target is "selected"
        Then the target is the panel of type "request-details"
        Then the target has 1 "request"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        Then the target has 1 "buttons"
        And the button with name "pay-button" has a label "Cobrar" and is "enabled"

    @F3D26.08
    Scenario: [F3D26.08]-Paid Customer requests have no buttons.
        Given the target is the panel of type "closed-requests"
        Then the target has 2 "request"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        # - Click on a Request to display the details panel
        Given the target is clicked
        Then target is "selected"
        Then the target is the panel of type "request-details"
        Then the target has 1 "request"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the target has 0 "buttons"

    @F3D26.09
    Scenario: [F3D26.09]-If the Paid button is clicked then there is a confirmation dialog.
        # - Select a request to check its details.
        Given the target is the panel of type "closed-requests"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        Given the target is clicked
        # - Click on the Complete button
        Given the target is the panel of type "request-details"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        Then the button with name "pay-button" has a label "Cobrar" and is "enabled"
        And the button with name "pay-button" has "ok" tag
        And the button with name "pay-button" is clicked
        Then the "Pay Confirmation" dialog opens and blocks the display
        # - Check the dialog contents
        Given the target is the panel of name "pay-confirmation-dialog"
        Then column named "confirmation" has contents "¿Realmente desea marcar el Pedido como cobrado?Primera parte pedido URBAN DIGIT."
        And the target has 2 "buttons"
        And the button with name "confirm-button" has a label "Cobrar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @F3D26.10
    Scenario: [F3D26.10]-When charging a request when the CHARGE button of the Confirmation dialog is clicked then the Request is paid, a notification shown and the dialog closes.
        # - Select a request to check its details.
        Given the target is the panel of type "closed-requests"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        Given the target is clicked
        # - Click on the Complete button
        Given the target is the panel of type "request-details"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        Then the button with name "pay-button" has a label "Cobrar" and is "enabled"
        And the button with name "pay-button" has "ok" tag
        And the button with name "pay-button" is clicked
        Then the "Pay Confirmation" dialog opens and blocks the display
        # - Click on the Charge of the Confirmation dialog
        Given the target is the panel of name "pay-confirmation-dialog"
        Given response "200-REQUEST_PAYD" for "Get Closed Requests"
        When the button with name "confirm-button" is clicked
        Then the Request is updated on the backend
        And there is a Notification panel

    @F3D26.11
    Scenario: [F3D26.11]-When charging a request if the CANCEL button of the Confirmation dialog is clicked then the request dialog closes.
        # - Select a request to check its details.
        Given the target is the panel of type "closed-requests"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        Given the target is clicked
        # - Click on the Complete button
        Given the target is the panel of type "request-details"
        Given the target the "request" with id "103477e5-de44-4542-90d4-d08e44b2f6c0"
        Then the button with name "pay-button" has a label "Cobrar" and is "enabled"
        And the button with name "pay-button" has "ok" tag
        And the button with name "pay-button" is clicked
        Then the "Pay Confirmation" dialog opens and blocks the display
        # - Click on the Charge of the Confirmation dialog
        Given the target is the panel of name "pay-confirmation-dialog"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"
        When the button with name "cancel-button" is clicked
        Then the dialog closes
