// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
// - DOMAIN
import { V1PagePath } from '../../support/page-objects/V1PagePath.panel';
import { V1Dock } from '../../support/page-objects/V1Dock.panel';
import { V1Feature } from '../../support/page-objects/V1Feature.panel';

Given('one instance of PagePath', function () {
    console.log('[GIVEN] one instance of PageTitle');
    const pageTitle: V1PagePath = new V1PagePath();
    expect(pageTitle).to.not.be.null;
    cy.get('app-root').find('v1-page-path-panel').should('have.length', 1)
});

Given('one instance of Dock', function () {
    console.log('[GIVEN] one instance of Dock');
    const dock: V1Dock = new V1Dock();
    expect(dock).to.not.be.null;
    cy.get('app-root').find('v1-dock').should('have.length', 1)
});

Given('one or more instances of Feature', function () {
    console.log('[GIVEN] one or more instances of Feature');
    const feature: V1Feature = new V1Feature();
    expect(feature).to.not.be.null;
    cy.get('app-root').find('v1-feature-render').should('have.length.gt', 0)
});

Then('there is a {string} with the next fields', (panelType, dataTable) => {
    cy.log('[THEN] there is a {string} with the next fields');
    cy.log('[THEN] panelType=' + panelType);
    const row = dataTable.hashes()[0];
    const service = new IsolationService();
    switch (panelType) {
        case 'app-title':
            cy.get('.app-title').contains(service.decodeDataTableRow(row, panelType));
            break;
        case 'app-version':
            cy.get('.app-version').contains(service.decodeDataTableRow(row, panelType));
            break;
        case 'page-name':
            cy.get('.page-name').contains(service.decodeDataTableRow(row, panelType));
            break;
        case 'v1-page-path-panel':
            const pagePath: V1PagePath = new V1PagePath();
            expect(pagePath).to.not.be.null;
            pagePath.validatePanel(row);
            break;
    }
});

Then('there is a {string} at index {string} with the next fields', function (panelType, index, dataTable) {
    cy.log('[THEN] there is a {string} at index {string} with the next fields');
    cy.log('[THEN] panelType=' + panelType);
    cy.log('[THEN] index=' + index);
    const row = dataTable.hashes()[index + 0];
    expect(row).to.not.be.empty;
    switch (panelType) {
        case 'v1-feature-render':
            const feature: V1Feature = new V1Feature();
            expect(feature).to.not.be.null;
            feature.validatePanel(row);
            break;
    }
});

// Then('there is a {string} at index {string} with the next fields', function (string, string2, dataTable) {
//     // Write code here that turns the phrase above into concrete actions
//     return 'pending';
//   });
