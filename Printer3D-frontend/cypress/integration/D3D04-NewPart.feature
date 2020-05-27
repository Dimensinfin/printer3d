
@D3D04
Feature: [D3D04]-Define the requirements for the Part interactions like creation and update.

    If the user interacts with Inventory features that affect the Parts there should be requirements to control the
    presentation, contents and interaction flow. Parts cannot be eliminated but some of their fields are editable
    once the part is constructed.

    Background: Dock Default Configuration setup
        Given the Default Dock Configuration
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D04 @D3D04.01
    Scenario: [D3D04.01]-If the Feature New Part received a click then we should show the New Part Dialog.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display

    @D3D04 @D3D04.02
    Scenario: [D3D04.02]-A new part dialog should have the unique identifier not editable.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And there is one instance of form with the next contents
            | fieldName | value               | class      |
            | id        | <constant.anyvalue> | noteditble |

    # @D3D04 @D3D04.03
    # Scenario: [D3D04.03]-On the dialog the color code field is a drop selector with a set of values.
    #     Given one instance of Dock
    #     When there is a click on Feature "/NUEVA PIEZA"
    #     Then the New Part dialog opens and blocks the display
    #     And there is a drop field called "colorCode" with the next list of values
    #         | values        |
    #         | WHITE         |
    #         | GREEN         |
    #         | GREEN_TRANSP  |
    #         | RED           |
    #         | LIGHT_BLUE    |
    #         | PINK_TRANSP   |
    #         | ORANGE_TRANSP |

    @D3D04 @D3D04.04
    Scenario: [D3D04.04]-A new part dialog should have the fields empty.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And the NewPart dialog input fields should be empty

    @D3D04 @D3D04.05
    Scenario: [D3D04.05]-A new part dialog should have some fields with default values
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And there is one instance of form with the next contents
            | fieldName      | value               | class      |
            | id             | <constant.anyvalue> | noteditble |
            | colorCode      | UNDEFINED           | noteditble |
            | stockLevel     | 1                   | noteditble |
            | stockAvailable | 0                   | noteditble |

    @D3D04 @D3D04.06
    Scenario: [D3D04.06]-A new part dialog should have two buttons. One to save the new part and another to cancel the operation.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And the button "SAVE" has the next properties
            | label         | state    |
            | Guardar Datos | disabled |
        And the button "CANCEL" has the next properties
            | label          | state   |
            | Go Back/Cancel | enabled |

    @D3D04 @D3D04.07
    Scenario: [D3D04.07]-When all the required fields are filled with the right values the Save button activates.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And when all required fields have next values
            | id                                   | label             | colorCode | buildTime | cost | price | stockLevel | active |
            | 9812a107-6c09-4b27-bdea-fbbbd35d12d4 | Pieza de Prueba 1 | WHITE     | 35        | 0.8  | 3     | 3          | true   |
        And the button "SAVE" has the next properties
            | label         | state   |
            | Guardar Datos | enabled |
        And the button "CANCEL" has the next properties
            | label          | state   |
            | Go Back/Cancel | enabled |

    @D3D04 @D3D04.08
    Scenario: [D3D04.08]-If the New Part Save button is clicked then the part is stored at the repository and a notification is thrown.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And when all required fields have next values
            | id                                   | label             | colorCode | buildTime | cost | price | stockLevel | active |
            | 9812a107-6c09-4b27-bdea-fbbbd35d12d4 | Pieza de Prueba 1 | WHITE     | 35        | 0.8  | 3     | 3          | true   |
        When there is a click on the "GUARDAR" button
        Then the part is persisted at the backend
        And the dialog closes

    @D3D04 @D3D04.09
    Scenario: [D3D04.09]-If the New Part Cancel button is clicked then dialog is closed.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And when all required fields have next values
            | id                                   | label             | colorCode | buildTime | cost | price | stockLevel | active |
            | 9812a107-6c09-4b27-bdea-fbbbd35d12d4 | Pieza de Prueba 1 | WHITE     | 35        | 0.8  | 3     | 3          | true   |
        When there is a click on the "CLOSE" button
        Then the dialog closes

    @D3D04 @D3D04.10
    Scenario: [D3D04.10]-If the New Part Save and Continue button is clicked then part is saved but the dialog is kept open and the contents updated.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And when all required fields have next values
            | id                                   | label             | colorCode | buildTime | cost | price | stockLevel | active |
            | 9812a107-6c09-4b27-bdea-fbbbd35d12d4 | Pieza de Prueba 1 | WHITE     | 35        | 0.8  | 3     | 3          | true   |
        And there is one instance of form with the next contents
            | fieldName | value | class      |
            | colorCode | GREEN | noteditble |
        When there is a click on the "GUARDAR-NUEVO" button
        Then the part is persisted at the backend
        And when all required fields have next values
            | id                                   | colorCode |
            | 43f2b077-3f25-4a04-ac81-67acb805a59b | GREEN     |
        And there is one instance of form with the next contents
            | fieldName | value | class      |
            | colorCode | GREEN | noteditble |

#     # - E X C E P T I O N S
#     @D3D04 @D3D04.E01
#     Scenario: [D3D04.E01]-If there is an exception saving the Part data at the repository then there is a notification and
# the part dialog is not exited.
