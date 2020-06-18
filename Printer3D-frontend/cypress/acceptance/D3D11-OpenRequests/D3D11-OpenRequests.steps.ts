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
Then('the target panel has a title {string}', function (title:string) {
    cy.get('@target-panel').find('.title').contains(title, { matchCase: false })
  });
Then('on the target panel there are one or more {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType).should('have.length.greaterThan', 0)
});
