@D3D12
Feature: [D3D12]-[STORY] When a request is selected then we can see the details.

    Select a request form the list of Open requests. Request of type Open are tagged red because cannot be closed.
    Requests with all the Parts available are tagged green and can be closed when selected.
    There is a button CLOSE to close the request and update the associated Parts stocks. This update is done at the background.
    Closed Requests do not apear on the Open Request list anymore.
    There is a Delete button that will remove the Request from the backend respository. This action requires a confirmation dialog.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/PEDIDOS"
        Then the page "Open Requests Page" is activated

    # - H A P P Y   P A T H
    @D3D12 @D3D12.01
    Scenario: [D3D12.01]-Select a Request from the list of requests in the Open state and verify that there is no Close button.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        Given a hover on the target
        # - Check the Request details and should not be Close button
        Given the target is the panel of type "request-detail"
        And the target has 1 "request"
        Given the target the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        # - Check the buttons and states
        And the target has 1 "button"
        And the target has no "complete-button" button
        And the button with name "delete-button" has a label "Borrar" and is "enabled"

    @D3D12 @D3D12.02
    Scenario: [D3D12.02]-Select a Request from the list of requests in the Completed state and verify that there is a Complete button.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given a hover on the target
        # - Check the buttons and states
        Given the target is the panel of type "request-detail"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the target has 2 "buttons"
        And the button with name "complete-button" has a label "Completar" and is "enabled"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"

    @D3D12 @D3D12.03
    Scenario: [D3D12.03]-If the Complete button is clicked the request is updated on the backend, a notification is shown and the page closes and returns to the Dashboard.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given a hover on the target
        # - Click on the Complete button
        Given the target is the panel of type "request-detail"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the button with name "complete-button" has a label "Completar" and is "enabled"
        And the button with name "complete-button" is clicked
        Then the Request is updated on the backend
        And there is a Notification panel
        And the active page is set to Dasboard
        And there are no Features active

    @D3D12 @D3D12.04
    Scenario: [D3D12.04]-When any Request on the list of Open Requests is hovered the Request Details shows the detailed data.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given a hover on the target
        # - Check the Request details
        Given the target is the panel of type "request-detail"
        And the target has 1 "request"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Then field named "requestDate" with label "FECHA" and value "Jun 29, 2020"
        And field named "label" with label "ETIQUETA" and value "Complete Slot Car Platform P01"
        And field named "partCount" with label "NRO. PIEZAS" and value "6"
        And field named "amount" with label "IMPORTE" and value "14 €"
        And target has a panel labeled "PIEZAS SOLICITADAS" named "contents"
        When the target is the panel of name "contents"
        And the target has 3 "request-item"
        # - Check the details for a Request item
        Given the target the "request-item" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then column named "missing" with contents "-"
        And column named "quantity" with contents "1"
        And column named "label" with contents "PLATAFORMA SLOT 1/32 - Base"
        And column named "finishing" with contents "PLA/BLANCO"

    @D3D12 @D3D12.05
    Scenario: [D3D12.05]-When the Detailed Request panel shows requests that are open because there are missing Parts then the Missed part should be highlighted and there should be a number with the number of missing parts.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        Given a hover on the target
        # - Check the Request details
        Given the target is the panel of type "request-detail"
        And the target has 1 "request"
        Given the target the "request" with id "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef"
        # - Check the details for a Request item
        Given the target the "request-item" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then column named "missing" with contents "1"
        And column named "quantity" with contents "2"
        And column named "label" with contents "PLATAFORMA SLOT 1/32 - Verde"
        And column named "finishing" with contents "-/-"

    @D3D12 @D3D12.06
    Scenario: [D3D12.06]-The Request detail always shows a Delete button. If this button is clicked there there is a confirmation dialog.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given a hover on the target
        # - Click on the Delete button
        Given the target is the panel of type "request-detail"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "delete-button" is clicked
        Then the "Delete Confirmation" dialog opens and blocks the display
        # - Check the dialog contents
        Given the target is the panel of name "delete-request-dialog"
        Then column named "confirmation" with contents "¿Realmente desea borrar el Pedido?Complete Slot Car Platform P01"
        And the target has 2 "buttons"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D12 @D3D12.07
    Scenario: [D3D12.07]-If the Cancel button of the Confirmation dialog is clicked then the dialog closes.
        # - Select a request to check its details.
        Given the target is the panel of type "open-requests"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        Given a hover on the target
        # - Click on the Delete button
        Given the target is the panel of type "request-detail"
        Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
        And the button with name "delete-button" has a label "Borrar" and is "enabled"
        And the button with name "delete-button" is clicked
        Then the "Delete Confirmation" dialog opens and blocks the display
        # - Click on the cancel of the Confirmation dialog
        Given the target is the panel of name "delete-request-dialog"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"
        When the button with name "cancel-button" is clicked
        Then the dialog closes

    # @D3D12 @D3D12.08
    # Scenario: [D3D12.08]-If the Delete button of the Confirmation dialog is clicked then the Request is deleted, a notification shown and the dialog closes.
    #     # - Select a request to check its details.
    #     Given the target is the panel of type "open-requests"
    #     Then the target has 2 "request"
    #     Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
    #     Given a hover on the target
    #     # - Click on the Delete button
    #     Given the target is the panel of type "request-detail"
    #     Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
    #     And the button with name "delete-button" has a label "Borrar" and is "enabled"
    #     And the button with name "delete-button" is clicked
    #     Then the "Delete Confirmation" dialog opens and blocks the display
    #     # - Click on the Delete of the Confirmation dialog
    #     Given the target is the panel of name "delete-request-dialog"
    #     Given response "200-REQUEST_DELETED" for "Get Open Requests"
    #     And the button with name "delete-button" has a label "Borrar" and is "enabled"
    #     When the button with name "delete-button" is clicked
    #     Then the Request is updated on the backend
    #     And there is a Notification panel
    #     And the dialog closes
    #     # Given there is a click on Feature "/PEDIDOS"
    #     # Given the target is the panel of type "open-requests"
    #     Then the target has 1 "request"

    # @D3D12 @D3D12.08
    # Scenario: [D3D12.08]-After the Request is deleted and the dialog closes the Open Requests page should refresh and the number of Requests opens should be smaller.

    # @D3D12 @D3D12.E.01
    # Scenario: [D3D12.E.01]-When deleting a request if the Request is not found on the backend repository we notify an exception and do not close the dialog.
    #     # - Select a request to check its details.
    #     Given the target is the panel of type "open-requests"
    #     Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
    #     Given a hover on the target
    #     # - Click on the Delete button
    #     Given the target is the panel of type "request-detail"
    #     Given the target the "request" with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9"
    #     And the button with name "delete-button" has a label "Borrar" and is "enabled"
    #     And the button with name "delete-button" is clicked
    #     Then the "Delete Confirmation" dialog opens and blocks the display
    #     # - Change the api simulator behavior
    #     Given response "404-REQUEST_NOT_FOUND" for "Get Open Requests"
    #     # - Click on the Delete of the Confirmation dialog
    #     Given the target is the panel of name "delete-request-dialog"
    #     And the button with name "delete-button" has a label "Borrar" and is "enabled"
    #     When the button with name "delete-button" is clicked
    #     Then the Request is updated on the backend
    #     And there is a "Error" Notification panel
    #     Then the "Delete Confirmation" dialog opens and blocks the display
