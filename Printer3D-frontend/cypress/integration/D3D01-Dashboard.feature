@D3D01
Feature: [D3D01]-When the application starts show the dashboard page with the default feature dock configuration.

    At the application starts the inital page should be the Dashboard. The dashboard page is the core application page where
    there is no page selected and the Page Display are is clean. On the dashboard there are the application title and version
    along with the Dock tool bar with the default out of the box feature configuration.

    @D3D01 @D3D01.01
    Scenario: [D3D01.01]-Check that the landing page after application start is the Dashboard.
        Given the application Printer3DManager
        When the application starts the default route is "/DASHBOARD"

    # @D3D01 @D3D01.02
    # Scenario: [D3D01.02]-The landing page Dashboard shows the title panel.
    #     Given the application Printer3DManager
    #     And the application starts the default route is "/DASHBOARD"
    #     Then there is an element "app-title" with the value "<environment.app-title>"
    #     Then there is an element "app-version" with the value "<environment.app-version>"
    #     Then there is an element "backend-version" with the value "<environment.backend-version>"
    #     Then there is an element "page-path" with the value "/DASHBOARD"

    @D3D01 @D3D01.03
    Scenario: [D3D01.03]-On the dashboard page there is a docker with feature elements.
        Given the application Printer3DManager
        Given one instance of Dock
        Then one or more instances of Feature

    @D3D01 @D3D01.04
    Scenario: [D3D01.04]-On the dashboard page the page display area in empty because there is no feature selected.
        Given the application Printer3DManager
        Then the Page Display Area is empty
