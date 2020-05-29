// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

Given('the InventoryPartListPage is activated', function () {
    console.log('[GIVEN] the InventoryPartListPage is activated');
    // cy.visit('/inventory');
    cy.get('app-root').find('inventory-part-list-page').should('have.length', 1);
});

Given('one instance of GridAngular', function () {
    console.log('[GIVEN] one instance of GridAngular');
    cy.get('app-root').find('inventory-part-list-page').find('ag-grid-angular').should('have.length', 1)
});

Given('one or more instances of Row', function () {
    console.log('[GIVEN] one or more instances of Feature');
    cy.get('app-root').find('inventory-part-list-page').find('ag-grid-angular').get('[role="row"]')
});

Then('there is a column with {string} data', function (fieldName) {
    const columnIdentifer = '[col-id="' + fieldName + '"]'
    cy.get('ag-grid-angular').find('.ag-center-cols-container')
        .get('[role="row"]').get(columnIdentifer).should('have.length.greaterThan', 1)
});
