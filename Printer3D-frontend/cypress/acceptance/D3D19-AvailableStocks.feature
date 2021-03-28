@D3D19
Feature: [D3D19]-[STORY] There is a list of items that can be deployed inside a Request.

    To create a request there is a panel that contains the elements that can be deployed on the Request.
    This list has also some interactions and some data on the panels that is described on this feature.

    Background: Application landing page
        Given the application Printer3DManager
        When the application completes loading

    # - H A P P Y   P A T H
    @D3D19.01
    Scenario: [D3D19.01]-One of the available item type is the Model. It should show the next fields.
        # - Target the available list of items
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target is the panel of type "available-request-elements"

        # - Select a Model and validate its fields
        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then field named "label" with label "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Verde"
        And field named "partCount" with label "NUMERO PIEZAS" has contents "6"
        And field named "price" with label "PRECIO" has contents "15 €"

    @D3D19.02
    Scenario: [D3D19.02]-The other type of item is a Part. Check their fields.
        # - Target the available list of items
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target is the panel of type "available-request-elements"

        # - Select a Part and validate its fields
        Given the target the "part" with id "52372bd9-76a3-4f65-926e-a50a896961c0"
        Then field named "label" with label "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Base"
        And field named "material" with label "MATERIAL/COLOR" has contents "PLA/MORADO TRANSPARENTE"
        And field named "partCount" with label "DISPONIBLE" has contents "0"
        And field named "price" with label "PRECIO" has contents "5 €"
