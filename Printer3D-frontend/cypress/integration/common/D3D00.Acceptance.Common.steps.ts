// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps"
import { When } from "cypress-cucumber-preprocessor/steps"
import { Then } from "cypress-cucumber-preprocessor/steps"
// - SERVICE
import { SupportService } from '../../support/SupportService.support'

const TITLE_VALIDATION = 'Tetsuo3D'
const supportService = new SupportService()

// - A P P L I C A T I O N
Given('the application Printer3DManager', function () {
    cy.viewport(1400, 900)
    cy.visit('/')
    cy.title().should('contain', TITLE_VALIDATION)
    cy.get('app-root').as('target-page').as('target')
})
When('the application completes loading', function () {
    cy.wait(2000)
})
Then('the active page is set to Dashboard', function () {
    cy.visit('/')
})

// - F E A T U R E   S E L E C T I O N
When('there is a click on Feature {string}', function (featureLabel: string) {
    const tag = supportService.translateTag('feature') // Do name replacement
    cy.get(supportService.translateTag('dock'))
        .find(tag)
        .contains(featureLabel, { matchCase: false }).parent().parent().as('target-feature')
        .scrollIntoView().click('center')
})
When('the Feature with label {string} is clicked the destination is the Page {string}', function (label: string, destination: string) {
    const tag = supportService.translateTag(destination) // Do name replacement
    cy.get(supportService.translateTag('dock'))
        .find(supportService.translateTag('feature'))
        .contains(label, { matchCase: false }).parent()
        .scrollIntoView().click()
    cy.get('app-root').find(tag).as('target-page').as('target').should('exist')
})
When('the Feature with label {string} is clicked the destination is the Dialog {string}', function (featureLabel: string, symbolicName: string) {
    const destination = supportService.translateTag(symbolicName) // Do name replacement
    cy.get(supportService.translateTag('dock'))
        .find(supportService.translateTag('feature')).find('[cy-name="feature"]')
        .contains(featureLabel, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').scrollIntoView().click('center')
    cy.get('mat-dialog-container')
        .get(destination).should('exist').as('target-dialog')
})
Then('the Feature {string} is hovered', function (featureLabel: string) {
    const tagDock = supportService.translateTag('dock')
    const tagFeature = supportService.translateTag('feature')
    cy.get(tagDock).find(tagFeature).find('[cy-name="feature-label"]').contains(featureLabel, { matchCase: false })
        .parent().parent().as('target-feature')

    cy.get('@target-feature').trigger('mouseenter')
})
Then('there are {int} Features active', function (featuresActive: number) {
    const tagDock = supportService.translateTag('dock')
    const tagFeature = supportService.translateTag('feature')
    cy.get(tagDock).find(tagFeature).within(($panel) => {
        cy.get('.corner-mark').should('have.length', featuresActive)
    })
})


// - S P I N N E R
Then('the loading panel shows {string}', function (loadingMessage: string) {
    cy.get('@target-page').find('.loading-message')
        .contains(loadingMessage)
})
When('the loading panel completes', function () {
    cy.wait(2000)
})

// - P A G E   A C T I V A T I O N
Then('the page {string} is activated', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('app-root').find(tag).as('target-page').as('target-panel').as('target')
        .should('exist')
})
Then('the page {string} has {int} panels', function (symbolicName: string, panelCount: number) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('app-root').find(tag).find('.row').first()
        .children()
        .should('have.length', panelCount)
})
Then('the page {string} has {int} sections', function (symbolicName: string, sectionsCount: number) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('app-root').find(tag).within(($item) => {
        cy.get('section').should('have.length', sectionsCount)
    })
})
Then('the {string} dialog opens and blocks the display', function (dialogName: string) {
    const tag = supportService.translateTag(dialogName) // Do name replacement
    cy.get('app-root').get('mat-dialog-container').get(tag).as('target-panel').as('target').as('target-dialog')
        .should('exist')
})

// - D O C K
Given('one instance of Dock', function () {
    const tag = supportService.translateTag('dock') // Do name replacement
    cy.get('app-root').find(tag).should('have.length', 1).as('target')
})

// - T A R G E T   S E L E C T I O N
Given('the target is the panel of type {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[tag replacement]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
})
Given('the target the {string} with id {string}', function (symbolicName: string, recordId: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
        .should('exist')
})
Given('the target has a panel labeled {string} named {string}',
    function (fieldLabel: string, fieldName: string) {
        cy.get('@target').within(($item) => {
            cy.get('[cy-name="' + fieldName + '"]').as('target')
        })
        cy.get('@target').contains(fieldLabel, { matchCase: false })
    })
Given('the target has a component ot type {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target').get(tag).as('target')
})
Given('target is {string}', function (selecState: string) {
    if (selecState == 'selected')
        cy.get('@target').should('have.class', 'selected')
    else
        cy.get('@target').should('not.have.class', 'selected')
})

// - B U T T O N S
Then('the button with name {string} has a label {string} and is {string}', function (
    buttonName: string, buttonLabel: string, buttonState: string) {
    if (buttonState == 'disabled')
        cy.get('@target').get('[disabled]').parent().within(($item) => {
            cy.get('[cy-name="' + buttonName + '"]').contains(buttonLabel, { matchCase: false })
        })
    else {
        cy.get('@target').within(($item) => {
            cy.get('[cy-name="' + buttonName + '"]')
                .contains(buttonLabel, { matchCase: false })
        })
        cy.get('@target').get('[cy-name="' + buttonName + '"]').within(($item) => {
            cy.get('[disabled]').should('not.exist')
        })
    }
})
When('the button with name {string} is clicked', function (buttonName: string) {
    cy.get('@target').within(($item) => {
        cy.get('[cy-name="' + buttonName + '"]')
            .scrollIntoView().click()
    })
    cy.wait(100)
})
Then('the button with name {string} has {string} tag', function (buttonName: string, tagColor: string) {
    const colorTag = '.' + tagColor + '-mark'
    cy.get('@target').get('[cy-name="' + buttonName + '"]').within(($item) => {
        cy.get(colorTag).should('exist')
    })
})

// - I M A G E   B U T T O N S
Then('target has an actionable image named {string}', function (buttonName: string) {
    cy.get('@target').find('[cy-name="' + buttonName + '"]').should('exist')
})
Then('target actionable image named {string} is missing', function (buttonName: string) {
    cy.get('@target').find('[cy-name="' + buttonName + '"]').should('not.exist')
})
When('target actionable image {string} is clicked', function (buttonName: string) {
    cy.get('@target').find('[cy-name="' + buttonName + '"]').as('target-button')
        .scrollIntoView().click()
})
Then('actionable image named {string} is {string}', function (buttonName: string, state: string) {
    cy.log('actionable')
    if (state == 'enabled')
        cy.get('@target').find('[cy-name="' + buttonName + '"]')
            .should('have.class', 'button-enabled')
    if (state == 'disabled')
        cy.get('@target').find('[cy-name="' + buttonName + '"]')
            .should('have.class', 'button-disabled')
})

// - T A R G E T   I N T E R A C T I O N
When('the target is clicked', function () {
    cy.get('@target').scrollIntoView().click()
})
Then('the dialog {string} closes', function (dialogName: string) {
    const tag = supportService.translateTag(dialogName) // Do name replacement
    cy.get(tag).should('not.exist')
})

// - T A R G E T   C O N T E N T S
Then('the target has the title {string}', function (title: string) {
    cy.get('@target').within(($item) => {
        cy.get('.panel-title').contains(title, { matchCase: false })
    })
})
Then('the target has {int} {string}', function (count: number, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target').within(($item) => {
        cy.get(tag).should('have.length', count)
    })
})
Then('the target has no {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target').within(($item) => {
        cy.get(tag).should('not.exist')
    })
})
Then('the target has variant {string}', function (variant: string) {
    cy.get('@target').find('viewer-panel').invoke('attr', 'ng-reflect-variant').should('equal', variant)
})

// - A C T I V E   T A G G I N G
Then('the target item has a {string} tag', function (tagColor: string) {
    const colorTag = '.' + tagColor + '-mark'
    cy.get('@target').find(colorTag).should('exist')
})
// Then('active {string} shows a {string} corner', function (symbolicName: string, tagColor: string) {
//     const tag = supportService.translateTag(symbolicName) // Do name replacement
//     cy.get('@target-panel').find(tag).find('[cy-field-value="status"]')
//         .contains('true', { matchCase: false }).first().as('target-item').parents().closest('.row').as('target')
//     const colorTag = '.' + tagColor + '-mark'
//     cy.get('@target').find(colorTag).should('exist')
// })
// Then('inactive {string} shows an {string} corner', function (symbolicName: string, tagColor: string) {
//     const tag = supportService.translateTag(symbolicName) // Do name replacement
//     const colorTag = '.' + tagColor + '-mark'
//     cy.get('@target').find(colorTag).should('exist')
//     // const fieldName = 'active'
//     // const fieldValue = 'FUERA PROD.'
//     // cy.get('@target-panel').find(tag).find('[cy-field-value="' + fieldName + '"]')
//     //     .contains(fieldValue, { matchCase: false }).first().as('target-item').parents().closest('.row').within(($item) => {
//     //         cy.get('.corner-mark').should('have.class', 'orangered-mark')
//     //     })
// })

// - F I E L D S
Then('field named {string} with label {string} has contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target').within(($item) => {
            cy.get('[cy-name="' + fieldName + '"]')
                .find('[cy-field-label="' + fieldName + '"]').contains(fieldLabel, { matchCase: false })
        })
        cy.get('@target').within(($item) => {
            cy.get('[cy-name="' + fieldName + '"]')
                .find('[cy-field-value="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
        })
    })
Then('field named {string} has contents {string}',
    function (fieldName: string, fieldValue: string) {
        cy.get('@target').find('[cy-name="' + fieldName + '"]').within(($item) => {
            cy.get('[cy-field-value="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
        })
    })
Then('field named {string} with label {string} is empty',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target').within(($item) => {
            cy.get('[cy-field-label="' + fieldName + '"]').contains(fieldLabel, { matchCase: false })
        })
        cy.get('@target').within(($item) => {
            cy.get('.label').contains(fieldLabel, { matchCase: false }).parent()
                .find('[cy-field-value="' + fieldName + '"]').should('be.empty')
        })
    })
Then('field named {string} with label {string} is not empty',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target').within(($item) => {
            cy.get('[cy-field-label="' + fieldName + '"]').contains(fieldLabel, { matchCase: false })
        })
        cy.get('@target').within(($item) => {
            cy.get('.label').contains(fieldLabel, { matchCase: false }).parent()
                .find('[cy-field-value="' + fieldName + '"]').should('not.be.empty')
        })
    })
Then('field named {string} is not editable', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').invoke('attr', 'cy-input-type')
        .should('not.exist')
})

// - F I E L D   V A L I D A T I O N
Then('field named {string} is tested for size constraints {int} and {int}',
    function (fieldName: string, minCharacters: number, maxCharacters: number) {
        cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('input').clear().should('have.class', 'ng-invalid') // validate invalid before starting test
        cy.get('@target-field').find('input').clear().type(supportService.generateRandomString(minCharacters - 1))
        cy.get('@target-field').find('input').should('have.class', 'ng-invalid') // invalid-one below limit
        cy.get('@target-field').find('input').clear().type(supportService.generateRandomString(minCharacters))
        cy.get('@target-field').find('input').should('have.class', 'ng-valid') // valid-low limit
        cy.get('@target-field').find('input').clear().type(supportService.generateRandomString(maxCharacters))
        cy.get('@target-field').find('input').should('have.class', 'ng-valid') // valid-high limit
        let largerValue = supportService.generateRandomString(maxCharacters + 5)
        cy.get('@target-field').find('input').clear().type(largerValue)
        cy.get('@target-field').find('input').invoke('val').should('equal', largerValue.substr(0, maxCharacters))
    })
Then('field named {string} is tested for max size of {int}',
    function (fieldName: string, maxCharacters: number) {
        const minCharacters = 1
        cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
        let largerValue = supportService.generateRandomString(maxCharacters + 5)
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
            cy.log(type as string)
            switch (type) {
                case 'input':
                    cy.get('@target-field').find('input').clear().type(largerValue)
                    cy.get('@target-field').find('input').invoke('val').should('equal', largerValue.substr(0, maxCharacters))
                    break
                case 'textarea':
                    cy.get('@target-field').find('textarea').clear().type(largerValue)
                    cy.get('@target-field').find('textarea').invoke('val').should('equal', largerValue.substr(0, maxCharacters))
                    break
            }
        })
    })
Then('field named {string} is tested for value constraints {int} to {int}',
    function (fieldName: string, minValue: number, maxValue: number) {
        cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('input').clear().should('have.class', 'ng-invalid') // validate invalid before starting test
        const numberValue: number = supportService.generateRandomNum(minValue, maxValue)
        cy.get('@target-field').find('input').clear().type(numberValue + '')
        cy.get('@target-field').find('input').should('have.class', 'ng-valid')
        cy.get('@target-field').find('input').clear().type((minValue - 1) + '')
        cy.get('@target-field').find('input').should('have.class', 'ng-invalid')
        cy.get('@target-field').find('input').clear().type((maxValue + 11) + '')
        cy.get('@target-field').find('input').should('have.class', 'ng-invalid')
    })
Then('field named {string} is tested for value constraints {int}',
    function (fieldName: string, minValue: number) {
        cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('input').clear().should('have.class', 'ng-invalid') // validate invalid before starting test
        const numberValue: number = supportService.generateRandomNum(minValue, minValue + 100)
        cy.get('@target-field').find('input').clear().type(numberValue + '')
        cy.get('@target-field').find('input').should('have.class', 'ng-valid')
        cy.get('@target-field').find('input').clear().type((minValue - 1) + '')
        cy.get('@target-field').find('input').should('have.class', 'ng-invalid')
    })
Then('field named {string} is tested for numeric constraints {float}',
    function (fieldName: string, minValue: number) {
        cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('input').clear().should('have.class', 'ng-invalid') // validate invalid before starting test
        const numberValue: number = supportService.generateRandomNum(minValue, minValue + 100)
        cy.get('@target-field').find('input').clear().type(numberValue + '').should('have.class', 'ng-valid')
        if ((minValue - 1) > 0) cy.get('@target-field').find('input').clear().type((minValue - 1) + '').should('have.class', 'ng-invalid')
    })

// - C O L U M N S
Then('column named {string} has contents {string}', function (fieldName: string, fieldContents: string) {
    cy.get('@target').within(($panel) => {
        cy.get('[cy-name="' + fieldName + '"]').contains(fieldContents, { matchCase: false })
    })
})

// - D R A G   &   D R O P
Given('the drag source the {string} with id {string}', function (symbolicName: string, recordId: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target').find(tag).find('[id="' + recordId + '"]').as('drag-source')
        .should('have.prop', 'draggable')
        .should('exist')
})
When('the drag source is dragged to the drop destination {string}', function (dropDestination: string) {
    cy.get('@drag-source').scrollIntoView().trigger('dragstart')
    cy.get('@target').find('[cy-name="' + dropDestination + '"]').trigger('drop')
})
Then('the target has a drop place named {string}', function (dropName: string) {
    cy.get('@target').find('[cy-name="' + dropName + '"]').should('exist')
})

// - F O R M   F I E L D S
Then('form field named {string} with label {string} has contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
            cy.log(type as string)
            switch (type) {
                case 'input':
                    cy.get('@target-field').within(($panel) => {
                        cy.get('input')
                            .invoke('val').should('equal', fieldValue)
                    })
                    break
                case 'select':
                    // cy.log('select')
                    cy.get('@target-field').find('select')
                        .invoke('val').should('equal', fieldValue)
                    break
                case 'textarea':
                    // cy.log('textarea')
                    cy.get('@target-field').find('textarea')
                        .invoke('val').should('equal', fieldValue)
                    break
                case 'checkbox':
                    // cy.log('chekbox')
                    cy.get('@target-field').find('input')
                        .invoke('val').should('equal', fieldValue)
                    break
            }
        })
    })
Then('form field named {string} with label {string} is empty',
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
                    cy.log('select')
                    cy.get('@target-field').find('textarea')
                        .should('be.empty')
                    break
            }
        })
    })
Then('form field named {string} with label {string} is not empty',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
            switch (type) {
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
        })
    })
Then('form field named {string} is {string}', function (fieldName: string, state: string) {
    cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
    let inputType: string = ''
    cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
        cy.log(type as string)
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
})

// - A L T E R N A T E   B A C K E N D   R E S P O N S E S
Given('response {string} for {string}', function (responseCode: string, endpoint: string) {
    const tag = supportService.translateTag(endpoint) // Do name replacement
    cy.setCookie(tag, responseCode)
})
// - N O T I F I C A T I O N S
Then('there is a {string} Notification panel', function (string) {
    cy.get('#toast-container').should('exist')
})

// - F O R M   F I E L D S
Given('empty is set on form field {string}', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear()
})
Given('{int} is set on form field {string}', function (fieldValue: number, fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear().type(fieldValue + '')
})
Given('{string} is set on form field {string}', function (fieldValue: string, fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
        // cy.get('@target').within(($item) => {
        //     cy.get('[cy-name="' + fieldName + '"]').as('target-field')
        // })
        cy.log(type as string)
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
})
