// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

Then('the V2CoilListPage is activated', function () {
    cy.get('app-root').find('v2-coil-list-page').as('target-page')
        .should('exist')
});
Then('the V2CoilListPage has {int} panels', function (panelCount: number) {
    cy.get('app-root').find('v2-coil-list-page').find('.row')
        .children()
        .should('have.length', panelCount)
});
Then('the panel on page V2CoilListPage has variant {string}', function (variant: string) {
    cy.get('app-root').find('v2-coil-list-page').find('.row')
        .find('[ng-reflect-variant="' + variant + '"]')
        .should('exist')
});
