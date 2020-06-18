// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { NewPartForm } from '../../support/page-objects/NewPartForm.form';

Then('the New Part dialog opens and blocks the display', function () {
    cy.get('app-root').get('mat-dialog-container').get('new-part-dialog').should('have.length', 1)
});

// Then('there is one instance of form with the next contents', function (dataTable) {
//     console.log('[THEN] there is one instance of form with the next contents');
//     // const row = dataTable.hashes()[0];
//     const form = new NewPartForm();
//     expect(form).to.not.be.null;
//     form.validateHasContents(dataTable);
// });

Then('the NewPart dialog input fields should be empty', function () {
    console.log('[THEN] the NewPart dialog input fields should be empty');
    const form = new NewPartForm();
    expect(form).to.not.be.null;
    form.fieldIsEmpty('label');
    form.fieldIsEmpty('description');
    form.fieldIsEmpty('buildTime');
    form.fieldIsEmpty('cost');
    form.fieldIsEmpty('price');
    form.fieldIsEmpty('imagePath');
    form.fieldIsEmpty('modelPath');
});

Then('the button {string} has the next properties', function (buttonName: string, dataTable: any) {
    console.log('[THEN] the button {string} has the next properties');
    const form = new NewPartForm();
    expect(form).to.not.be.null;
    for (let index = 0; index < dataTable.hashes().length; index++) {
        const row = dataTable.hashes()[index];
        form.validateButton(buttonName, row)
    }
});

// Then('when all required fields have next values', function (dataTable) {
//     console.log('[THEN] the button {string} has the next properties');
//     const row = dataTable.hashes()[0];
//     const form = new NewPartForm();
//     expect(form).to.not.be.null;
//     form.formInput(row);
// });

When('there is a click on the {string} button', function (buttonName) {
    console.log('[WHEN] there is a click on the {string} button');
    switch (buttonName) {
        case 'GUARDAR':
            cy.get('new-part-dialog').find('button').get('#submit-button').should('not.be.disabled')
            cy.get('new-part-dialog').find('button').get('#submit-button').click();
            break;
        case 'CLOSE':
            cy.get('new-part-dialog').find('button').get('#cancel-button').should('not.be.disabled')
            cy.get('new-part-dialog').find('button').get('#cancel-button').click();
            break;
        case 'GUARDAR-NUEVO':
            cy.get('new-part-dialog').find('button').get('#repeat-button').should('not.be.disabled')
            cy.get('new-part-dialog').find('button').get('#repeat-button').click();
            break;
    }
});

Then('the part is persisted at the backend', function () {
    console.log('[THEN] the part is persisted at the backend');
});

Then('the dialog closes', function () {
    console.log('[THEN] the dialog closes');
    cy.get('new-part-dialog').should('have.length', 0);
});

Then('there is a call to the backend to get the Finishings', function () {
    console.log('[THEN] there is a call to the backend to get the Finishings');
});

Then('the Material dropdown has {int} elements', function (options) {
    cy.get('new-part-dialog')
        .find('select').get('#material')
        .find('option')
        .should('have.length', options);
});

Then('form fields have the next values', function (dataTable) {
    const row = dataTable.hashes()[0];
    const form = new NewPartForm();
    expect(form).to.not.be.null;
    form.validateFields(row);
});

Then('the Color dropdown has {int} values', function (options) {
    cy.get('new-part-dialog')
        .find('select').get('#color')
        .find('option')
        .should('have.length', options);
});


Then('the New Part dialog input field {string} should be empty', function (fieldName: string) {
    cy.get('app-root').get('new-part-dialog').find('.column').as('target-field')
        .find('[name="' + fieldName + '"]')
        .should('to.be.empty')
});

Then('the New Part dialog input field {string} should not be empty', function (fieldName: string) {
    cy.get('app-root').get('new-part-dialog').find('.column').as('target-field')
        .find('[name="' + fieldName + '"]')
        .should('not.to.be.empty')
});

Then('the New Part dialog input field {string} should be {string}', function (fieldName: string, fieldValue: string) {
    cy.get('app-root').get('new-part-dialog').find('.column').as('target-field')
        .find('[name="' + fieldName + '"]')
        .should('have.value', fieldValue + ''.trim())
});

Then('{string} is set on the New Part dialog input field {string}', function (fieldValue: string, fieldName: string) {
    cy.get('app-root').get('new-part-dialog').find('.column').as('target-field')
        .find('[name="' + fieldName + '"]')
        .clear().type(fieldValue)
});

Then('{string} is set on the New Part dialog dropdown field {string}', function (fieldValue: string, fieldName: string) {
    cy.get('app-root').get('new-part-dialog').find('.column').as('target-field')
        .find('[name="' + fieldName + '"]')
        .select(fieldValue)
});
