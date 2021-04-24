// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - L A T E S T   I M P L E M E N T A T I O N
Then('the Request is updated on the backend', function () {
    cy.log('>[the Request is updated on the backend]')
})
Then('the active page is set to Dasboard', function () {
    cy.visit('/')
})
Given('the target is the panel of name {string}', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]')
        .as('target-panel').as('target')
})

// - T A R G E T   C O N T E N T S
Then('target has a panel labeled {string} named {string}',
    function (fieldLabel: string, fieldName: string) {
        cy.get('@target').get('[cy-name="' + fieldName + '"]').as('target-panel')
        cy.get('@target').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
    });
// - D I A L O G
Then('the dialog closes', function () {
    cy.get('@target-dialog').should('not.exist');
});
