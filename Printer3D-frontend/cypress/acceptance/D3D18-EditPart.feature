@D3D18
Feature: [D3D18]-[STORY] Parts have fields editable both at the generic definition level and at the instance definition level

    There is a button on the Inventory table to togle the 'Edit in Place' for a Part or Part Group. A Part group is just an artificial structure to
    define a set of Parts that have some set of common field values.
    There is another button to edit the properties of a single Part instance.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D18.01
    Scenario: [D3D18.01]-There is a new field on the Part that is the weight of plastic that needs to be used to build an instance.
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated
        Given the target panel is the panel of type "catalog"
        Given the target item the "part-container" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
        Then the target item has a field named "label" with label "ETIQUETA" and value "PLATAFORMA SLOT 1/32 - Guarda Tornillos"
        And the target item has a field named "description" with label "DESCRIPCION" and value "Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot"
        And the target item has a field named "buildTime" with label "TIEMPO" and value "45 min."
        And the target item has a field named "weight" with label "PLASTICO" and value "6 gr."

    @D3D18.02
    Scenario: [D3D18.02]-The Part Container group now has an edit button that toggles the editing of some properties for a set of Parts. All the parts that share the label belong to the same Part Group and are edited at the same time.
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated
        Given the target panel is the panel of type "catalog"
        Given the target item the "part-container" with id "52372bd9-76a3-4f65-926e-a50a896961c0"
        Then the target item has a actionable image named "edit-button"
        When the target item actionable image "edit-button" is clicked

        # Then new the target item text field named "description" is editable
        Then the field named "buildTime" of the target item is editable
        Then the target item input field named "weight" is editable
        Then the target item input field named "imagePath" is editable
        Then the target item input field named "modelPath" is editable
        Then the target item has a actionable image named "update-button"
        And the target panel has an input field named "description" with label "DESCRIPCION" and contents "Base para la plataforma de slot cars."
        And the target panel has an input field named "buildTime" with label "TIEMPO" and contents "30 min."
        And the target panel has an input field named "weight" with label "PESO" and contents "4 gr."
        And the target panel has an input field named "imagePath" with label "IMAGEN" and contents "https://ibb.co/3dGbsRh"
        And the target panel has an input field named "modelPath" with label "FICHERO IMPR." and contents "pieza3.sft"

    @D3D18.03
    Scenario: [D3D18.03]-Validate the contents of a Part Container. Now there are new fields and the Image and Model File should also be visible if not empty.
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated
        Given the target panel is the panel of type "catalog"
        Given the target item the "part-container" with id "52372bd9-76a3-4f65-926e-a50a896961c0"
        Then the target item has a field named "label" with label "ETIQUETA" and value "PLATAFORMA SLOT 1/32 - Base"
        And the target item has a field named "description" with label "DESCRIPCION" and value "Base para la plataforma de slot cars."
        And the target item has a field named "buildTime" with label "TIEMPO" and value "30 min."
        And the target item has a field named "weight" with label "PLASTICO" and value "4 gr."
        And the target item has a field named "imagePath" with label "IMAGEN" and value "https://ibb.co/3dGbsRh"
        And the target item has a field named "modelPath" with label "FICHERO IMPR." and value "pieza3.sft"

# Then the target item has a named "edit-button" button
# When the target Part Duplicate button is clicked

# <div _ngcontent-kfo-c90="" class="row part-container-panel" id="52372bd9-76a3-4f65-926e-a50a896961c0"><div _ngcontent-kfo-c90="" class="field label-width"><span _ngcontent-kfo-c90="" cy-field-label="label" class="label">ETIQUETA</span><br _ngcontent-kfo-c90=""><span _ngcontent-kfo-c90="" cy-field-value="label" class="partcontainer-label">PLATAFORMA SLOT 1/32 - Base</span></div><div _ngcontent-kfo-c90="" class="field"><span _ngcontent-kfo-c90="" cy-field-label="description" class="label">DESCRIPCION</span><br _ngcontent-kfo-c90=""><span _ngcontent-kfo-c90="" cy-field-value="description" class="partcontainer-description">Base para la plataforma de slot cars.</span></div><div _ngcontent-kfo-c90="" class="field buildTime-width"><span _ngcontent-kfo-c90="" cy-field-label="buildTime" class="label">TIEMPO</span><br _ngcontent-kfo-c90=""><span _ngcontent-kfo-c90="" cy-field-value="buildTime" class="partcontainer-buildTime">30 min.</span></div><div _ngcontent-kfo-c90="" class="field weight-width"><span _ngcontent-kfo-c90="" cy-field-label="weight" class="label">PLASTICO</span><br _ngcontent-kfo-c90=""><span _ngcontent-kfo-c90="" cy-field-value="weight" class="partcontainer-buildTime">4 gr.</span></div><div _ngcontent-kfo-c90="" class="column"><div _ngcontent-kfo-c90="" cy-name="edit-button" class="field edit-width"><svg _ngcontent-kfo-c90="" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="36px" height="36px" class="edit-icon"><path _ngcontent-kfo-c90="" d="M0 0h24v24H0V0z" fill="none"></path><path _ngcontent-kfo-c90="" d="M17.63 7H6.37C3.96 7 2 9.24 2 12s1.96 5 4.37 5h11.26c2.41 0 4.37-2.24 4.37-5s-1.96-5-4.37-5zm0 8H6.37C5.09 15 4 13.63 4 12s1.09-3 2.37-3h11.26C18.91 9 20 10.37 20 12s-1.09 3-2.37 3zM7.24 13.06l-1.87-1.87-.7.7 2.57 2.57 4.22-4.22-.7-.7z"></path></svg></div><div _ngcontent-kfo-c90="" cy-name="expand-button" class="arrow-box"><svg _ngcontent-kfo-c90="" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="white" width="36px" height="36px" ng-reflect-ng-class="[object Object]" class="arrow-expanded"><path _ngcontent-kfo-c90="" d="M0 0h24v24H0z" fill="none"></path><path _ngcontent-kfo-c90="" d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path></svg></div><!--bindings={
#   "ng-reflect-ng-if": "true"
# }--></div></div>


# <div _ngcontent-iji-c90="" class="row part-container-panel" id="9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"><div _ngcontent-iji-c90="" class="field label-width"><span _ngcontent-iji-c90="" cy-field-label="label" class="label">ETIQUETA</span><br _ngcontent-iji-c90=""><span _ngcontent-iji-c90="" cy-field-value="label" class="partcontainer-label">PLATAFORMA SLOT 1/32 - Guarda Tornillos</span></div><div _ngcontent-iji-c90="" class="field"><span _ngcontent-iji-c90="" cy-field-label="description" class="label">DESCRIPCION</span><br _ngcontent-iji-c90=""><span _ngcontent-iji-c90="" cy-field-value="description" class="partcontainer-description">Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot</span></div><div _ngcontent-iji-c90="" class="field buildTime-width"><span _ngcontent-iji-c90="" cy-field-label="buildTime" class="label">TIEMPO</span><br _ngcontent-iji-c90=""><span _ngcontent-iji-c90="" cy-field-value="buildTime" class="partcontainer-buildTime">45 min.</span></div><div _ngcontent-iji-c90="" class="column"><div _ngcontent-iji-c90="" cy-name="edit-button" class="field edit-width"><svg _ngcontent-iji-c90="" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="36px" height="36px" class="edit-icon"><path _ngcontent-iji-c90="" d="M0 0h24v24H0V0z" fill="none"></path><path _ngcontent-iji-c90="" d="M17.63 7H6.37C3.96 7 2 9.24 2 12s1.96 5 4.37 5h11.26c2.41 0 4.37-2.24 4.37-5s-1.96-5-4.37-5zm0 8H6.37C5.09 15 4 13.63 4 12s1.09-3 2.37-3h11.26C18.91 9 20 10.37 20 12s-1.09 3-2.37 3zM7.24 13.06l-1.87-1.87-.7.7 2.57 2.57 4.22-4.22-.7-.7z"></path></svg></div><div _ngcontent-iji-c90="" cy-name="expand-button" class="arrow-box"><svg _ngcontent-iji-c90="" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="white" width="36px" height="36px" ng-reflect-ng-class="[object Object]"><path _ngcontent-iji-c90="" d="M0 0h24v24H0z" fill="none"></path><path _ngcontent-iji-c90="" d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path></svg></div><!--bindings={
# "ng-reflect-ng-if": "true"
# }--></div></div>
