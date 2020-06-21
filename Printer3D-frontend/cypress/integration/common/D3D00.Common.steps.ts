// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';
// - DOMAIN
import { V1PagePath } from '../../support/page-objects/V1PagePath.panel';
import { V1Dock } from '../../support/page-objects/V1Dock.panel';
import { V1Feature } from '../../support/page-objects/V1Feature.panel';
import { GridRow } from '../../support/page-objects/GridRow.panel';

const TITLE_VALIDATION = '3DPrinterManagement - UI';
const supportService = new SupportService();

// - N E W E S T   I M P L E M E N T A T I O N
When('the page {string} is activated', function (name: string) {
    const tag = supportService.translateTag(name) // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('app-root').find(tag).as('target-page')
        .should('exist')
});
Then('the page {string} has {int} panels', function (name: string, panelCount: number) {
    const tag = supportService.translateTag(name) // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('app-root').find(tag).find('.row').first()
        .children()
        .should('have.length', panelCount)
});
Given('the target panel has one or more {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('@target-panel').find(tag).should('have.length.greaterThan', 0)
});
Given('the target item the {string} with id {string}', function (renderName: string, recordId: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target-item')
        .should('exist')
});









// - A C C E P T A N C E
Given('the target panel is the panel named {string}', function (elementName: string) {
    cy.get('app-root').find('[cy-name="' + elementName + '"]').as('target-panel')
        .should('exist')
});
Then('on the target panel there is one {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType).should('have.length', 1)
});
Then('the target panel has a title {string}', function (title: string) {
    cy.get('@target-panel').find('.title').contains(title, { matchCase: false })
});
Then('on the target panel there are one {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType).should('have.length', 1)
});
Then('on the target panel there are one or more {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType).should('have.length.greaterThan', 0)
});
Then('the target item has a field labeled {string} with value {string}', function (fieldLabel: string, fieldValue: string) {
    cy.get('@target-item').within(($item) => {
        cy.get('[cy-field-label="' + fieldLabel + '"]').contains(fieldLabel, { matchCase: false })
    })
    cy.get('@target-item').within(($item) => {
        cy.get('.label').contains(fieldLabel, { matchCase: false }).parent()
            .find('[cy-field-value="' + fieldLabel + '"]').contains(fieldValue, { matchCase: false })
    })
});
Then('the target Machine has no instances of {string}', function (panelName: string) {
    cy.get('@target-machine').find(panelName).should('not.exist')
});
Then('the target panel button with name {string} has a label {string} and is {string}', function (
    buttonName: string, buttonLabel: string, buttonState: string) {
    if (buttonState == 'disabled')
        cy.get('@target-panel').get('[disabled]')
            .get('[cy-name="' + buttonName + '"]').contains(buttonLabel, { matchCase: false })
    else
        cy.get('@target-panel').get('[cy-name="' + buttonName + '"]')
            .contains(buttonLabel, { matchCase: false })
});
Then('the target item button with name {string} has a label {string} and is {string}', function (
    buttonName: string, buttonLabel: string, buttonState: string) {
    if (buttonState == 'disabled')
        cy.get('@target-item').get('[disabled]')
            .get('[cy-name="' + buttonName + '"]').contains(buttonLabel, { matchCase: false })
    else
        cy.get('@target-item').get('[cy-name="' + buttonName + '"]')
            .contains(buttonLabel, { matchCase: false })
});



// - R E V I E W
Given('the application Printer3DManager', function () {
    cy.viewport(1400, 900)
    new IsolationService().doLandingPage(); // Load the landing page.
    cy.title().should('eq', TITLE_VALIDATION);
});
Given('one instance of Dock', function () {
    cy.get('app-root').find('v1-dock').should('have.length', 1)
});
When('the Feature with label {string} is clicked the destination is the Page {string}', function (label: string, destination: string) {
    cy.get('v1-dock')
        .find('v2-feature-render')
        .contains(label, { matchCase: false }).parent()
        .click('center');
    cy.wait(1200)
    cy.get('app-root').find(destination).should('exist')
});
When('the Feature with label {string} is clicked the destination is the Dialog {string}', function (label: string, destination: string) {
    cy.get('v1-dock')
        .find('v2-feature-render')
        .contains(label, { matchCase: false }).parent()
        .click('center');
    cy.get('app-root')
        .get(destination).should('exist').as('target-dialog')
});
When('there is a click on Feature {string}', function (featureLabel: string) {
    cy.get('v1-dock')
        .find('v2-feature-render')
        .contains(featureLabel, { matchCase: false }).parent().parent().as('target-feature')
        .click('center');
});
// - C O M M O N   T O   S O M E   F E A T U R E S
Given('the target panel is the panel with variant {string}', function (variant: string) {
    cy.get('@target-page').find('.row')
        .find('[ng-reflect-variant="' + variant + '"]')
        .as('target-panel')
});
When('there is a click on the {string} button of target dialog', function (buttonId: string) {
    cy.get('@target-dialog').find('[id="' + buttonId + '"]').click('center')
});
Then('there is a Notification panel', function () {
    cy.get('#toast-container').should('exist')
});
Then('there are no Features active', function () {
    cy.get('v1-dock')
        .find('v2-feature-render').within(($panel) => {
            cy.get('.corner-mark').should('have.length', 0)
        });
});
Then('there is a Feature with label {string}', function (label: string) {
    cy.get('v1-dock').find('v2-feature-render').find('.feature-label').contains(label, { matchCase: false })
});
Then('the target panel has one or more {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType)
        .should('have.length.greaterThan', 0)
});




// Given('the DashboardPage is activated', function () {
//     console.log('[GIVEN] the DashboardPage is activated');
//     new IsolationService().doLandingPage(); // Start the application to a known point.
// });
// - O B S O L E T E
Given('one instance of PagePath', function () {
    console.log('[GIVEN] one instance of PageTitle');
    const pageTitle: V1PagePath = new V1PagePath();
    expect(pageTitle).to.not.be.null;
    cy.get('app-root').find('v1-page-path-panel').should('have.length', 1)
});

// Given('one instance of Dock', function () {
//     console.log('[GIVEN] one instance of Dock');
//     const dock: V1Dock = new V1Dock();
//     expect(dock).to.not.be.null;
//     cy.get('app-root').find('v1-dock').should('have.length', 1)
// });

// Given('one or more instances of Feature', function () {
//     console.log('[GIVEN] one or more instances of Feature');
//     const feature: V1Feature = new V1Feature();
//     expect(feature).to.not.be.null;
//     cy.get('app-root').find('v1-feature-render').should('have.length.gt', 0)
// });

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

Then('there is a dialog title saying {string}', function (dialogLabel) {
    console.log('[THEN] there is a dialog title saying {string}');
    cy.get('mat-dialog-container').find('header').find('div').contains(dialogLabel, { matchCase: false })
});

Then('there is one field called {string} with the label {string}', function (fieldId, label) {
    console.log('[THEN] there is one field called {string} with the label {string}');
    cy.get('mat-dialog-container').find('tr').find(fieldId).should('have.length', 1)
    cy.get('mat-dialog-container').find('tr').find('label').contains(label, { matchCase: false })
});

When('the target form is the New Part dialog', function () {
    cy.get('app-root').get('new-part-dialog').as('target-form')
});

Then('on target input form there is a field named {string} with name {string} and content up to {string} characters',
    function (fieldLabel: string, fieldName: string, maxLength: number) {
        cy.get('@target-form').find('.column').as('target-field')
        cy.get('@target-field').find('label').contains(fieldLabel)
        // Generate content
        const service = new IsolationService()
        const testContent = service.generateRandomString(maxLength + 1)
        cy.get('@target-field').find('[name="' + fieldName + '"]')
            .clear().type(testContent)
        // Check that field trimms input
        cy.get('@target-field').find('[name="' + fieldName + '"]').then(($field) => {
            const value = $field.text()
            cy.log('[on target input form there is a field named {string} with name {string} and content up to {string} characters]> Field text: ' + value)
            // expect(value.length).to.equal(maxLength)
        })
    });

Given('the InventoryPartListPage at route {string}', function (route: string) {
    cy.visit(route)
});
