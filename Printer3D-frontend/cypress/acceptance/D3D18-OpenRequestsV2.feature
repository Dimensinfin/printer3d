@D3D18
Feature: [D3D18]-[STORY] Upgrade the page that lists the Open requests to the new request api. Old requests should be visible until closed but this is a backend functionality.

    Read the Requests from the backend.
    The requests reported can be with the RequestV1 format or the RequestV2 format. On the first the ids are always for Parts. On the seconds they can also point to Models
    The requests can be found on two states.
    COMPLETED means that there are enough Parts on the stock to serve the Request. The it can be closed using the close button.
    OPEN is that there are not enough Parts. Missing Parts are scheduled on higher priority on the list of Jobs.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D18.01
    Scenario: [D3D18.01]-During the Request processing when downloaded convert RequestV1 to RequestV2 for rendering.
        Given there is a click on Feature "/PEDIDOS"
        Then the page "OpenRequestsPage" is activated
        Given the target panel is the panel of type "open-requests"
        Then the target panel has 3 "request"
