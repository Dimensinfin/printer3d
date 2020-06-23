// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

Then('the New Request dialog opens and blocks the display', function () {
    cy.get('app-root').get('mat-dialog-container').get('v1-new-request-dialog').should('exist')
});
Then('the V1NewRequestPage is activated', function () {
    cy.get('app-root').find('v1-new-request-page').as('target-page')
        .should('exist')
});
Then('the V1NewRequestPage has {int} panels', function (panelCount: number) {
    cy.get('app-root').find('v1-new-request-page').find('.row').first()
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
When('the target Part is dragged to the drop panel {string}', function (drop: string) {
    cy.get('@target-part').trigger('dragstart')
    cy.get('@target-panel').find('[name="' + drop + '"]').trigger('drop')
});
Then('the target panel has {string} buttons', function (buttonCount: string) {
    cy.get('@target-panel').find('button').should('have.length', buttonCount)
});
Given('the form {string} be the target form', function (formName: string) {
    cy.get(formName).find('form').as('target-form')
});

When('{string} is set on the target form field {string}', function (value: string, fieldName: string) {
    cy.get('@target-form').find('[name="' + fieldName + '"]').clear().type(value)
});
When('the target panel button with name {string} is clicked', function (buttonName: string) {
    cy.get('@target-panel').find('[cy-name="' + buttonName + '"]')
        .scrollIntoView().click('left', { force: true })
});
Then('the Request is persisted at the backend', function () {
    cy.log('The Request is being persisted at the backend.')
});
Then('the active page is set to Dasboard', function () {
    cy.visit('/')
});
Given('an amplified viewport', function () {
    cy.viewport(1000, 800)
});

// - B E S T   P R A C T I C E   I M P L E M E N T A T I O N
Then('the target page has one panel of type {string} with variant {string}', function (renderName: string, variant: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-page').find(tag).get('[ng-reflect-variant="' + variant + '"]').first().should('exist')
});

Then('the target page has one panel of type {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-page').find(tag).should('exist')
});
Given('the target panel is the panel of type {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[tag replacement]> ' + renderName + ' -> ' + tag)
    cy.get('@target-page').find(tag)
        .as('target-panel')
});
Then('the target panel has one or more {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag)
        .should('have.length.greaterThan', 0)
});
Given('the drag source the {string} with id {string}', function (renderName: string, recordId: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[translation]> ' + renderName + ' -> ' + tag)
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('drag-source')
        .should('exist')
});
When('the drag source is dragged to the drop destination {string}', function (dropDestination: string) {
    cy.get('@drag-source').trigger('dragstart')
    cy.get('@target-panel').find('[cy-name="' + dropDestination + '"]').trigger('drop')
});
Then('the target panel has {int} {string}', function (count: number, renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[translation]> ' + renderName + ' -> ' + tag)
    cy.get('@target-panel').find(tag).should('have.length', count)
});
Then('the target item has a field named {string} with value {string}', function (fieldName: string, fieldValue: string) {
    cy.get('@target-panel').within(($item) => {
        cy.get('[cy-name="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
    })
});
When('the target item Remove button is clicked', function () {
    cy.get('@target-item').find('[cy-name="remove-button"]')
        .click()
});
Then('the target panel has no {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[translation]> ' + renderName + ' -> ' + tag)
    cy.get('@target-panel').find(tag).should('not.exist')
});

// - This should be moved to Common
When('the Feature with label {string} is clicked the destination is the Page {string}', function (label: string, tagName: string) {
    const tag = supportService.translateTag(tagName) // Do name replacement
    cy.get('v1-dock')
        .find('v2-feature-render')
        .contains(label, { matchCase: false }).parent()
        .click('center');
    cy.wait(1200)
    cy.get('app-root').find(tag).should('exist')
});
When('the Feature with label {string} is clicked the destination is the Dialog {string}', function (label: string, tagName: string) {
    const tag = supportService.translateTag(tagName) // Do name replacement
    cy.get('v1-dock')
        .find('v2-feature-render')
        .contains(label, { matchCase: false }).parent()
        .click('center');
    cy.get('app-root')
        .get(tag).should('exist').as('target-dialog')
});
