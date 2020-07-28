// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - C O L U M N S
Then('the target panel has a drop place named {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-panel').find(tag).should('exist')
});
Then('{string}" is set on the target panel input field named {string}', function (stringValue: string, fieldName: string) {
    cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear().type(stringValue)
});
When('{string} is set on the target panel input field named {string}', function (stringValue: string, fieldName: string) {
    cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear().type(stringValue)
});
// Given('the drag source the {string} with id {string}', function (renderName: string, recordId: string) {
//     const tag = supportService.translateTag(renderName) // Do name replacement
//     cy.log('>[translation]> ' + renderName + ' -> ' + tag)
//     cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('drag-source')
//         .should('exist')
// });
When('the drag source is dragged to the drop destination {string}', function (dropDestination: string) {
    cy.get('@drag-source').trigger('dragstart')
    cy.get('@target-panel').find('[cy-name="' + dropDestination + '"]').trigger('drop')
});
When('the target item has actionable image named {string} is clicked', function (buttonName: string) {
    cy.get('@target-panel').find('[cy-name="' + buttonName + '"]').should('exist')
        .click()
});
Then('the target panel has no {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[translation]> ' + renderName + ' -> ' + tag)
    cy.get('@target-panel').find(tag).should('not.exist')
});
// When('the target panel button with name {string} is clicked', function (buttonName: string) {
//     cy.get('@target-panel').find('[cy-name="' + buttonName + '"]')
//         .scrollIntoView().click('left', { force: true })
// });
Then('the Model is persisted at the backend', function () {
    cy.log('The Model is being persisted at the backend.')
});
Then('the active page is set to Dashboard', function () {
    cy.visit('/')
});
