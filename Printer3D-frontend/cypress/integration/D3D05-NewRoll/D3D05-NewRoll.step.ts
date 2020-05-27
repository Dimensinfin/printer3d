// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { NewPartForm } from '../../support/page-objects/NewPartForm.form';
import { NewRollForm } from '../../support/page-objects/NewRollForm.form';

Then('the New Roll dialog opens and blocks the display', function () {
    console.log('[THEN] the New Roll dialog opens and blocks the display');
    cy.get('app-root').get('mat-dialog-container').get('new-roll-dialog').should('have.length', 1)
});

Then('the button {string} has the next properties', function (buttonName: string, dataTable: any) {
    console.log('[THEN] the button {string} has the next properties');
    const form = new NewRollForm();
    expect(form).to.not.be.null;
    for (let index = 0; index < dataTable.hashes().length; index++) {
        const row = dataTable.hashes()[index];
        form.validateButton(buttonName, row)
    }
});

Then('when all required fields have next values', function (dataTable) {
    console.log('[THEN] the button {string} has the next properties');
    const row = dataTable.hashes()[0];
    const form = new NewRollForm();
    expect(form).to.not.be.null;
    form.formInput(row);
});
