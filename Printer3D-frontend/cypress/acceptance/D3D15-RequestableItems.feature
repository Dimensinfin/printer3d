@D3D15
Feature: [D3D15]-[STORY] For Requests the source of elements allowed to be added to the request is expanded to include the Models. The available list of elements to Request have changed to include Models.

        Validate that the render of Parts and Models follows the ordering requirements.
        Models go first then followed by Parts ordered by Label/Material/Color
        Models show a different set of fields than Parts and are expandable.
        When a Model is expanded it shows the list of Parts that are required for that Model instance.
    The elements that can be dragged to the drop destination are the Models and the real Parts. The parts that belong to the Model can also be dragged as a means of pointer references the same as the real Parts.¡

    Background: Application landing page
        Given the application Printer3DManager

    @D3D15.01
    Scenario: [D3D15.01]-When the New Request page open then we can see a panel with Models and Parts.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-request-elements"
        Then the target panel has 2 "model"
        Then the target panel has 16 "part"

    @D3D15.02
    Scenario: [D3D15.02]-Validate the fields shown by the Model and the data rendered by the Part.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-request-elements"

        Given the target item the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a field named "label" with label "ETIQUETA" and value "PLATAFORMA SLOT 1/32 - Verde"
        # Then the target item has a field named "partCount" with label "NUMERO PIEZAS" and value "5"
        Then the target item has a field named "price" with label "PRECIO" and value "15 €"

        Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then the target item has a field named "ETIQUETA" with label "ETIQUETA" and value "Boquilla Ganesha - Figura"
        Then the target item has a field named "MATERIAL" with label "MATERIAL" and value "PLA"
        Then the target item has a field named "COLOR" with label "COLOR" and value "ROSA"
        Then the target item has a field named "DISPONIBLE" with label "DISPONIBLE" and value "0"

# <div _ngcontent-qvc-c97="" draggable="true" class="cornered-panel drag-handle" id="0f789845-cdc6-48ce-a0ce-cbaf63cffab5" ng-reflect-drag-data="[object Object]" ng-reflect-drag-scope="ITEM" ng-reflect-drag-class="model-selected"><div _ngcontent-qvc-c97="" class="row"><div _ngcontent-qvc-c97="" class="field label-width"><span _ngcontent-qvc-c97="" cy-field-label="ETIQUETA" class="label">ETIQUETA</span><br _ngcontent-qvc-c97=""><span _ngcontent-qvc-c97="" cy-field-value="ETIQUETA" class="field-height part-material">PLATAFORMA SLOT 1/32 - Verde</span></div><div _ngcontent-qvc-c97="" class="field price-width"><span _ngcontent-qvc-c97="" cy-field-label="price" class="label">PRECIO</span><br _ngcontent-qvc-c97=""><span _ngcontent-qvc-c97="" cy-field-value="price" class="field-height part-material">15 €</span></div></div><svg _ngcontent-qvc-c97="" viewBox="0 0 100 100" class="corner-border"><path _ngcontent-qvc-c97="" d="M100,100 L0,100 L100,0 L100,100" fill="white"></path></svg><svg _ngcontent-qvc-c97="" viewBox="0 0 100 100" class="corner-clear"><path _ngcontent-qvc-c97="" d="M100,100 L0,100 L100,0 L100,100" fill="#180A16"></path></svg><svg _ngcontent-qvc-c97="" viewBox="0 0 100 100" class="corner-mark corner-blue"><path _ngcontent-qvc-c97="" d="M100,100 L0,100 L100,0 L100,100"></path></svg></div>
