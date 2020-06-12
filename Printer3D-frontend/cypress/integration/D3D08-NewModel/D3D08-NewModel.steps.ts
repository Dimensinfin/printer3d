// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

Then('the New Model dialog opens and blocks the display', function () {
    cy.get('app-root').get('mat-dialog-container').get('new-model-dialog').should('exist')
});
