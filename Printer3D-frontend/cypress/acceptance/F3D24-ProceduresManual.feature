@F3D24
Feature: [F3D24]-Open the Manual.

    [STORY] Add a new Operation Procedures Manual connected to the application name click.

    @F3D24.01
    Scenario: [F3D24.01]-If the application name is clicked the Operation Procedures Manual open in a new tab.
        Given the intercepted application Printer3DManager
        When the button with name "app-title" is clicked
        # Then the Procedures Manual open on a new tab
