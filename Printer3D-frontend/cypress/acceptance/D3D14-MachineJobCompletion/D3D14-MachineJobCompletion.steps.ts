// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - T I M E   C O N T R O L
Given('a timed application Printer3DManager', function () {
    cy.clock()
    cy.viewport(1400, 900)
    cy.visit('/')
});
Then('advance time {string} minutes', function (minutes: number) {
    cy.tick(minutes * 60 * 1000)
});
// - M O V E M E N T
When('the mouse exits the target', function () {
    cy.get('@target').find('[cy-focus="mouseleave"]').trigger('mouseleave')
});

// - S T E P   M A C R O S
Given('a timed application Printer3DManager', function () {
    // Given the target is the panel of type "jobs-list"
    // Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
    // # - Drag a part to the drop contents
    // Given the target is the panel of type "machines"
    // Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
    // When the drag source is dragged to the drop destination "dropJobs"
    // Then the button with name "start-button" has a label "Comenzar" and is "enabled"
});
