// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

Given('the target item named button {string} is clicked', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
        .click('left')
});
Then('the target item has a named {string} button', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
        .should('exist')
});
Then('the target panel has an input field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldLabel + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('input').parent()
            .find('[ng-reflect-model="' + fieldValue + '"]')
    });
Then('the target item has a actionable image named {string}', function (buttonName: string) {
    cy.get('@target-panel').find('[cy-name="' + buttonName + '"]').should('exist')
});
