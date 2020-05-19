// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

Given('the InventoryPartListPage is activated', function () {
    console.log('[GIVEN] the InventoryPartListPage is activated');
    cy.visit('/inventory');
    cy.get('app-root').find('inventory-part-list-page').should('have.length', 1);
});

When('there is a click on Feature {string}', function (featureLabel: string) {
    console.log('[WHEN] there is a click on Feature {string}');
    cy.get('v1-dock').find('v1-feature-render').find('.feature-block').contains(featureLabel).click()
});

Then('the dialog New Part opens and blocks the display', function () {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
});
