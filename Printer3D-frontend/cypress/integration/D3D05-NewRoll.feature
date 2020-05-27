@D3D05
Feature: [D3D05]-Define the requirements fopr the Roll dialog.

    Added to the list of Parts there is a list of Rolls that are also stored at the inventory group endpoint.
    Rolls are required for building and define some of the characteristics of the part.

    Background: Dock Default Configuration setup
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D05 @D3D05.01
    Scenario: [D3D05.01]-If the Feature New Roll receives a click then we should show the New Roll Dialog.
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"
        Then the New Roll dialog opens and blocks the display
