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

Then('the first NodeContainer contains a Part Container Render', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('v1-part-container').should('exist');
});

Then('on the v1-part-container component there is a field named {string} with class {string}', function (fieldName: string, fieldClass: string) {
    const fieldClassIdentifier = '.' + fieldClass
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').first().find('v1-part-container')
        .find('.field').as('target-partcontainer-field')
        .find(fieldClassIdentifier).should('exist')
    cy.get('@target-partcontainer-field').find('.label').contains(fieldName)
    cy.get('@target-partcontainer-field').find(fieldClassIdentifier).should('exist')
});

Then('there is a loading panel displaying {string}', function (downloadMessage: string) {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('.index-loading').contains(downloadMessage)
});

Then('on the v1-part-container component there is a right arrow', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('.arrow-box')
        .find('.right-arrow').should('exist')
});

When('the right arrow is clicked', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('.arrow-box')
        .find('.right-arrow').first().click()
});

Then('the container expands and there are one or more v1-part nodes', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('v1-part').should('exist');
});

Then('on the v1-part component there is a field named {string} with class {string}', function (fieldName: string, fieldClass: string) {
    const fieldClassIdentifier = '.' + fieldClass
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('v1-part').first()
        .find('.field').as('target-part-field')
        .find(fieldClassIdentifier).should('exist')
    cy.get('@target-part-field').find('.label').contains(fieldName)
    cy.get('@target-part-field').find(fieldClassIdentifier).should('exist')
});

Then('active Parts show a green corner', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').as('target-node-active')
        .find('v1-part')
        .find('.field')
        .find('.part-active').contains('ACTIVA')
    cy.get('@target-node-active').find('.corner-bottom').should('have.class', 'active')
});

Then('inactive Part show an orange corner', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').as('target-node-inactive')
        .find('v1-part')
        .find('.field')
        .find('.part-active').contains('FUERA PROD.')
        .parents('node-container').as('target-node-inactive')
    cy.get('@target-node-inactive').find('.corner-bottom').should('not.have.class', 'active')
});
