// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - L A T E S T   I M P L E M E N T A T I O N
Then('the Request is updated on the backend', function () {
    cy.log('>[the Request is updated on the backend]')
})
Given('the target is the panel of name {string}', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]')
        .as('target-panel').as('target')
})
// - D I A L O G
Then('the dialog closes', function () {
    cy.get('@target-dialog').should('not.exist')
})
