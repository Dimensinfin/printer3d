
@D3D04
Feature: [D3D04]-Define the requirements for the Part interactions like creation and update.

    If the user interacts with Inventory features that affect the Parts there should be requirements to control the
    presentation, contents and interaction flow. Parts cannot be eliminated but some of their fields are editable
    once the part is constructed.

    Background: Dock Default Configuration setup
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
            | id                                   | label             | material | colorCode | buildTime | cost | price | stockLevel | active |
            | 9812a107-6c09-4b27-bdea-fbbbd35d12d4 | Pieza de Prueba 1 | PLA      | BLANCO    | 35        | 0.8  | 3     | 3          | true   |
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
            | id                                   | label             | material | colorCode | buildTime | cost | price | stockLevel | active |
            | 9812a107-6c09-4b27-bdea-fbbbd35d12d4 | Pieza de Prueba 1 | PLA      | BLANCO    | 35        | 0.8  | 3     | 3          | true   |
        When there is a click on the "GUARDAR" button
        Then the part is persisted at the backend
        And the dialog closes

    @D3D04 @D3D04.09
    Scenario: [D3D04.09]-If the New Part Cancel button is clicked then dialog is closed.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And when all required fields have next values
            | id                                   | label             | material | colorCode | buildTime | cost | price | stockLevel | active |
            | 9812a107-6c09-4b27-bdea-fbbbd35d12d4 | Pieza de Prueba 1 | PLA      | BLANCO    | 35        | 0.8  | 3     | 3          | true   |
        When there is a click on the "CLOSE" button
        Then the dialog closes

    @D3D04 @D3D04.10
    Scenario: [D3D04.10]-If the New Part Save and Continue button is clicked then part is saved but the dialog is kept open and the contents updated.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And when all required fields have next values
            | id                                   | label             | material | colorCode | buildTime | cost | price | stockLevel | active |
            | 9812a107-6c09-4b27-bdea-fbbbd35d12d4 | Pieza de Prueba 1 | PLA      | BLANCO    | 35        | 0.8  | 3     | 3          | true   |
        And there is one instance of form with the next contents
            | fieldName | value  | class      |
            | colorCode | BLANCO | noteditble |
        When there is a click on the "GUARDAR-NUEVO" button
        Then the part is persisted at the backend
        And when all required fields have next values
            | id                                   | material | colorCode |
            | 43f2b077-3f25-4a04-ac81-67acb805a59b | PLA      | BLANCO    |
        And there is one instance of form with the next contents
            | fieldName | value | class      |
            | colorCode | GREEN | noteditble |

    @D3D04 @D3D04.11
    Scenario: [D3D04.11] The dialog Material field should be populated with the list of material types from the backend list.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And there is a call to the backend to get the Finishings
        And the Material dropdown has 2 elements
        And form fields have the next values
            | material |
            | PLA      |
        And the Color dropdown has 3 values

    @D3D04 @D3D04.12
    Scenario: [D3D04.12]-The New Part dialog has an specific set of fields.
        Given one instance of Dock
        When there is a click on Feature "/NUEVA PIEZA"
        Then the New Part dialog opens and blocks the display
        And there is a dialog title saying "/INVENTARIO/NUEVA PIEZA"
        And there is one field called "#part-id" with the label "ID"
        And there is one field called "#part-label" with the label "Etiqueta"
        And there is one field called "#part-description" with the label "Descripción"
