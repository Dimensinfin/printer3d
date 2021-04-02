@D3D01
Feature: [D3D01]-When the application starts show the dashboard page with the header set of components.

    At the application starts the inital page should be the Dashboard.
    The dashboard page is the core application page where there is no page selected and the Page Display is clean.
    On the dashboard there are the application title and version along with the Dock tool bar with the feature configuration.
    On the header there are also a work load indicator that shown the number of minutes that the machines should run to complete the pending jobs.

    @D3D01.01
    Scenario: [D3D01.01]-Check that the landing page after application start is the Dashboard.
        Given the application Printer3DManager
        When the application starts the default route is "/DASHBOARD"

    @D3D01.02
    Scenario: [D3D01.02]-The landing page Dashboard shows the title panel.
        Given the application Printer3DManager
        When the application starts the default route is "/DASHBOARD"
        # - Check the header application contents
        Then there is a field named "app-title" with the value "TETSUO3D - UI"
        Then there is a field named "app-version" with the value "<environment.app-version>"
        Then there is a field named "backend-version" with the value "<0.0.0 backend"
        Then there is a field named "page-path" with the value "/DASHBOARD"

    @D3D01.03
    Scenario: [D3D01.03]-On the dashboard page there is a dock and a work load
        Given the application Printer3DManager
        When the application starts the default route is "/DASHBOARD"
        Then the target page has one panel of type "dock"
        Then the target page has one panel of type "work-load"


    @D3D01.04
    Scenario: [D3D01.04]-On the dashboard page the page display area in empty because there is no feature selected.
        Given the application Printer3DManager
        When the application starts the default route is "/DASHBOARD"
        Then the Page Display Area is empty

    @D3D01.05
    Scenario: [D3D01.05]-The work load should report the total number of minutes of pending jobs.
        Given the application Printer3DManager
        When the application starts the default route is "/DASHBOARD"
        Given the target is the panel of type "work-load"
        Then the target panel has a field named "work-load" with label "TOTAL MINUTOS" and contents "17H0M"
