// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';

Then('the V1OpenRequestsPage is activated', function () {
    cy.get('app-root').find('v1-open-requests-page').as('target-page')
        .should('exist')
});
Then('the V1OpenRequestsPage has {int} panels', function (panelCount: number) {
    cy.get('app-root').find('v1-open-requests-page').find('.row').first()
        .children()
        .should('have.length', panelCount)
});
Given('the target panel is the panel named {string}', function (elementName: string) {
    cy.get('app-root').find('[cy-name="' + elementName + '"]').as('target-panel')
        .should('exist')
});
Then('the target panel has a title {string}', function (title: string) {
    cy.get('@target-panel').find('.title').contains(title, { matchCase: false })
});
Then('on the target panel there are one or more {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType).should('have.length.greaterThan', 0)
});
Given('the target item the {string} with id {string}', function (itemType: string, recordId: string) {
    cy.get('@target-panel').find(itemType).find('[id="' + recordId + '"]').as('target-item')
        .should('exist')
});
Then('the target item has a field labeled {string} with value {string}', function (fieldLabel: string, fieldValue: string) {
    cy.get('@target-item').within(($item) => {
        cy.get('[cy-field-label="' + fieldLabel + '"]').contains(fieldLabel, { matchCase: false })
    })
    cy.get('@target-item').within(($item) => {
        cy.get('.label').contains(fieldLabel, { matchCase: false }).parent()
            .find('[cy-field-value="' + fieldLabel + '"]').contains(fieldValue, { matchCase: false })
    })
});
Then('the target item has a mark {string}', function (markname: string) {
    cy.get('@target-item').parent().find('[cy-field-mark="REQUEST"]').get('.' + markname).should('exist')
});

Given('a hover on the target item', function () {
    cy.get('@target-item').trigger('mouseenter')
});
Then('on the target panel there is one {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType).should('have.length', 1)
});
