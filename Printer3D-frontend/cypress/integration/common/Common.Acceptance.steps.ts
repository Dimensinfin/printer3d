// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - FEATURE SELECTION
When('there is a click on Feature {string}', function (featureLabel: string) {
    cy.get('v1-dock')
        .find('v2-feature-render')
        .contains(featureLabel, { matchCase: false }).parent().parent().as('target-feature')
        .click('center');
});
// - PAGE ACTIVATION & CONTENTS
Then('the page {string} is activated', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('app-root').find(tag).as('target-page')
        .should('exist')
});
Then('the target page has one panel of type {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).should('exist')
});
Then('the target page has one panel of type {string} with variant {string}', function (symbolicName: string, variant: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).get('[ng-reflect-variant="' + variant + '"]').first().should('exist')
});

// - TARGET SELECTION
Given('the target panel is the panel of type {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[tag replacement]> ' + renderName + ' -> ' + tag)
    cy.get('@target-page').find(tag)
        .as('target-panel')
});

// - PANEL CONTENTS
Then('the target panel has a title {string}', function (title: string) {
    cy.get('@target-panel').find('.title').contains(title, { matchCase: false })
});
Then('the target panel has {int} {string}', function (count: number, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target-panel').find(tag).should('have.length', count)
});
Then('the target panel has a field named {string} with label {string} and empty',
    function (fieldName: string, fieldLabel: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldLabel + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-value="' + fieldLabel + '"]')
            .should('be.empty')
    });
Then('the target panel has a field named {string} with label {string} and not empty',
    function (fieldName: string, fieldLabel: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldLabel + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-value="' + fieldLabel + '"]')
            .should('not.be.empty')
    });
Then('the target panel has a field named {string} with label {string} and and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldLabel + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-value="' + fieldLabel + '"]')
            .contains(fieldValue, { matchCase: false })
    });
// - FORMS
Then('the target panel has an input field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldLabel + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('input')
            .should('have.text', fieldValue)
    });
Then('the target panel has an input field named {string} with label {string} and not empty',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldLabel + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('input')
            .should('not.be.empty')
    });
Then('the target panel has an input field named {string} with label {string} and empty',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldLabel + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('input')
            .should('be.empty')
    });
Then('the target panel input field named {string} is {string}', function (fieldName: string, state: string) {
    if (state == 'invalid') cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        .find('input').parent().within(($field) => {
            cy.get('.ng-invalid').should('exist')
        })
});






Then('the target item has a field named {string} with label {string} and value {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-item').within(($item) => {
            cy.get('[cy-field-label="' + fieldName + '"]').contains(fieldLabel, { matchCase: false })
        })
        cy.get('@target-item').within(($item) => {
            cy.get('.label').contains(fieldLabel, { matchCase: false }).parent()
                .find('[cy-field-value="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
        })
    });
