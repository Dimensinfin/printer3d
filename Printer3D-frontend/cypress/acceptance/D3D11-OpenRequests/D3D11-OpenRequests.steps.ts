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
// Then('the target panel has a title {string}', function (title: string) {
//     cy.get('@target-panel').find('.title').contains(title, { matchCase: false })
// });
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
