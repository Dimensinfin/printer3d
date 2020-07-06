// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - N E W   I M P L E M E N T A T I O N
Given('the application starts the default route is {string}', function (pageRoute: string) {
    cy.get('app-root').as('target-page').find('.page-path').contains(pageRoute, { matchCase: false })
    cy.get('app-root').find('router-outlet').children().should('have.length', 0)
});
Then('there is a field named {string} with the value {string}', function (fieldName: string, fieldValue: string) {
    cy.get('app-root').find('[cy-name="' + fieldName + '"]')
        .contains(supportService.replaceValueTemplated(fieldValue), { matchCase: false })
});
Then('the Page Display Area is empty', function () {
    cy.get('app-root').find('router-outlet').children().should('have.length', 0)
});
Then('the target panel has a field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-value="' + fieldName + '"]')
            .contains(fieldValue, { matchCase: false })
    });
