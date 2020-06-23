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
    @D3D08 @D3D08.01
    Scenario: [D3D08.01]-When the Feature is clicked the display open on the New Model page.
        Given one instance of Dock
        When the Feature with label "/NUEVO MODELO" is clicked the destination is the Page "NewModelPage"

    @D3D08 @D3D08.02
    Scenario: [D3D08.02]-The New Model page has two panels. One contains the Parts (active) that can be used in the MOdel and the other panel contains the model being created.
        Given one instance of Dock
        When the Feature with label "/NUEVO MODELO" is clicked the destination is the Page "NewModelPage"
        Then the page "NewModelPage" is activated
        And the page "NewModelPage" has 2 panels
        And the target page has one panel of type "available-parts" with variant "-NEW-MODEL-"
        And the target page has one panel of type "new-model"
