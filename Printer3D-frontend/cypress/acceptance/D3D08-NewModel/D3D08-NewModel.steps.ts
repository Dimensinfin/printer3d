// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - This should be moved to Common
Then('the target page has one panel of type {string} with variant {string}', function (symbolicName: string, variant: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).get('[ng-reflect-variant="' + variant + '"]').first().should('exist')
});
Then('the target page has one panel of type {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).should('exist')
});
When('the Feature with label {string} is clicked the destination is the Page {string}', function (label: string, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('v1-dock')
        .find('v2-feature-render')
        .contains(label, { matchCase: false }).parent()
        .click('center');
    cy.wait(500)
    cy.get('app-root').find(tag).should('exist')
});
When('the Feature with label {string} is clicked the destination is the Dialog {string}', function (label: string, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('v1-dock')
        .find('v2-feature-render')
        .contains(label, { matchCase: false }).parent()
        .click('center');
    cy.get('app-root')
        .get(tag).should('exist').as('target-dialog')
});
