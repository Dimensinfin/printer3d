@D3D12
Feature: [D3D12]-[STORY] When a request is selected then we can see the details.

    Select a request form the list of Open requests. Request of type Open are tagged red because cannot be closed.
    Requests with all the Parts available are tagged green and can be closed when selected.
    There is a button CLOSE to close the request and update the associated Parts stocks. This update is done at the background.
    Closed Requests do not apear on the Open Request list anymore.
    There is a Delete button that will remove the Request from the backend respository. This action requires a confirmation dialog.
    [STORY] The new Customer request Card displays more data about the Request. Added tax and total amount calculations.

    Background: Application landing page
        Given the application Printer3DManager
        Given the Feature "/PEDIDOS" is hovered
        When there is a click on Feature "/PEDIDOS ABIERTOS"
        Then the page "Open Requests Page" is activated
        When the application completes loading

    # - H A P P Y   P A T H
    @D3D12.01
    Scenario: [D3D12.01]-Select a Request from the list of requests in the Open state and verify that there is no Deliver button.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
        Given the target is clicked
        # - Check the Request details and should not be Close button
        Given the target is the panel of type "request-details"
        And the target has 1 "request"
        Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
        # - Check the buttons and states
        And the target has 2 "buttons"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "delete-button" has "ko" tag
        And the button with name "edit-button" has a label "Editar" and is "enabled"
        And the button with name "edit-button" has "edit" tag

    @D3D12.02
    Scenario: [D3D12.02]-Select a Request from the list of requests in the Completed state and verify that there is a Deliver button.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given the target is clicked
        # - Check the buttons and states
        Given the target is the panel of type "request-details"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the target has 3 "buttons"
        And the button with name "deliver-button" has a label "Entregar" and is "enabled"
        And the button with name "deliver-button" has "ok" tag
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "edit-button" has a label "Editar" and is "enabled"
        And the button with name "edit-button" has "edit" tag

    @D3D12.03
    Scenario: [D3D12.03]-If the Deliver button is clicked the request is updated on the backend, a notification is shown and the page closes and returns to the Dashboard.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given the target is clicked
        # - Click on the Complete button
        Given the target is the panel of type "request-details"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the button with name "deliver-button" has a label "Entregar" and is "enabled"
        And the button with name "deliver-button" has "ok" tag
        And the button with name "deliver-button" is clicked
        Then the Request is updated on the backend
        And there is a Notification panel
        And the active page is set to Dashboard
        Then there are 0 Features active

    @D3D12.04
    Scenario: [D3D12.04]-When any Request on the list of Open Requests is clicked the Request Details shows the detailed data.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
        Given the target is clicked
        # - Check the Request details
        Given the target is the panel of type "request-details"
        And field named "requestDate" with label "FECHA" has contents "Apr 14, 2021"
        And field named "customer" with label "DATOS COMPRADOR" is empty
        And field named "label" with label "ETIQUETA" has contents "PEDIDO URBAN DIGIT 2021"
        And field named "partCount" with label "NRO. PIEZAS" has contents "15"
        And field named "amount" with label "IMPORTE" has contents "125.00 €"
        And field named "iva" with label "IVA" has contents "26.25 €"
        And field named "total" with label "TOTAL" has contents "151.25 €"
        And field named "paid" with label "PAGADO" has contents "on"
        And target has a panel labeled "PIEZAS SOLICITADAS" named "contents"
        When the target is the panel of name "contents"
        And the target has 3 "request-item"
        # - Check the details for a Request item
        Given the target the "request-item" with id "ce7462e2-692c-4ce3-a871-35e909d039f0"
        Then column named "missing" has contents "5"
        And column named "quantity" has contents "5"
        And column named "label" has contents "CARCASA INFERIOR ''URBAN DIGIT''"
        And column named "finishing" has contents "PLA/BLACK"

    @D3D12.05
    Scenario: [D3D12.05]-When the Detailed Request panel shows requests that are open because there are missing Parts then the Missed part should be highlighted and there should be a number with the number of missing parts.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
        Given the target is clicked
        # - Check the Request details
        Given the target is the panel of type "request-details"
        And the target has 3 "request-item"
        # - Check the details for a Request item
        Given the target the "request-item" with id "ce7462e2-692c-4ce3-a871-35e909d039f0"
        Then column named "missing" has contents "5"
        And column named "quantity" has contents "5"
        And column named "label" has contents "CARCASA INFERIOR ''URBAN DIGIT''"
        And column named "finishing" has contents "PLA/BLACK"

    @D3D12.06
    Scenario: [D3D12.06.01]-The Request detail always shows a Delete button. If this button is clicked there there is a confirmation dialog.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given the target is clicked
        # - Click on the Delete button
        Given the target is the panel of type "request-details"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "delete-button" is clicked
        Then the "Delete Confirmation" dialog opens and blocks the display
        # - Check the dialog contents
        Given the target is the panel of name "delete-request-dialog"
        Then column named "confirmation" has contents "¿Realmente desea borrar el Pedido?Complete Slot Car Platform P01"
        And the target has 2 "buttons"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D12.06
    Scenario: [D3D12.06.02]-The Request detail always shows a Delete button. If this button is clicked there there is a confirmation dialog.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
        Given the target is clicked
        # - Click on the Delete button
        Given the target is the panel of type "request-details"
        Given the target the "request" with id "88d5785a-6bb4-4d89-9b2b-f590a1112d31"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "delete-button" is clicked
        Then the "Delete Confirmation" dialog opens and blocks the display
        # - Check the dialog contents
        Given the target is the panel of name "delete-request-dialog"
        Then column named "confirmation" has contents "¿Realmente desea borrar el Pedido?PEDIDO URBAN DIGIT 2021"
        And the target has 2 "buttons"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D12.07
    Scenario: [D3D12.07]-If the Cancel button of the Confirmation dialog is clicked then the dialog closes.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given the target is clicked
        # - Click on the Delete button
        Given the target is the panel of type "request-details"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "delete-button" is clicked
        Then the "Delete Confirmation" dialog opens and blocks the display
        # - Click on the cancel of the Confirmation dialog
        Given the target is the panel of name "delete-request-dialog"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"
        When the button with name "cancel-button" is clicked
        Then the dialog closes

    @D3D12 @D3D12.08
    Scenario: [D3D12.08]-If the Delete button of the Confirmation dialog is clicked then the Request is deleted, a notification shown and the dialog closes.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Then the target has 3 "request"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given the target is clicked
        # - Click on the Delete button
        Given the target is the panel of type "request-details"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "delete-button" is clicked
        Then the "Delete Confirmation" dialog opens and blocks the display
        # - Click on the Delete of the Confirmation dialog
        Given the target is the panel of name "delete-request-dialog"
        Given response "200-REQUEST_DELETED" for "Get Open Requests"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        When the button with name "delete-button" is clicked
        Then the Request is updated on the backend
        And there is a Notification panel

    # - E X C E P T I O N S
    @D3D12 @D3D12.E.01
    Scenario: [D3D12.E.01]-When deleting a request if the Request is not found on the backend repository we notify an exception and do not close the dialog.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given the target is clicked
        # - Click on the Delete button
        Given the target is the panel of type "request-details"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "delete-button" is clicked
        Then the "Delete Confirmation" dialog opens and blocks the display
        # - Change the api simulator behavior
        Given response "404-REQUEST_NOT_FOUND" for "Get Open Requests"
        # - Click on the Delete of the Confirmation dialog
        Given the target is the panel of name "delete-request-dialog"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        When the button with name "delete-button" is clicked
        Then the Request is updated on the backend
        And there is a "Error" Notification panel
