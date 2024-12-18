@F3D23
Feature: [F3D23]-validate the acction and contents of the new Dialog for Patch Notes.

    [STORY] Create a Patch notes dialog with the changes since the last release. The activation is tied to the header version.

    Background: Application landing page
        Given the application Printer3DManager

    @F3D23.01
    Scenario: [F3D23.01]-If the front end version literal is clicked the Patch Notes dialog opens.
        When the button with name "app-version" is clicked
        Then the "Patch Notes" dialog opens and blocks the display

    @F3D23.02
    Scenario: [F3D23.02]-Once open if the Close button is clicked the dialog closes.
        When the button with name "app-version" is clicked
        Then the "Patch Notes" dialog opens and blocks the display
        When the button with name "cancel-button" is clicked
        Then the dialog "Patch Notes" closes
