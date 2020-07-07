// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { NewPartForm } from '../../support/page-objects/NewPartForm.form';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - L A T E S T   I M P L E M E N T A T I O N
Then('the {string} dialog opens and blocks the display', function (dialogName: string) {
    const tag = supportService.translateTag(dialogName) // Do name replacement
    cy.get('app-root').get('mat-dialog-container').get(tag).as('target-panel')
        .should('exist')
})
// - I N P U T   F I E L D S
Then('the target panel has a form field named {string} with label {string} and empty',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        // Read the type of field from the cy-input-type
        // let inputType: string = ''
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
            //     cy.log(type as string);
            //     inputType = type as string
            // })
            // if (inputType != '') {
            switch (type) {
                case 'input':
                    cy.log('input')
                    cy.get('@target-field').find('input')
                        .should('be.empty')
                    break
                case 'select':
                    cy.log('select')
                    cy.get('@target-field').find('select')
                        .should('not.have.value')
                    break
                case 'textarea':
                    cy.log('select')
                    cy.get('@target-field').find('textarea')
                        .should('be.empty')
                    break
            }
        })
    });
Then('the target panel has a form field named {string} with label {string} and not empty',
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
                        .should('not.be.empty')
                    break
                case 'select':
                    cy.log('select')
                    cy.get('@target-field').find('select')
                        .should('have.value')
                    break
                case 'textarea':
                    cy.log('select')
                    cy.get('@target-field').find('textarea')
                        .should('not.be.empty')
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
Then('the target panel input field named {string} is {string}', function (fieldName: string, state: string) {
    cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
    let inputType: string = ''
    cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
        cy.log(type as string);
        inputType = type as string
    })
    let stateClass = 'ng-valid'
    if (state == 'invalid') stateClass = 'ng-invalid'
    if (state == 'valid') stateClass = 'ng-valid'
    if (state == 'indiferent') stateClass = 'dsf-input'
    if (inputType != '') {
        switch (inputType) {
            case 'input':
                cy.log('input')
                cy.get('@target-field').find('input')
                    .should('have.class', stateClass)
                break
            case 'select':
                cy.log('select')
                cy.get('@target-field').find('select')
                    .should('have.class', stateClass)
                break
            case 'textarea':
                cy.log('textarea')
                cy.get('@target-field').find('textarea')
                    .should('have.class', stateClass)
                break
        }
    }
});
// - B U T T O N S
Then('the target panel button with name {string} has a label {string} and is {string}', function (
    buttonName: string, buttonLabel: string, buttonState: string) {
    cy.get('@target-panel')
        .get('[cy-name="' + buttonName + '"]').contains(buttonLabel, { matchCase: false })
    if (buttonState == 'disabled') {
        cy.get('@target-panel').find('[cy-name="' + buttonName + '"]').invoke('attr', 'disabled')
            .should('exist')
    } else {
        cy.get('@target-panel').find('[cy-name="' + buttonName + '"]').invoke('attr', 'disabled')
            .should('not.exist')
    }
});
Then('the target panel field {string} is tested for size constraints {int} and {int}',
    function (fieldName: string, minCharacters: number, maxCharacters: number) {
        cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('input').should('have.class', 'ng-invalid') // validate invalid before starting test
        cy.get('@target-field').find('input').clear().type(supportService.generateRandomString(minCharacters - 1))
        cy.get('@target-field').find('input').should('have.class', 'ng-invalid') // invalid-one below limit
        cy.get('@target-field').find('input').clear().type(supportService.generateRandomString(minCharacters))
        cy.get('@target-field').find('input').should('have.class', 'ng-valid') // valid-low limit
        cy.get('@target-field').find('input').clear().type(supportService.generateRandomString(maxCharacters))
        cy.get('@target-field').find('input').should('have.class', 'ng-valid') // valid-high limit
        let largerValue = supportService.generateRandomString(maxCharacters + 5)
        cy.get('@target-field').find('input').clear().type(largerValue)
        cy.get('@target-field').find('input').invoke('val').should('equal', largerValue.substr(0, maxCharacters))
    });
Then('the target panel field {string} is tested for value constraints', function (fieldName: string) {
    cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').should('have.class', 'ng-invalid') // validate invalid before starting test
    const numberValue: string = supportService.generateRandomNum(1, 15) + ''
    cy.get('@target-field').find('input').clear().type(numberValue)
    cy.get('@target-field').find('input').should('have.class', 'ng-valid') // validate invalid before starting test
});


// - E X P E R I M E N T A L

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



//     Then('the New Part dialog opens and blocks the display', function () {
//     cy.get('app-root').get('mat-dialog-container').get('new-part-dialog').should('have.length', 1)
// });

// Then('there is one instance of form with the next contents', function (dataTable) {
//     console.log('[THEN] there is one instance of form with the next contents');
//     // const row = dataTable.hashes()[0];
//     const form = new NewPartForm();
//     expect(form).to.not.be.null;
//     form.validateHasContents(dataTable);
// });

// Then('the NewPart dialog input fields should be empty', function () {
//     console.log('[THEN] the NewPart dialog input fields should be empty');
//     const form = new NewPartForm();
//     expect(form).to.not.be.null;
//     form.fieldIsEmpty('label');
//     form.fieldIsEmpty('description');
//     form.fieldIsEmpty('buildTime');
//     form.fieldIsEmpty('cost');
//     form.fieldIsEmpty('price');
//     form.fieldIsEmpty('imagePath');
//     form.fieldIsEmpty('modelPath');
// });

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
