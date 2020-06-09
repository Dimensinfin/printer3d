// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { NewPartForm } from '../../support/page-objects/NewPartForm.form';
import { NewCoilForm } from '../../support/page-objects/NewCoilForm.form';

Then('the New Coil dialog opens and blocks the display', function () {
    console.log('[THEN] the New Coil dialog opens and blocks the display');
    cy.get('app-root').get('mat-dialog-container').get('new-coil-dialog').should('have.length', 1)
});

Then('the button {string} has the next properties', function (buttonName: string, dataTable: any) {
    console.log('[THEN] the button {string} has the next properties');
    const form = new NewCoilForm();
    expect(form).to.not.be.null;
    for (let index = 0; index < dataTable.hashes().length; index++) {
        const row = dataTable.hashes()[index];
        form.validateButton(buttonName, row)
    }
});

Then('when all required fields have next values', function (dataTable) {
    console.log('[THEN] the button {string} has the next properties');
    const row = dataTable.hashes()[0];
    const form = new NewCoilForm();
    expect(form).to.not.be.null;
    form.formInput(row);
});

When('there is a click on the {string} button', function (buttonName) {
    console.log('[WHEN] there is a click on the {string} button');
    switch (buttonName) {
        case 'GUARDAR':
            cy.get('new-coil-dialog').find('button').get('#submit-button').should('not.be.disabled')
            cy.get('new-coil-dialog').find('button').get('#submit-button').click();
            break;
        case 'CLOSE':
            cy.get('new-coil-dialog').find('button').get('#cancel-button').should('not.be.disabled')
            cy.get('new-coil-dialog').find('button').get('#cancel-button').click();
            break;
        case 'GUARDARYCONTINUAR':
            cy.get('new-coil-dialog').find('button').get('#repeat-button').should('not.be.disabled')
            cy.get('new-coil-dialog').find('button').get('#repeat-button').click();
            break;
    }
});

Then('the coil is persisted at the backend', function () {
});

Then('the dialog closes', function () {
    console.log('[THEN] the dialog closes');
    cy.get('new-coil-dialog').should('have.length', 0);
});

Then('there is one field called {string} with the content {string}', function (fieldName:string, value:string) {
    cy.get('mat-dialog-container').find('input').get('[name="' + fieldName + '"]').should('have.value', value)
});

Then('form fields have the next values', function (dataTable) {
    const row = dataTable.hashes()[0];
    const form = new NewCoilForm();
    expect(form).to.not.be.null;
    form.validateFields(row);
  });
