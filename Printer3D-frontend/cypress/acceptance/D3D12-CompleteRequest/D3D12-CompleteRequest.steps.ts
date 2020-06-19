// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';

Given('in the target item there is no {string} button', function (buttonName: string) {
    cy.get('@target-item').get('[cy-name="' + buttonName + '"]')
        .should('not.exist')
});
Given('in the target item there is a {string} button', function (buttonName: string) {
    cy.get('@target-item').get('[cy-name="' + buttonName + '"]')
        .should('exist')
});
