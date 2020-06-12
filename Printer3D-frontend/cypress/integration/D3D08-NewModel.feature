@D3D08
Feature: [D3D08]-Define the requirements for the New Model dialog interactions

    Models are structures that aggregate Parts. The Model definition has a set of fields tht can be set when the model is
    created but at that moment it is not possible to set the list of Parts that compose the model since for that action 
    the user should not be inside a Dialog..

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D04 @D3D08.01
    Scenario: [D3D08.01]-When there is a click on the New Model Feature then the dialog opens and blocks any other edit until the dialog is dismissed.
        Given one instance of Dock
        When there is a click on Feature "/NUEVO MODELO"
        Then the New Model dialog opens and blocks the display
