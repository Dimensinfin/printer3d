// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - PAGE ACTIVATION & CONTENTS
Then('the page {string} is activated', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('app-root').find(tag).as('target-page')
        .should('exist')
    // cy.wait(500) // Now there is a delay on the Api Simulation so we need to add  delay
});
Then('the target page has one panel of type {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).should('exist')
});
Then('the target page has one panel of type {string} with variant {string}', function (symbolicName: string, variant: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).get('[ng-reflect-variant="' + variant + '"]').first().should('exist')
});





// - ITEMS
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
Then('the target item has a column named {string} with value {string}', function (columnName: string, fieldValue: string) {
    cy.get('@target-item').within(($item) => {
        cy.get('[cy-name="' + columnName + '"]').contains(fieldValue, { matchCase: false })
    })
});
Given('a hover on the target item', function () {
    cy.get('@target-item').trigger('mouseenter')
});

// - DRAG & DROP
When('the mouse enter the target item', function () {
    cy.get('@target-item').trigger('mouseenter')
});
Given('the drag source the {string} with id {string}', function (renderName: string, recordId: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[translation]> ' + renderName + ' -> ' + tag)
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('drag-source')
        .should('have.prop', 'draggable')
        .should('exist')
});
