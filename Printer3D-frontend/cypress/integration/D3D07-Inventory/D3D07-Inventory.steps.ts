// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

Then('the V2InventoryPartListPage is activated', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').should('exist');
});

Then('one instance of ViewerPanel', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel').should('exist');
});

Then('one or more instances of NodeContainer', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel').find('node-container').should('exist');
});

Then('the first NodeContainer contains a Part Render', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').first().find('v1-part-render').should('exist');
});

Then('on the v1-part-render component there is a field named {string}', function (string) {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
  });
