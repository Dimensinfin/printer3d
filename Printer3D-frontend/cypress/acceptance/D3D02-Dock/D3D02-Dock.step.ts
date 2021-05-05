// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - F E A T U R E S
Then('there is a Feature with label {string}', function (label: string) {
    cy.get('v1-dock').find('v2-feature').find('.feature-label').contains(label, { matchCase: false })
});
Then('there are {int} Features enabled', function (count: number) {
    cy.get('v1-dock').find('v2-feature').within(($panel) => {
        cy.get('.feature').not('.disabled').should('have.length', count)
    });
});
Then('the Feature with label {string} opens a Dialog', function (label: string) {
    cy.get('v1-dock').find('v2-feature')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').parent().find('.blue-mark').should('exist')
});
Then('the Feature with label {string} opens a Page', function (label: string) {
    cy.get('v1-dock').find('v2-feature')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').should('not.exist')
});
Then('the Feature with label {string} opens a DropPage', function (label: string) {
    cy.get('v1-dock').find('v2-feature')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').parent().find('.blueviolet-mark').should('exist')
});
Then('the target Feature {string} changes to state {string}', function (featureLabel: string, state: string) {
    if (state == 'active')
        cy.get('@target-feature').within(($panel) => {
            cy.get('.corner-mark').should('exist')
        });
    if (state == 'inactive')
        cy.get('@target-feature').within(($panel) => {
            cy.get('.corner-mark').should('not.exist')
        });
});

// -  D I A L O G   B U T T O N S
When('there is a click on the {string} button of target dialog', function (buttonName: string) {
    cy.get('@target-dialog').find('[cy-name="' + buttonName + '"]').click('center')
});
// - D I A L O G   C O N T E N T S
Then('the dialog has the title {string}', function (title: string) {
    cy.get('@target-dialog').find('.panel-title').contains(title, { matchCase: false })
});
