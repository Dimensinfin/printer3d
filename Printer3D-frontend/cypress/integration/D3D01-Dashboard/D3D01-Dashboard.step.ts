// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

Given('the Inventory Active Dock Configuration', function () {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
});

When('the application starts the default route is {string}', function (route) {
    console.log('[WHEN] the application starts the default route is the {string}');
    new IsolationService().doLandingPage(); // Load the landing page.
    cy.get('.page-path').contains(route);
});

Then('there is {int} v1-feature-render active', function (activeCount) {
    console.log('[THEN] there is {int} v1-feature-render active');
    cy.get('v1-dock').find('v1-feature-render').find('.active').should('have.length', activeCount)
});

Then('there is a v1-feature active with label {string}', function (label) {
    console.log('[THEN] there is a v1-feature active with label {string}');
    cy.get('v1-dock').find('v1-feature-render').find('.active').contains(label);
});

Then('the Page Display Area is empty', function () {
    console.log('[THEN] the Page Display Area is empty');
});
