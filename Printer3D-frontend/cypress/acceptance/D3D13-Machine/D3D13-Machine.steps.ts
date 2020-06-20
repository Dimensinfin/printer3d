// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

Then('the target machine has no Job assigned', function () {
    cy.get('@target-item').get('v1-part')
        .should('not.exist')
});
Given('the target job the {string} with id {string}', function (renderName: string, recordId: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target-item').as('target-job')
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
Then('the target machine has one Job assigned with id {string}', function (recordId: string) {
    cy.get('@target-machine').find('v1-pending-job').get('[id="' + recordId + '"]')
        .should('exist')
});
When('there is a click on the target item {string} button', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
        .click('center')
});
Then('the Job is started and sent to the background', function () {
    cy.log('>[the Job is started and sent to the background]')
});
Then('the {string} item has a value of {string}', function (renderName:string,timerValue: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-item').find(tag).first().contains(timerValue, { matchCase: false })
});
// TODO - replace by a timed action so the new countdown value can be verified.
Then('the build-countdown-timer item has started countdown', function () {
    cy.wait(2000)
    cy.get('@target-item').find('v1-build-countdown-timer-panel').should('exist')
});
Then('the target Machine has {string} instances of {string}', function (elementCount: string, elementType: string) {
    cy.get('@target-machine').find(elementType).should('have.length', elementCount)
});
