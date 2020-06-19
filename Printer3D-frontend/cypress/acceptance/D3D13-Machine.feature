@D3D13
Feature: [D3D13]-[STORY] Steps to define the interactions with a Machine.

    The list of Machines is not editable but by the system administrator. They are rendered on a fixed postion panel to avoid them from scrolling out of view.
    If the Machin is IDLE id does not have any Part on the build slot. Neither it has activation buttons not the timer counter.
    If a Part is dropped on the Machine the actuator buttons are shown and the counter initialized to the Part build time.
    If the Start button is clicked the Machine start the Part build and the timer start to count down.
    If the Clear button is clicked the Part is removed from the build slot.
    If the Machine is RUNNING then the build timer is shown. There is only visible the Cancel button to stop Part building.
    If the Machine is RUNNING and the build remaining time reaches zero then there is a new Button named Complete to complete the build and store the part on the inventory stock.
 
    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D13 @D3D13.01
    Scenario: [D3D13.01]-The list of Machins is a fixed list at the right panel and should show some fields.
        Given there is a click on Feature "/TRABAJOS PND."
        Given the target panel is the panel named "machines"
        Then the target panel has a title "/MAQUINAS"
        Then on the target panel there are one or more "v3-machine"
