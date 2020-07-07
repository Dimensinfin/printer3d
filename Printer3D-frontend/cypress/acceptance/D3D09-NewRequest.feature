@D3D09
Feature: [D3D09]-[STORY] There is a Feature to create user Requests. It can accept Models and Parts and does some prcie calculations.

    The new implementation allows to drop Parts and Models into a new Request.
    There are minor differences on the reports contents between models and parts.
    During the Request composition the calculation fields are updated automatically.
    Models and parts are added to the Request by dropping them into a landing area. If we need more items of a same model we drop it several times.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D09.01
    Scenario: [D3D09.01]-Check that the New Request Features point to the right page components.
        Given one instance of Dock
        When the Feature with label "/NUEVO PEDIDO" is clicked the destination is the Page "NewRequestPage"

    @D3D09.02
    Scenario: [D3D09.02]-The right panel is the Request definition panel. It should have the New Request fields and a place to drop Parts.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "new-request"
        Then the target dialog has a title "/NUEVO PEDIDO/DEFINICION"
        And the target panel has a field named "requestDate" with label "FECHA PEDIDO" and not empty
        And the target panel has a field named "quantity" with label "NRO. PIEZAS" and contents "0"
        And the target panel has a field named "price" with label "PRECIO" and contents "0 €"
        And the target panel has a form field named "label" with label "ETIQUETA" and empty
        And the target panel input field named "label" is "invalid"
        And the target panel has a drop place named "dropContents"

    @D3D09.03
    Scenario: [D3D09.03]-The Parts at the left panel can be dragged and deployed on the box at the right Panel to be added to the Request.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Given the target panel is the panel of type "new-request"
        And the target panel has a drop place named "dropContents"
        # - Drag a part to the drop contents
        When the drag source is dragged to the drop destination "dropContents"

    @D3D09.04
    Scenario: [D3D09.04]-The Models at the left panel can be dragged and deployed on the box at the right Panel to be added to the Request.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Given the target panel is the panel of type "new-request"
        And the target panel has a drop place named "dropContents"
        # - Drag a model to the drop contents
        When the drag source is dragged to the drop destination "dropContents"

    @D3D09.05
    Scenario: [D3D09.05]-When a Part or a Model are dropped on the Request the data is displayed on the request content area.
        # - Activate the page
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        # - Select a Part for drag
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Select a Model for drag
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        # - Drag a model to the drop contents
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"

        # - Check the contents dropped on the request
        Then the target panel has a field named "quantity" with label "NRO. PIEZAS" and contents "2"
        And the target panel has a field named "price" with label "PRECIO" and contents "20 €"
        And the target panel has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target panel has 2 "request-content"
        # - Validate the contents columns
        Given the target item the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then the target item has a column named "CANTIDAD" with value "1"
        And the target item has a column named "PRECIO" with value "5 €"
        And the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Base"
        And the target item has a column named "TERMINACION" with value "PLA/BLANCO"
        And the target item has a named "remove-button" button
        # - Validate the contents columns
        Given the target item the "request-content" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a column named "CANTIDAD" with value "1"
        And the target item has a column named "PRECIO" with value "15 €"
        And the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Verde"
        And the target item has a column named "TERMINACION" with value "-/-"
        And the target item has a named "remove-button" button

    @D3D09.06
    Scenario: [D3D09.06]-If we drag the same part multiple times the number of contents does not change but other fields reflect the new count.
        # - Activate the page
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        # - Select a Part for drag
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the contents dropped on the request
        Then the target panel has a field named "quantity" with label "NRO. PIEZAS" and contents "1"
        And the target panel has a field named "price" with label "PRECIO" and contents "5 €"
        And the target panel has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target panel has 1 "request-content"
        # - Validate the contents columns
        Given the target item the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then the target item has a column named "CANTIDAD" with value "1"
        And the target item has a column named "PRECIO" with value "5 €"
        And the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Base"
        And the target item has a column named "TERMINACION" with value "PLA/BLANCO"
        And the target item has a named "remove-button" button
        # - Drag a part to the drop contents
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the contents dropped on the request
        Then the target panel has a field named "quantity" with label "NRO. PIEZAS" and contents "2"
        And the target panel has a field named "price" with label "PRECIO" and contents "10 €"
        And the target panel has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target panel has 1 "request-content"
        # - Validate the contents columns
        Given the target item the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then the target item has a column named "CANTIDAD" with value "2"
        And the target item has a column named "PRECIO" with value "10 €"
        And the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Base"
        And the target item has a column named "TERMINACION" with value "PLA/BLANCO"
        And the target item has a named "remove-button" button

    @D3D09.07
    Scenario: [D3D09.07]-The New request shows two buttons.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "new-request"
        # - Check the state of the buttons
        Then the target panel button with name "save" has a label "Guardar" and is "disabled"
        And the target panel button with name "cancel" has a label "Cancelar" and is "enabled"

    @D3D09.08
    Scenario: [D3D09.08]-If the label field is filled and there it at least one content then the Save button activates.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "new-request"
        # - Validate the label field
        And the target panel input field named "label" is "invalid"
        When the target panel field "label" is tested for size constraints 3 and 50
        And "Pedido de Prueba P01" is set on form field "label"
        And the target panel input field named "label" is "valid"
        # - Check the state of the buttons
        Then the target panel button with name "save" has a label "Guardar" and is "disabled"
        And the target panel button with name "cancel" has a label "Cancelar" and is "enabled"
        # - Select a Part for drag
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the state of the buttons
        Then the target panel button with name "save" has a label "Guardar" and is "enabled"
        And the target panel button with name "cancel" has a label "Cancelar" and is "enabled"

    @D3D09.09
    Scenario: [D3D09.09]-If the Save button is active and it is clicked then the Request is persisted on the backend, a notification shown and the page clears.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "new-request"
        # - Select a Part for drag
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target panel is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        And "Pedido de Prueba P01" is set on form field "label"
        # - Check the state of the buttons
        Then the target panel button with name "save" has a label "Guardar" and is "enabled"
        And the target panel button with name "cancel" has a label "Cancelar" and is "enabled"
        When the target panel button with name "save" is clicked
        Then the Request is persisted at the backend
        And there is a Notification panel
        And the active page is set to Dasboard
        And there are no Features active

    @D3D09.10
    Scenario: [D3D09.10]-If the Cancel button is clicked then the page clears.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "new-request"
        # - Check the state of the buttons
        Then the target panel button with name "save" has a label "Guardar" and is "enabled"
        And the target panel button with name "cancel" has a label "Cancelar" and is "enabled"
        When the target panel button with name "cancel" is clicked
        And the active page is set to Dasboard
        And there are no Features active



# @D3D09.15
# Scenario: [D3D09.15]-If the user clicks on the Remove button of a Part assigned to a Request then the number of that type of Parts reduces in 1. The the count reaches 0 then the part is removed from the Report.
#     Given there is a click on Feature "/NUEVO PEDIDO"
#     Then the page "NewRequestPage" is activated
#     Given the target panel is the panel of type "available-request-elements"
#     And the drag source the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
#     Given the target panel is the panel of type "new-request"
#     When the drag source is dragged to the drop destination "dropContents"
#     Then the target panel has 1 "part4-request"
#     Given the target item the "part4-request" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
#     Then the target item has a field named "REQUERIDAS" with value "1"
#     When the drag source is dragged to the drop destination "dropParts"
#     Then the target panel has 1 "part4-request"
#     Given the target item the "part4-request" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
#     Then the target item has a field named "REQUERIDAS" with value "2"

#     Given the target panel is the panel of type "available-request-elements"
#     Given the drag source the "part" with id "754e6cc5-1a8a-435b-8c17-956b2a8391a7"
#     Given the target panel is the panel of type "new-request"
#     When the drag source is dragged to the drop destination "dropContents"
#     Then the target panel has 2 "part4-request"

#     Given the target item the "part4-request" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
#     Then the target item has a field named "REQUERIDAS" with value "2"
#     When the target item Remove button is clicked
#     Then the target item has a field named "REQUERIDAS" with value "1"
#     Then the target panel has 2 "part4-request"
#     When the target item Remove button is clicked
#     Then the target panel has 1 "part4-request"
#     Given the target item the "part4-request" with id "754e6cc5-1a8a-435b-8c17-956b2a8391a7"
#     When the target item Remove button is clicked
#     Then the target panel has no "part4-request"
