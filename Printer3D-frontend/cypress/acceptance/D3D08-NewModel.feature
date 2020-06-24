@D3D08
Feature: [D3D08]-[STORY] There should be a Feature to create Models. Models have some data fields and an area where the user can deploy from a list of parts to define the Parts that compose the model.

    To create Models we are going to use a Page and not a Dialog. We need to allow to drag and drop Parts into the Model for its construction.
    A Model is a set of Parts that can have a different price and packaging than the parts alone.
    Models should not be edited since a change on a Model should really be the creation of a new Model reference.
    A same model can be created using same parts but of different color. This should be a different Model and have a different Label.
    Labels can be repeated and are not editable.
    Price should be editable because things can change in value depending on demand.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    # @D3D08 @D3D08.01
    # Scenario: [D3D08.01]-When the Feature is clicked the display open on the New Model page.
    #     Given one instance of Dock
    #     When the Feature with label "/NUEVO MODELO" is clicked the destination is the Page "NewModelPage"

    # @D3D08 @D3D08.02
    # Scenario: [D3D08.02]-The New Model page has two panels. One contains the Parts (active) that can be used in the MOdel and the other panel contains the model being created.
    #     Given one instance of Dock
    #     When the Feature with label "/NUEVO MODELO" is clicked the destination is the Page "NewModelPage"
    #     Then the page "NewModelPage" is activated
    #     And the page "NewModelPage" has 2 panels
    #     And the target page has one panel of type "available-parts" with variant "-NEW-MODEL-"
    #     And the target page has one panel of type "new-model"

    # @D3D08 @D3D08.03
    # Scenario: [D3D08.03]-The Parts panel has a simplified, one level list of Parts. The listing contains Parts with any number of copies on stock but that are active.
    #     Given there is a click on Feature "/NUEVO MODELO"
    #     Then the page "NewModelPage" is activated
    #     Given the target panel is the panel of type "available-parts"
    #     Then the target panel has a title "/PIEZAS DISPONIBLES"
    #     And the target panel has 12 "part"
    #     Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
    #     Then the target item has a field named "ETIQUETA" with label "ETIQUETA" and value "Boquilla Ganesha - Figura"
    #     Then the target item has a field named "MATERIAL" with label "MATERIAL" and value "PLA"
    #     Then the target item has a field named "COLOR" with label "COLOR" and value "ROSA"
    #     Then the target item has a field named "DISPONIBLE" with label "DISPONIBLE" and value "0"

    @D3D08 @D3D08.04
    Scenario: [D3D08.04]-The New Model panel has some input fields and a place to drop the Parts that are to be used on the Model.
        Given there is a click on Feature "/NUEVO MODELO"
        Then the page "NewModelPage" is activated
        Given the target panel is the panel of type "new-model"
        Then the target panel has an input field named "label" with label "ETIQUETA" and contents ""
        Then the target panel has an input field named "price" with label "PRECIO" and contents ""
        Then the target panel has an input field named "stock" with label "NIVEL STOCK DESEADO" and contents ""
        And the target panel input field named "label" is "invalid"
        And the target panel input field named "price" is "invalid"
        And the target panel input field named "stock" is "invalid"
        And the target panel has a drop place named "drop-part-location"
        And the target panel button with name "SAVE" has a label "Guardar" and is "disabled"

    @D3D08 @D3D08.05
    Scenario: [D3D08.05]-The New Model panel has buttons to save the Model. The Save button is only active when all the constraints are validated.
        Given there is a click on Feature "/NUEVO MODELO"
        Then the page "NewModelPage" is activated
        Given the target panel is the panel of type "new-model"
        And the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
        When "Modelo de prueba 1" is set on the target panel input field named "label"
        Then the target panel input field named "label" is "valid"
        And the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
        And "2" is set on the target panel input field named "price"
        Then the target panel input field named "price" is "valid"
        And the target panel button with name "SAVE" has a label "Guardar" and is "disabled"
        And "8" is set on the target panel input field named "stock"
        Then the target panel input field named "stock" is "valid"
        And the target panel button with name "SAVE" has a label "Guardar" and is "disabled"

        Given the target panel is the panel of type "available-parts"
        And the drag source the "part" with id "754e6cc5-1a8a-435b-8c17-956b2a8391a7"
        Given the target panel is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"
        Then the target panel button with name "SAVE" has a label "Guardar" and is "enabled"

    @D3D08 @D3D08.06
    Scenario: [D3D08.06]-When on eor more Parts are dropped on the Model the dropped Part information has some required fields.
        Given there is a click on Feature "/NUEVO MODELO"
        Then the page "NewModelPage" is activated
        Given the target panel is the panel of type "new-model"
        When "Modelo de prueba 1" is set on the target panel input field named "label"
        And "2" is set on the target panel input field named "price"
        And "8" is set on the target panel input field named "stock"

        Given the target panel is the panel of type "available-parts"
        And the drag source the "part" with id "754e6cc5-1a8a-435b-8c17-956b2a8391a7"
        Given the target panel is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"

        Given the target panel is the panel of type "available-parts"
        And the drag source the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Given the target panel is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"
        Given the target panel is the panel of type "available-parts"
        And the drag source the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Given the target panel is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"
        Given the target panel is the panel of type "available-parts"
        And the drag source the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Given the target panel is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"

        Given the target panel is the panel of type "available-parts"
        And the drag source the "part" with id "cb3f7075-e364-4c41-8d4b-a31a8f2039fe"
        Given the target panel is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"

        Given the target item the "part-stack" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        Then the target item has a field named "REQUERIDAS" with value "3"
        Then the target item has a field named "ETIQUETA" with value "Boquilla Ganesha - Embocadura"
        Then the target item has a field named "MATERIAL" with value "TPU"
        Then the target item has a field named "COLOR" with value "AZUL"
        Then the target item has a actionable image named "remove-button"



# <div _ngcontent-qwq-c89="" draggable="true" id="754e6cc5-1a8a-435b-8c17-956b2a8391a7" ng-reflect-drag-data="[object Object]" ng-reflect-drag-scope="PART" ng-reflect-drag-class="part-selected" class="drag-handle"><div _ngcontent-qwq-c89="" class="row part-panel"><div _ngcontent-qwq-c89="" class="field label-width"><span _ngcontent-qwq-c89="" cy-field-label="ETIQUETA" class="label">ETIQUETA</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="ETIQUETA" class="field-height part-material">Boquilla Ganesha - Adaptador</span></div><div _ngcontent-qwq-c89="" class="field material-width"><span _ngcontent-qwq-c89="" cy-field-label="MATERIAL" class="label">MATERIAL</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="MATERIAL" class="field-height part-material">FLEX</span></div><div _ngcontent-qwq-c89="" class="field color-width"><span _ngcontent-qwq-c89="" cy-field-label="COLOR" class="label">COLOR</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="COLOR" class="field-height part-color">NEGRO</span></div><div _ngcontent-qwq-c89="" class="field stock-width"><span _ngcontent-qwq-c89="" cy-field-label="DISPONIBLE" class="label">DISPONIBLE</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="DISPONIBLE" class="field-height part-stockAvailable">10</span></div></div><div _ngcontent-qwq-c89=""><svg _ngcontent-qwq-c89="" viewBox="0 0 100 100" class="corner-border"><path _ngcontent-qwq-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="white"></path></svg><svg _ngcontent-qwq-c89="" viewBox="0 0 100 100" class="corner-clear"><path _ngcontent-qwq-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="#180A16"></path></svg><svg _ngcontent-qwq-c89="" viewBox="0 0 100 100" class="corner-mark"><path _ngcontent-qwq-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="greenyellow"></path></svg></div></div>
# <div _ngcontent-qwq-c89="" draggable="true" id="0078cd61-63bb-4a35-9d66-c4c630b017c3" ng-reflect-drag-data="[object Object]" ng-reflect-drag-scope="PART" ng-reflect-drag-class="part-selected" class="drag-handle"><div _ngcontent-qwq-c89="" class="row part-panel"><div _ngcontent-qwq-c89="" class="field label-width"><span _ngcontent-qwq-c89="" cy-field-label="ETIQUETA" class="label">ETIQUETA</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="ETIQUETA" class="field-height part-material">Boquilla Ganesha - Embocadura</span></div><div _ngcontent-qwq-c89="" class="field material-width"><span _ngcontent-qwq-c89="" cy-field-label="MATERIAL" class="label">MATERIAL</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="MATERIAL" class="field-height part-material">TPU</span></div><div _ngcontent-qwq-c89="" class="field color-width"><span _ngcontent-qwq-c89="" cy-field-label="COLOR" class="label">COLOR</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="COLOR" class="field-height part-color">AZUL</span></div><div _ngcontent-qwq-c89="" class="field stock-width"><span _ngcontent-qwq-c89="" cy-field-label="DISPONIBLE" class="label">DISPONIBLE</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="DISPONIBLE" class="field-height part-stockAvailable">10</span></div></div><div _ngcontent-qwq-c89=""><svg _ngcontent-qwq-c89="" viewBox="0 0 100 100" class="corner-border"><path _ngcontent-qwq-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="white"></path></svg><svg _ngcontent-qwq-c89="" viewBox="0 0 100 100" class="corner-clear"><path _ngcontent-qwq-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="#180A16"></path></svg><svg _ngcontent-qwq-c89="" viewBox="0 0 100 100" class="corner-mark"><path _ngcontent-qwq-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="greenyellow"></path></svg></div></div>
# <div _ngcontent-qwq-c89="" draggable="true" id="cb3f7075-e364-4c41-8d4b-a31a8f2039fe" ng-reflect-drag-data="[object Object]" ng-reflect-drag-scope="PART" ng-reflect-drag-class="part-selected" class="drag-handle"><div _ngcontent-qwq-c89="" class="row part-panel"><div _ngcontent-qwq-c89="" class="field label-width"><span _ngcontent-qwq-c89="" cy-field-label="ETIQUETA" class="label">ETIQUETA</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="ETIQUETA" class="field-height part-material">Boquilla Ganesha - Figura</span></div><div _ngcontent-qwq-c89="" class="field material-width"><span _ngcontent-qwq-c89="" cy-field-label="MATERIAL" class="label">MATERIAL</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="MATERIAL" class="field-height part-material">PLA</span></div><div _ngcontent-qwq-c89="" class="field color-width"><span _ngcontent-qwq-c89="" cy-field-label="COLOR" class="label">COLOR</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="COLOR" class="field-height part-color">ROJO</span></div><div _ngcontent-qwq-c89="" class="field stock-width"><span _ngcontent-qwq-c89="" cy-field-label="DISPONIBLE" class="label">DISPONIBLE</span><br _ngcontent-qwq-c89=""><span _ngcontent-qwq-c89="" cy-field-value="DISPONIBLE" class="field-height part-stockAvailable">0</span></div></div><div _ngcontent-qwq-c89=""><svg _ngcontent-qwq-c89="" viewBox="0 0 100 100" class="corner-border"><path _ngcontent-qwq-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="white"></path></svg><svg _ngcontent-qwq-c89="" viewBox="0 0 100 100" class="corner-clear"><path _ngcontent-qwq-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="#180A16"></path></svg><svg _ngcontent-qwq-c89="" viewBox="0 0 100 100" class="corner-mark"><path _ngcontent-qwq-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="greenyellow"></path></svg></div></div>



# @D3D08 @D3D08.06
# Scenario: [D3D08.06]-If the Save button is active and it is clicked then the Model is persisted at the repository, there is a notification panel and the page is closed returning to the Dashboard.
