// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { contains } from 'cypress/types/jquery';

Then('the V2CoilListPage is activated', function () {
    cy.get('app-root').find('v2-coil-list-page').as('target-page')
        .should('exist')
});
Then('the V2CoilListPage has {string} panels', function (panelCount: number) {
    cy.get('app-root').find('v2-coil-list-page')
        .children()
        .should('have.length', panelCount)
});
Then('the panel on page V2CoilListPage has variant {string}', function (variant: string) {
    cy.get('app-root').find('v2-coil-list-page').find('.row')
        .find('[ng-reflect-variant="' + variant + '"]')
        .should('exist')
});
Given('the target Coil is one of color {string}', function (color: string) {
    cy.get('v1-coil').contains(color, { matchCase: false })
        .parent().parent().as('target-coil')
});
Given('on the target Coil there is a field labeled {string} with field name {string} and the value {string}', function (
    fieldLabel: string, fieldName: string, value: string) {
    cy.get('@target-coil')
        .find('.label').contains(fieldLabel, { matchCase: false })
    cy.get('@target-coil')
        .find('[name="' + fieldName + '"]').contains(value, { matchCase: false })
});
