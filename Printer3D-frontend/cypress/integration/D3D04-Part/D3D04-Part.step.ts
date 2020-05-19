// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

When('there is a click on Feature {string}', function (featureLabel: string) {
    console.log('[WHEN] there is a click on Feature {string}');
    cy.get('v1-dock')
        .find('v1-feature-render')
        .find('.feature-block')
        .contains(featureLabel, { matchCase: false })
        .closest('.feature-block').click('top');
    // cy.get('@featureSelected').click('top');
});

Then('the dialog New Part opens and blocks the display', function () {
    console.log('[THEN] the dialog New Part opens and blocks the display');
    cy.get('app-root').get('mat-dialog-container').get('new-part-dialog').should('have.length', 1)
});
