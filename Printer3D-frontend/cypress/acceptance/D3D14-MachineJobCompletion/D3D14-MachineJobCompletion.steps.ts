// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

Given('a timed application Printer3DManager', function () {
    cy.clock()
    cy.visit('/')
});
Given('the target job the {string} with id {string}', function (renderName: string, recordId: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').first().as('target-item').as('target-job')
        .should('exist')
});
Given('the target machine the {string} with id {string}', function (renderName: string, recordId: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target-item').as('target-machine')
        .should('exist')
});
When('the target Job is dragged and dropped on the target Machine', function () {
    cy.get('@target-job').trigger('dragstart')
    cy.get('@target-machine').find('[cy-name="drop-job"]').trigger('drop')
});
When('there is a click on the target item {string} button', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
        .click('center')
});
Then('advance time {string} minutes', function (minutes: number) {
    cy.tick(minutes * 60 * 1000)
});
Then('the target Machine has {string} instances of {string}', function (elementCount: string, elementType: string) {
    cy.get('@target-machine').find(elementType).should('have.length', elementCount)
});
