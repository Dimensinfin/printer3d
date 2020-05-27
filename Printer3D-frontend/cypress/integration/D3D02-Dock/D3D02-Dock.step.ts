// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

const INVENTORY_PART_LIST_PAGE_NAME = '/Inventory/Part List';

When('there is a click on v1-feature-render with index {int}', function (featureIndex) {
    console.log('[WHEN] there is a click on v1-feature-render with index {int}');
    cy.get('v1-dock').find('v1-feature-render').get('.feature-block').each(($el, index, $list) => {
        if (index === featureIndex) {
            cy.wrap($el).click()
        }
    });
});

Then('the target page is InventoryPartListPage', function () {
    console.log('[THEN] the target page is InventoryPartListPage');
    cy.get('app-root').find('inventory-part-list-page').should('have.length', 1);
});

Then('there are {int} Features', function (featureCount) {
    console.log('[THEN] there are {int} Features');
    cy.get('app-root').find('v1-feature-render').should('have.length', featureCount)
});

Then('there is a v1-feature-render with label {string}', function (label) {
    console.log('[THEN] there is a v1-feature-render with label {string}');
    cy.get('app-root').find('v1-dock').find('v1-feature-render').contains(label)
});
