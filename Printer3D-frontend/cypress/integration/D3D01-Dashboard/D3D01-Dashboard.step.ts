// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

const TITLE_VALIDATION = 'Printer3DFrontend';
// const DASHBOARD_PAGE_NAME = '/Dashboard';

Given('the Inventory Active Dock Configuration', function () {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
  });

Given('the application Printer3DManager', function () {
    console.log('[GIVEN] the application Printer3DManager');
    new IsolationService().doLandingPage(); // Load the landing page.
    cy.title().should('eq', TITLE_VALIDATION);
});

When('the application starts the default route is {string}', function (route) {
    console.log('[WHEN] the application starts the default route is the {string}');
    new IsolationService().doLandingPage(); // Load the landing page.
    cy.get('.page-name').contains(route);
  });


Given('the DashboardPage is activated', function () {
    console.log('[GIVEN] the DashboardPage is activated');
    new IsolationService().doLandingPage(); // Start the application to a known point.
});

Then('there is {int} v1-feature-render active', function (activeCount) {
    console.log('[THEN] there is {int} v1-feature-render active');
    cy.get('v1-dock').find('v1-feature-render').find('.active').should('have.length', activeCount)
});

Then('there is a v1-feature active with label {string}', function (label) {
    console.log('[THEN] there is a v1-feature active with label {string}');
    cy.get('v1-dock').find('v1-feature-render').find('.active').contains(label);
});
