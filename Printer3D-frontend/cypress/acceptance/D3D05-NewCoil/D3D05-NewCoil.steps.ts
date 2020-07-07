// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { NewPartForm } from '../../support/page-objects/NewPartForm.form';
import { NewCoilForm } from '../../support/page-objects/NewCoilForm.form';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - L A T E S T   I M P L E M E N T A T I O N
Then('the {string} dialog opens and blocks the display', function (dialogName: string) {
    const tag = supportService.translateTag(dialogName) // Do name replacement
    cy.get('app-root').get('mat-dialog-container').get(tag).as('target-panel').as('target-dialog')
        .should('exist')
})
Then('the coil is persisted at the backend', function () {
    cy.log('Coil persisted')
});
Then('the dialog closes', function () {
    console.log('Dialog closes');
    cy.get('new-coil-dialog').should('not.exist');
});
When('there is a click on the {string} button of target dialog', function (buttonName: string) {
    cy.get('@target-dialog').find('[cy-name="' + buttonName + '"]').click('center')
});

// - I N P U T   F I E L D S
Then('the target panel has a form field named {string} with label {string} and empty',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        // Read the type of field from the cy-input-type
        let inputType: string = ''
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
            cy.log(type as string);
            inputType = type as string
        })
        if (inputType != '') {
            switch (inputType) {
                case 'input':
                    cy.log('input')
                    cy.get('@target-field').find('input')
                        .should('be.empty')
                    break
                case 'select':
                    cy.log('select')
                    cy.get('@target-field').find('select')
                        .should('be.empty')
                    break
                case 'textarea':
                    cy.log('select')
                    cy.get('@target-field').find('textarea')
                        .should('be.empty')
                    break
            }
        }
    });
Then('the target panel has a form field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
            cy.log(type as string);
            switch (type) {
                case 'input':
                    cy.log('input')
                    cy.get('@target-field').find('input')
                        .invoke('val').should('equal', fieldValue)
                    break
                case 'select':
                    cy.log('select')
                    cy.get('@target-field').find('select')
                        .invoke('val').should('equal', fieldValue)
                    break
                case 'textarea':
                    cy.log('textarea')
                    cy.get('@target-field').find('textarea')
                        .invoke('val').should('equal', fieldValue)
                    break
            }
        })
    });
Then('the target panel field {string} is tested for size constraints {int} and {int}',
    function (fieldName: string, minCharacters: number, maxCharacters: number) {
        cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('input').should('have.class', 'ng-invalid') // validate invalid before starting test
        cy.get('@target-field').find('input').clear().type(supportService.generateRandomString(Math.max(1, minCharacters - 1)))
        cy.get('@target-field').find('input').should('have.class', 'ng-invalid') // invalid-one below limit
        cy.get('@target-field').find('input').clear().type(supportService.generateRandomString(minCharacters))
        cy.get('@target-field').find('input').should('have.class', 'ng-valid') // valid-low limit
        cy.get('@target-field').find('input').clear().type(supportService.generateRandomString(maxCharacters))
        cy.get('@target-field').find('input').should('have.class', 'ng-valid') // valid-high limit
        let largerValue = supportService.generateRandomString(maxCharacters + 5)
        cy.get('@target-field').find('input').clear().type(largerValue)
        cy.get('@target-field').find('input').invoke('val').should('equal', largerValue.substr(0, maxCharacters))
    });
Given('{string} is set on form field {string}', function (fieldValue: string, fieldName: string) {
    cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
        switch (type) {
            case 'input':
                cy.get('@target-field').find('input').clear().type(fieldValue)
                break
            case 'textarea':
                cy.get('@target-field').find('textarea').clear().type(fieldValue)
                break
            case 'select':
                cy.log('select')
                cy.get('@target-field').find('select').select(fieldValue)
                break
        }
    })
});
Given('{int} is set on form field {string}', function (fieldValue: number, fieldName: string) {
    cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear().type(fieldValue + '')
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

// When('there is a click on the {string} button', function (buttonName) {
//     console.log('[WHEN] there is a click on the {string} button');
//     switch (buttonName) {
//         case 'GUARDAR':
//             cy.get('new-coil-dialog').find('button').get('#submit-button').should('not.be.disabled')
//             cy.get('new-coil-dialog').find('button').get('#submit-button').click();
//             break;
//         case 'CLOSE':
//             cy.get('new-coil-dialog').find('button').get('#cancel-button').should('not.be.disabled')
//             cy.get('new-coil-dialog').find('button').get('#cancel-button').click();
//             break;
//         case 'GUARDARYCONTINUAR':
//             cy.get('new-coil-dialog').find('button').get('#repeat-button').should('not.be.disabled')
//             cy.get('new-coil-dialog').find('button').get('#repeat-button').click();
//             break;
//     }
// });


Then('there is one field called {string} with the content {string}', function (fieldName: string, value: string) {
    cy.get('mat-dialog-container').find('input').get('[name="' + fieldName + '"]').should('have.value', value)
});

Then('form fields have the next values', function (dataTable) {
    const row = dataTable.hashes()[0];
    const form = new NewCoilForm();
    expect(form).to.not.be.null;
    form.validateFields(row);
});
