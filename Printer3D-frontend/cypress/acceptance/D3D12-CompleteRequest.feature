@D3D12
Feature: [D3D12]-[STORY] When a request is selected then we can see the details and if all the required Parts are available
    the Request is on the COMPLETE state and the COMPLETE button allows to close the Request.

    Select a request form the list of Open requests. Request of type Open are marked red because cannot be closed.
    Requests with all the Parts avaialbe are marker green and can be closed when selected.
    There is a button CLOSE to close the request and update the associated Parts stocks. This update is done at the background.
    Closed Requests do not apear on the Open Request list anymore.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D12 @D3D12.01
    # Scenario: [D3D12.01]-Select a Request from the list of requests in the Open state and verify that there is no Close button.
    #     Given there is a click on Feature "/PEDIDOS"
    #     Given the target panel is the panel named "open-request-list"
    #     Given the target item the "request" with id "9903926b-e786-4fb2-8e8e-68960ebebb7a"
    #     Given a hover on the target item
    #     Then the target panel is the panel named "request-details"
    #     Then on the target panel there is one "request"
    #     Given the target item the "request" with id "9903926b-e786-4fb2-8e8e-68960ebebb7a"
    #     And in the target item there is no "COMPLETAR" button

    # @D3D12 @D3D12.02
    # Scenario: [D3D12.02]-Select a Request from the list of requests in the Completed state and verify that there is a Close button.
    #     Given there is a click on Feature "/PEDIDOS"
    #     Given the target panel is the panel named "open-request-list"
    #     Given the target item the "request" with id "8674832e-8377-4e30-ab01-d6468a312012"
    #     Given a hover on the target item
    #     Then the target panel is the panel named "request-details"
    #     Then on the target panel there is one "request"
    #     Given the target item the "request" with id "8674832e-8377-4e30-ab01-d6468a312012"
    #     And in the target item there is a "COMPLETAR" button

    # @D3D12 @D3D12.03
    # Scenario: [D3D12.03]-If the Complete button is clicked the request is updated on the backend, a notification is shown and the page closes and returns to the Dashboard.
    #     Given there is a click on Feature "/PEDIDOS"
    #     Given the target panel is the panel named "open-request-list"
    #     Given the target item the "request" with id "8674832e-8377-4e30-ab01-d6468a312012"
    #     Given a hover on the target item
    #     Then the target panel is the panel named "request-details"
    #     Given the target item the "request" with id "8674832e-8377-4e30-ab01-d6468a312012"
    #     And the target panel button with name "COMPLETAR" has a label "Completar" and is "enabled"
    #     When there is a click on the target panel "COMPLETAR" button
    #     Then the Request is updated on the backend
    #     And there is a Notification panel
    #     And the active page is set to Dasboard
    #     And there are no Features active

    # @D3D12 @D3D12.04
    # Scenario: [D3D12.04]-When the Detailed Request panel shows requests that are open because there are missing Parts then the Missed part should be highlighted and there should be a number with the number of missing parts.
    #     Given there is a click on Feature "/PEDIDOS"
    #     Given the target panel is the panel named "open-request-list"
    #     Given the target item the "request" with id "9903926b-e786-4fb2-8e8e-68960ebebb7a"
    #     Given a hover on the target item
    #     Then the target panel is the panel named "request-details"
    #     Then on the target panel there is one "request"
    #     Given the target item the "request" with id "9903926b-e786-4fb2-8e8e-68960ebebb7a"
    #     And in the target item there is no "COMPLETAR" button
    #     Given the target item the "part4-request" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
    #     Then the target item has a field named "FALTAN" with value "4"
    #     Then the target item has a field named "PEDIDAS" with value "4"
    #     Then the target item has a field named "ETIQUETA" with value "Boquilla Ganesha - Figura"
    #     Then the target item has a field named "MATERIAL" with value "PLA"
    #     Then the target item has a field named "COLOR" with value "GRIS"
