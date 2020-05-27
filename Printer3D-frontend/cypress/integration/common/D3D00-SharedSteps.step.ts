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
import { GridRow } from '../../support/page-objects/GridRow.panel';

const TITLE_VALIDATION = '3DPrinterManagement - UI';

Given('the Default Dock Configuration', function () {
    console.log('[GIVEN] the Default Dock Configuration');
    cy.clearLocalStorage()
});

Given('the application Printer3DManager', function () {
    console.log('[GIVEN] the application Printer3DManager');
    new IsolationService().doLandingPage(); // Load the landing page.
    cy.title().should('eq', TITLE_VALIDATION);
});

Given('the DashboardPage is activated', function () {
    console.log('[GIVEN] the DashboardPage is activated');
    new IsolationService().doLandingPage(); // Start the application to a known point.
});

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

Given('one instance of GridAngular', function () {
    console.log('[GIVEN] one instance of GridAngular');
    cy.get('app-root').find('inventory-part-list-page').find('ag-grid-angular').should('have.length', 1)
});

Given('one or more instances of Feature', function () {
    console.log('[GIVEN] one or more instances of Feature');
    const feature: V1Feature = new V1Feature();
    expect(feature).to.not.be.null;
    cy.get('app-root').find('v1-feature-render').should('have.length.gt', 0)
});

Given('one or more instances of Row', function () {
    console.log('[GIVEN] one or more instances of Feature');
    // const feature: V1Feature = new V1Feature();
    // expect(feature).to.not.be.null;
    cy.get('app-root').find('inventory-part-list-page').find('ag-grid-angular').get('[role="row"]')
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

Then('there is a {string} at index {string} with the next fields', function (panelType: string, index: string, dataTable) {
    cy.log('[THEN] there is a {string} at index {string} with the next fields');
    cy.log('[THEN] panelType=' + panelType);
    const row = dataTable.hashes()[0];
    expect(row).to.not.be.empty;
    switch (panelType) {
        case 'v1-feature-render':
            const feature: V1Feature = new V1Feature();
            expect(feature)
            feature.validatePanel(row);
            break;
        case 'grid-row':
            const gridrow: GridRow = new GridRow(+index);
            expect(gridrow).to.not.be.null;
            gridrow.validatePanel(row);
            break;
    }
});

When('there is a click on Feature {string}', function (featureLabel: string) {
    console.log('[WHEN] there is a click on Feature {string}');
    cy.get('v1-dock')
        .find('v1-feature-render')
        .find('.feature-block')
        .contains(featureLabel, { matchCase: false })
        .closest('.feature-block')
        .click('top');
});

Then('there is a dialog title saying {string}', function (dialogLabel) {
    console.log('[THEN] there is a dialog title saying {string}');
    cy.get('mat-dialog-container').find('header').find('div').contains(dialogLabel, {matchCase: false})
});

Then('there is one field called {string} with the label {string}', function (fieldId, label) {
    console.log('[THEN] there is one field called {string} with the label {string}');
    cy.get('mat-dialog-container').find('tr').find(fieldId).should('have.length', 1)
    cy.get('mat-dialog-container').find('tr').find('label').contains(label, {matchCase: false})
});
