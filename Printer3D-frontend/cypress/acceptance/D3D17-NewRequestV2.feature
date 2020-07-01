@D3D17
Feature: [D3D17]-[STORY] Now that there are Parts and Models and they could be used inside a Request the Api model for the Request changes to a more generic type of container.

    Add support for Models on the New Request feature. Now we can drop Parts and Models.
    Render the Request contents differently is they are a part or a model.
    Update the Request price amount calculations.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D17.01
    Scenario: [D3D17.01]-The Parts at the left panel can be dragged and deployed on the box at the right Panel to be added to the Request.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Given the target panel is the panel of type "new-request"
        And the target panel has a drop place named "dropContents"
        When the drag source is dragged to the drop destination "dropContents"

        Then the target panel has a panel labeled "CONTENIDO PEDIDO" named "requestContents" and with "1" elements
        Given the target item the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then the target item has a column named "CANTIDAD" with value "1"
        Then the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Base"
        Then the target item has a column named "TERMINACION" with value "PLA/BLANCO"
        Then the target item has a named "remove-button" button

    @D3D17.02
    Scenario: [D3D17.02]-The Models at the left panel can be dragged and deployed on the box at the right Panel to be added to the Request.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Given the target panel is the panel of type "new-request"
        And the target panel has a drop place named "dropContents"
        When the drag source is dragged to the drop destination "dropContents"

        Then the target panel has a panel labeled "CONTENIDO PEDIDO" named "requestContents" and with "1" elements
        Given the target item the "request-content" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a column named "CANTIDAD" with value "1"
        Then the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Verde"
        Then the target item has a column named "TERMINACION" with value "-/-"
        Then the target item has a named "remove-button" button

# <div _ngcontent-igs-c89="" draggable="true" id="b6deea8c-1ea6-4a04-bbe6-486630dc4f2b" ng-reflect-drag-data="[object Object]" ng-reflect-drag-scope="REQUEST-CONTENT" ng-reflect-drag-class="part-selected" class="drag-handle ng-star-inserted"><div _ngcontent-igs-c89="" class="row part-panel"><div _ngcontent-igs-c89="" class="field label-width"><span _ngcontent-igs-c89="" cy-field-label="ETIQUETA" class="label">ETIQUETA</span><br _ngcontent-igs-c89=""><span _ngcontent-igs-c89="" cy-field-value="ETIQUETA" class="field-height part-material">BASE SLOT 1/32</span></div><div _ngcontent-igs-c89="" class="field material-width"><span _ngcontent-igs-c89="" cy-field-label="MATERIAL" class="label">MATERIAL</span><br _ngcontent-igs-c89=""><span _ngcontent-igs-c89="" cy-field-value="MATERIAL" class="field-height part-material">PLA</span></div><div _ngcontent-igs-c89="" class="field color-width"><span _ngcontent-igs-c89="" cy-field-label="COLOR" class="label">COLOR</span><br _ngcontent-igs-c89=""><span _ngcontent-igs-c89="" cy-field-value="COLOR" class="field-height part-color">MORADO TRANSPARENTE</span></div><div _ngcontent-igs-c89="" class="field stock-width"><span _ngcontent-igs-c89="" cy-field-label="DISPONIBLE" class="label">DISPONIBLE</span><br _ngcontent-igs-c89=""><span _ngcontent-igs-c89="" cy-field-value="DISPONIBLE" class="field-height part-stockAvailable">0</span></div></div><div _ngcontent-igs-c89=""><svg _ngcontent-igs-c89="" viewBox="0 0 100 100" class="corner-border"><path _ngcontent-igs-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="white"></path></svg><svg _ngcontent-igs-c89="" viewBox="0 0 100 100" class="corner-clear"><path _ngcontent-igs-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="#180A16"></path></svg><svg _ngcontent-igs-c89="" viewBox="0 0 100 100" class="corner-mark"><path _ngcontent-igs-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="greenyellow"></path></svg></div></div>

# <div _ngcontent-gmt-c89="" draggable="true" class="cornered-panel drag-handle ng-star-inserted" id="a12ec0be-52a4-424f-81e1-70446bc38372" ng-reflect-drag-data="[object Object]" ng-reflect-drag-scope="REQUEST-CONTENT" ng-reflect-drag-class="part-selected"><div _ngcontent-gmt-c89="" class="row"><div _ngcontent-gmt-c89="" class="field label-width"><span _ngcontent-gmt-c89="" cy-field-label="ETIQUETA" class="label">ETIQUETA</span><br _ngcontent-gmt-c89=""><span _ngcontent-gmt-c89="" cy-field-value="ETIQUETA" class="field-height part-material">PLATAFORMA SLOT 1/32 - Base</span></div><div _ngcontent-gmt-c89="" class="field material-width"><span _ngcontent-gmt-c89="" cy-field-label="MATERIAL" class="label">MATERIAL</span><br _ngcontent-gmt-c89=""><span _ngcontent-gmt-c89="" cy-field-value="MATERIAL" class="field-height part-material">PLA</span></div><div _ngcontent-gmt-c89="" class="field color-width"><span _ngcontent-gmt-c89="" cy-field-label="COLOR" class="label">COLOR</span><br _ngcontent-gmt-c89=""><span _ngcontent-gmt-c89="" cy-field-value="COLOR" class="field-height part-color">BLANCO</span></div><div _ngcontent-gmt-c89="" class="field stock-width"><span _ngcontent-gmt-c89="" cy-field-label="DISPONIBLE" class="label">DISPONIBLE</span><br _ngcontent-gmt-c89=""><span _ngcontent-gmt-c89="" cy-field-value="DISPONIBLE" class="field-height part-stockAvailable">0</span></div></div><svg _ngcontent-gmt-c89="" viewBox="0 0 100 100" class="corner-border"><path _ngcontent-gmt-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="white"></path></svg><svg _ngcontent-gmt-c89="" viewBox="0 0 100 100" class="corner-clear"><path _ngcontent-gmt-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="#180A16"></path></svg><svg _ngcontent-gmt-c89="" viewBox="0 0 100 100" class="corner-mark greenyellow-mark"><path _ngcontent-gmt-c89="" d="M100,100 L0,100 L100,0 L100,100"></path></svg></div>

# <div _ngcontent-gmt-c98="" draggable="true" class="cornered-panel drag-handle ng-star-inserted" id="0f789845-cdc6-48ce-a0ce-cbaf63cffab5" ng-reflect-drag-data="[object Object]" ng-reflect-drag-scope="REQUEST-CONTENT" ng-reflect-drag-class="model-selected"><div _ngcontent-gmt-c98="" class="row"><div _ngcontent-gmt-c98="" class="field label-width"><span _ngcontent-gmt-c98="" cy-field-label="label" class="label">ETIQUETA</span><br _ngcontent-gmt-c98=""><span _ngcontent-gmt-c98="" cy-field-value="label" class="field-height part-material">PLATAFORMA SLOT 1/32 - Verde</span></div><div _ngcontent-gmt-c98="" class="field price-width"><span _ngcontent-gmt-c98="" cy-field-label="partCount" class="label">NUMERO PIEZAS</span><br _ngcontent-gmt-c98=""><span _ngcontent-gmt-c98="" cy-field-value="partCount" class="field-height part-material">15 €</span></div><div _ngcontent-gmt-c98="" class="field price-width"><span _ngcontent-gmt-c98="" cy-field-label="price" class="label">PRECIO</span><br _ngcontent-gmt-c98=""><span _ngcontent-gmt-c98="" cy-field-value="price" class="field-height part-material">15 €</span></div></div><!--bindings={
#   "ng-reflect-ng-if": "false"
# }--><svg _ngcontent-gmt-c98="" viewBox="0 0 100 100" class="corner-border"><path _ngcontent-gmt-c98="" d="M100,100 L0,100 L100,0 L100,100" fill="white"></path></svg><svg _ngcontent-gmt-c98="" viewBox="0 0 100 100" class="corner-clear"><path _ngcontent-gmt-c98="" d="M100,100 L0,100 L100,0 L100,100" fill="#180A16"></path></svg><svg _ngcontent-gmt-c98="" viewBox="0 0 100 100" class="corner-mark blueviolet-mark"><path _ngcontent-gmt-c98="" d="M100,100 L0,100 L100,0 L100,100"></path></svg></div>
