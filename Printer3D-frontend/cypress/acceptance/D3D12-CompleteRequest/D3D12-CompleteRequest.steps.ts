// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

Given('in the target item there is no {string} button', function (buttonName: string) {
    cy.get('@target-item').get('[cy-name="' + buttonName + '"]')
        .should('not.exist')
});
Given('in the target item there is a {string} button', function (buttonName: string) {
    cy.get('@target-item').get('[cy-name="' + buttonName + '"]')
        .should('exist')
});
When('there is a click on the target panel {string} button', function (buttonName: string) {
    cy.get('@target-panel').get('[cy-name="' + buttonName + '"]')
        .click('center')
});
Then('the Request is updated on the backend', function () {
    cy.log('>[the Request is updated on the backend]')
});
Then('the active page is set to Dasboard', function () {
    cy.visit('/')
});
Then('on the target panel there is one {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).should('have.length', 1)
});
