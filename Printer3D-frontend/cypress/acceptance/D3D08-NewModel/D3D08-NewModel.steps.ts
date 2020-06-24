// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - This should be moved to Common
Then('the target page has one panel of type {string} with variant {string}', function (symbolicName: string, variant: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).get('[ng-reflect-variant="' + variant + '"]').first().should('exist')
});
Then('the target page has one panel of type {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).should('exist')
});
When('the Feature with label {string} is clicked the destination is the Page {string}', function (label: string, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('v1-dock')
        .find('v2-feature-render')
        .contains(label, { matchCase: false }).parent()
        .click('center');
    cy.wait(500)
    cy.get('app-root').find(tag).should('exist')
});
When('the Feature with label {string} is clicked the destination is the Dialog {string}', function (label: string, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('v1-dock')
        .find('v2-feature-render')
        .contains(label, { matchCase: false }).parent()
        .click('center');
    cy.get('app-root')
        .get(tag).should('exist').as('target-dialog')
});
Then('the target panel has {int} {string}', function (count: number, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target-panel').find(tag).should('have.length', count)
});
Then('the target item has a field named {string} with value {string}', function (fieldName: string, fieldValue: string) {
    cy.get('@target-panel').within(($item) => {
        cy.get('[cy-name="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
    })
});
Then('the target item has a field named {string} with label {string} and value {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-item').within(($item) => {
            cy.get('[cy-field-label="' + fieldName + '"]').contains(fieldLabel, { matchCase: false })
        })
        cy.get('@target-item').within(($item) => {
            cy.get('.label').contains(fieldLabel, { matchCase: false }).parent()
                .find('[cy-field-value="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
        })
    });
Given('the target panel is the panel of type {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[tag replacement]> ' + renderName + ' -> ' + tag)
    cy.get('@target-page').find(tag)
        .as('target-panel')
});
Then('the target panel has an input field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldLabel + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('input')
            .should('have.text', fieldValue)
    });
Then('the target panel input field named {string} is {string}', function (fieldName: string, state: string) {
    if (state == 'invalid') cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        .find('input').parent().within(($field) => {
            cy.get('.ng-invalid').should('exist')
        })
});
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
Given('the drag source the {string} with id {string}', function (renderName: string, recordId: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[translation]> ' + renderName + ' -> ' + tag)
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('drag-source')
        .should('exist')
});
When('the drag source is dragged to the drop destination {string}', function (dropDestination: string) {
    cy.get('@drag-source').trigger('dragstart')
    cy.get('@target-panel').find('[cy-name="' + dropDestination + '"]').trigger('drop')
});
Then('the target item has a actionable image named {string}', function (buttonName: string) {
    cy.get('@target-panel').find('[cy-name="' + buttonName + '"]').should('exist')
});


// - ON CONSTRUCTION
