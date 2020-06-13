// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

Then('the New Request dialog opens and blocks the display', function () {
    cy.get('app-root').get('mat-dialog-container').get('v1-new-request-dialog').should('exist')
});
Then('the V1NewRequestPage is activated', function () {
    cy.get('app-root').find('v1-new-request-page').as('target-page')
        .should('exist')
});
Then('the V1NewRequestPage has {int} panels', function (panelCount: number) {
    cy.get('app-root').find('v1-new-request-page').find('.row')
        .children()
        .should('have.length', panelCount)
});
Then('the left panel on page V1NewRequestPage is a {string}', function (panelType: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row').children().eq(0)
        .find(panelType)
        .should('exist')
});
Then('the left panel on page V1NewRequestPage has variant {string}', function (variant: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row')
        .find('[ng-reflect-variant="' + variant + '"]')
        .should('exist')
});

Then('the right panel on page V1NewRequestPage is a {string}', function (panelType: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row').children().eq(1)
        .find(panelType)
        .should('exist')
});
Then('the target panel has a {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType)
        .should('exist')
});
Then('the target panel has one or more {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType)
        .should('have.length.greaterThan', 0)
});
Given('the target Part is one labeled {string}', function (labelContent: string) {
    cy.get('app-root').get('v1-available-parts-panel').find('v1-part').find('[name="label"]')
        .contains(labelContent, { matchCase: false }).first().parent().parent()
        .as('target-part')
});
Given('on the target Part there is a field named {string} with field name {string}', function (fieldLabel: string, fieldName: string) {
    cy.get('@target-part').find('.label').contains(fieldLabel, { matchCase: false })
        .parent()
        .find('[name="' + fieldName + '"]').should('exist')
});
Given('the target panel is the panel of type {string}', function (panelType: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row')
        .find(panelType)
        .as('target-panel')
});
Then('the target panel has a title {string}', function (title: string) {
    cy.get('@target-panel').find('.title').contains(title, { matchCase: false })
});
Then('the target panel has a field labeled {string} named {string} and not empty', function (fieldLabel: string, fieldName: string) {
    cy.get('@target-panel').find('.field').find('.label').contains(fieldLabel, { matchCase: false })
        .parent().as('target-field')
    cy.get('@target-field').find('[name="' + fieldName + '"]').should('exist').should('not.be.empty')
});
Then('the target panel has a field labeled {string} named {string} and empty', function (fieldLabel: string, fieldName: string) {
    cy.get('@target-panel').find('.field').find('.label').contains(fieldLabel, { matchCase: false })
        .parent().as('target-field')
    cy.get('@target-field').find('[name="' + fieldName + '"]').should('exist').should('be.empty')
});
Then('the target panel has a panel labeled {string} named {string} and with {string} elements', function (
    fieldLabel: string, fieldName: string, elementCount: number) {
    cy.get('@target-panel').find('.field').find('.label').contains(fieldLabel, { matchCase: false })
        .parent().as('target-field')
    cy.get('@target-field').find('[name="' + fieldName + '"]').should('exist')
        .find('v1-part').should('have.length', elementCount)
});
Then('the target part is draggable with the contraint {string}', function (scope: string) {
    cy.get('@target-part').parent().parent().find('[draggable="true"]')
        .should('exist')
    cy.get('@target-part').parent().parent().find('[ng-reflect-drag-scope="' + scope + '"]')
        .should('exist')
});
Then('the target panel has a place for drop with the contraint {string}', function (scope: string) {
    cy.get('@target-panel').find('div').find('[droppable]').parent().find('[ng-reflect-drop-scope="PART"]')
        .should('exist')
});
