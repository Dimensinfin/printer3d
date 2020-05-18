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
