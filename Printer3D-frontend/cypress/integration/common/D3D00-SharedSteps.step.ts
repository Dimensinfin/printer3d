// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - DOMAIN
import { V1PagePath } from '../../support/page-objects/V1PagePath.panel';

Given('one instance of PagePath', function () {
    console.log('[GIVEN] one instance of PageTitle');
    const pageTitle: V1PagePath = new V1PagePath();
    expect(pageTitle).to.not.be.null;
    cy.get('app-root').find('v1-page-path-panel').should('have.length', 1)
});

Then('there is a {string} with the next fields', (panelType, dataTable) => {
    cy.log('[THEN] there is a {string} with the next fields');
    cy.log('[THEN] panelType=' + panelType);
    const row = dataTable.hashes()[0];
    switch (panelType) {
        case 'v1-page-path-panel':
            const pagePath: V1PagePath = new V1PagePath();
            expect(pagePath).to.not.be.null;
            pagePath.validatePanel(row);
            break;
    }
});
