// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - S T E P   M A C R O S
Given('a job on a Machine', function () {
    // Given the target is the panel of type "jobs-list"
    let tag = supportService.translateTag('jobs-list') // Do name replacement
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
    // Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
    tag = supportService.translateTag('job') // Do name replacement
    let recordId = "1e238019-f9aa-45ee-a765-e5df22b7219c"
    cy.get('@target').find(tag).find('[id="' + recordId + '"]').as('drag-source')
        .should('have.prop', 'draggable')
        .should('exist')
    // Given the target is the panel of type "machines"
    tag = supportService.translateTag('machines') // Do name replacement
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
    // Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
    tag = supportService.translateTag('machine') // Do name replacement
    let machineId = "e18aa442-19cd-4b08-8ed0-9f1917821fac"
    cy.get('@target-panel').find(tag).find('[id="' + machineId + '"]').as('target')
        .should('exist')
    // When the drag source is dragged to the drop destination "dropJobs"
    const dropDestination = "dropJobs"
    cy.get('@drag-source').scrollIntoView().trigger('dragstart')
    cy.get('@target').find('[cy-name="' + dropDestination + '"]').trigger('drop')
})

// - T A R G E T   C O N T E N T S
Then('the panel {string} has no {string}', function (targetName: string, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target').within(($item) => {
        cy.get(tag).should('not.exist')
    })
})
Then('the target has a drop place named {string}', function (dropName: string) {
    cy.get('@target').find('[cy-name="' + dropName + '"]').should('exist')
})
Then('the build-countdown-timer item has started countdown', function () {
    cy.wait(2000)
    cy.get('@target-item').find('v1-build-countdown-timer').should('exist')
})
Then('the Job is started and sent to the background', function () {
    cy.log('>[the Job is started and sent to the background]')
})

// - M O V E M E N T
When('the mouse exits the target', function () {
    cy.get('@target').find('[cy-focus="mouseleave"]').trigger('mouseleave')
})
