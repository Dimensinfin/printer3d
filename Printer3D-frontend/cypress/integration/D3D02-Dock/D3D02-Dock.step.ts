// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

// const TITLE_VALIDATION = 'Printer3DFrontend';
const INVENTORY_PART_LIST_PAGE_NAME = '/Dashboard';

When('there is a click on v1-feature-render with index {int}', function (featureIndex) {
    console.log('[WHEN] there is a click on v1-feature-render with index {int}');
    cy.get('v1-dock').find('v1-feature-render').each(($el, index, $list) => {
        if (index === featureIndex) {
            cy.wrap($el).click()
        }
    });
});

Then('the target page is InventoryPartListPage', function () {
    console.log('[THEN] the target page is InventoryPartListPage');
    cy.get('app-dashboard-page').find('.page-path').contains(INVENTORY_PART_LIST_PAGE_NAME);
  });
