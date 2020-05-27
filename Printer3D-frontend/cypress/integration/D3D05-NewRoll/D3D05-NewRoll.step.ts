// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { NewPartForm } from '../../support/page-objects/NewPartForm.form';

Then('the New Roll dialog opens and blocks the display', function () {
    console.log('[THEN] the New Roll dialog opens and blocks the display');
    cy.get('app-root').get('mat-dialog-container').get('new-roll-dialog').should('have.length', 1)
});
