// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - T A R G E T   C O N T E N T S
Then('the target has a drop place named {string}', function (dropName: string) {
    cy.get('@target').find('[cy-name="' + dropName + '"]').should('exist')
});
// - I N P U T   F I E L D S
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

// - N E W E S T
Then('the target panel has a drop place named {string}', function (symbolicName: string) {
    // const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-panel').find('[cy-name="' + symbolicName + '"]').parent().within(($item) => {
        cy.get('[droppable]').should('exist')

    })
});
// - I N P U T   F I E L D S
Then('the target panel has a field named {string} with label {string} and empty',
    function (fieldName: string, fieldLabel: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-value="' + fieldName + '"]')
            .should('be.empty')
    });
Then('the target panel has a field named {string} with label {string} and not empty',
    function (fieldName: string, fieldLabel: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-value="' + fieldName + '"]')
            .should('not.be.empty')
    });
Then('the target panel has a field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-value="' + fieldName + '"]')
            .contains(fieldValue, { matchCase: false })
    });
Then('the target panel has a form field named {string} with label {string} and empty',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
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
                    cy.log('textarea')
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
// - C O N T E N T S
Then('the target panel has a panel labeled {string} named {string}',
    function (fieldLabel: string, fieldName: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('content-panel')
        cy.get('@target-panel').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
    });
Then('the target item has a named {string} button', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
        .should('exist')
});
Then('the target panel input field named {string} is {string}', function (fieldName: string, state: string) {
    cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
        cy.log(type as string);
        let stateClass = 'ng-valid'
        if (state == 'invalid') stateClass = 'ng-invalid'
        if (state == 'valid') stateClass = 'ng-valid'
        if (state == 'indiferent') stateClass = 'dsf-input'
        switch (type) {
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
    })
});
Then('the target panel field {string} is tested for size constraints {int} and {int}',
    function (fieldName: string, minCharacters: number, maxCharacters: number) {
        cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('input').clear() // Clear the field before starting
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





// - OLD
// Then('the New Request dialog opens and blocks the display', function () {
//     cy.get('app-root').get('mat-dialog-container').get('v1-new-request-dialog').should('exist')
// });
Then('the V1NewRequestPage is activated', function () {
    cy.get('app-root').find('v1-new-request-page').as('target-page')
        .should('exist')
});
Then('the V1NewRequestPage has {int} panels', function (panelCount: number) {
    cy.get('app-root').find('v1-new-request-page').find('.row').first()
        .children()
        .should('have.length', panelCount)
});
Then('the left panel on page V1NewRequestPage is a {string}', function (panelType: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row').children().eq(0)
        .find(panelType)
        .should('exist')
});
Then('the left panel on page V1NewRequestPage has variant {string}', function (variant: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row')
        .find('[ng-reflect-variant="' + variant + '"]')
        .should('exist')
});

Then('the right panel on page V1NewRequestPage is a {string}', function (panelType: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row').children().eq(1)
        .find(panelType)
        .should('exist')
});
Then('the target panel has a {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType)
        .should('exist')
});
Given('the target Part is one labeled {string}', function (labelContent: string) {
    cy.get('app-root').get('v1-available-parts-panel').find('v1-part').find('[name="label"]')
        .contains(labelContent, { matchCase: false }).first().parent().parent()
        .as('target-part')
});
Given('on the target Part there is a field named {string} with field name {string}', function (fieldLabel: string, fieldName: string) {
    cy.get('@target-part').find('.label').contains(fieldLabel, { matchCase: false })
        .parent()
        .find('[name="' + fieldName + '"]').should('exist')
});
// Then('the target panel has a field labeled {string} named {string} and not empty', function (fieldLabel: string, fieldName: string) {
//     cy.get('@target-panel').find('.field').find('.label').contains(fieldLabel, { matchCase: false })
//         .parent().as('target-field')
//     cy.get('@target-field').find('[name="' + fieldName + '"]').should('exist').should('not.be.empty')
// });
// Then('the target panel has a field labeled {string} named {string} and empty', function (fieldLabel: string, fieldName: string) {
//     cy.get('@target-panel').find('.field').find('.label').contains(fieldLabel, { matchCase: false })
//         .parent().as('target-field')
//     cy.get('@target-field').find('[name="' + fieldName + '"]').should('exist').should('be.empty')
// });
Then('the target part is draggable with the contraint {string}', function (scope: string) {
    cy.get('@target-part').parent().parent().find('[draggable="true"]')
        .should('exist')
    cy.get('@target-part').parent().parent().find('[ng-reflect-drag-scope="' + scope + '"]')
        .should('exist')
});
Then('the target panel has a place for drop with the contraint {string}', function (scope: string) {
    cy.get('@target-panel').find('div').find('[droppable]').parent().find('[ng-reflect-drop-scope="PART"]')
        .should('exist')
});
When('the target Part is dragged to the drop panel {string}', function (drop: string) {
    cy.get('@target-part').trigger('dragstart')
    cy.get('@target-panel').find('[name="' + drop + '"]').trigger('drop')
});
Then('the target panel has {string} buttons', function (buttonCount: string) {
    cy.get('@target-panel').find('button').should('have.length', buttonCount)
});
Given('the form {string} be the target form', function (formName: string) {
    cy.get(formName).find('form').as('target-form')
});

When('{string} is set on the target form field {string}', function (value: string, fieldName: string) {
    cy.get('@target-form').find('[name="' + fieldName + '"]').clear().type(value)
});
// When('the target panel button with name {string} is clicked', function (buttonName: string) {
//     cy.get('@target-panel').find('[cy-name="' + buttonName + '"]')
//         .scrollIntoView().click('left', { force: true })
// });
Then('the Request is persisted at the backend', function () {
    cy.log('The Request is being persisted at the backend.')
});
Then('the active page is set to Dasboard', function () {
    cy.visit('/')
});
Given('an amplified viewport', function () {
    cy.viewport(1000, 800)
});

// - B E S T   P R A C T I C E   I M P L E M E N T A T I O N
Then('the target page has one panel of type {string} with variant {string}', function (renderName: string, variant: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-page').find(tag).get('[ng-reflect-variant="' + variant + '"]').first().should('exist')
});

Then('the target page has one panel of type {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-page').find(tag).should('exist')
});
Then('the target panel has one or more {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag)
        .should('have.length.greaterThan', 0)
});
When('the drag source is dragged to the drop destination {string}', function (dropDestination: string) {
    cy.get('@drag-source').trigger('dragstart')
    cy.get('@target-panel').find('[cy-name="' + dropDestination + '"]').trigger('drop')
});
