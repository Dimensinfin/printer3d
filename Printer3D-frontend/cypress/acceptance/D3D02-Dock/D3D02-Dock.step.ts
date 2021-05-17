// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps"
import { When } from "cypress-cucumber-preprocessor/steps"
import { Then } from "cypress-cucumber-preprocessor/steps"
// - SERVICE
import { SupportService } from '../../support/SupportService.support'

const supportService = new SupportService()

// - F E A T U R E S
Then('there are {int} Features enabled', function (count: number) {
    const tagDock = supportService.translateTag('dock')
    const tagFeature = supportService.translateTag('feature')
    cy.get(tagDock).find(tagFeature).within(($panel) => {
        cy.get('.feature').not('.disabled').should('have.length', count)
    })
})
Then('there are {int} Features disabled', function (count: number) {
    const tagDock = supportService.translateTag('dock')
    const tagFeature = supportService.translateTag('feature')
    cy.get(tagDock).find(tagFeature).within(($panel) => {
        cy.get('.feature').and('.disabled').should('have.length', count)
    })
})
Then('there is a Feature with label {string}', function (label: string) {
    const tagDock = supportService.translateTag('dock')
    const tagFeature = supportService.translateTag('feature')
    cy.get(tagDock).find(tagFeature).find('[cy-name="feature-label"]').contains(label, { matchCase: false })
        .parent().parent().as('target-feature')
})
Then('the target Feature enabled state is {string}', function (enabledState: string) {
    const tagDock = supportService.translateTag('dock')
    const tagFeature = supportService.translateTag('feature')
    if (enabledState == 'enabled')
        cy.get('@target-feature').parent().within(($panel) => {
            cy.get('.feature').not('.disabled').should('exist')
        })
    if (enabledState == 'disabled')
        cy.get('@target-feature').parent().within(($panel) => {
            cy.get('.feature').and('.disabled').should('exist')
        })
})
Then('the target Feature active state is {string}', function (activeState: string) {
    const tagDock = supportService.translateTag('dock')
    const tagFeature = supportService.translateTag('feature')
    if (activeState == 'active')
        cy.get('@target-feature').within(($panel) => {
            cy.get('.corner-mark').should('exist')
        })
    if (activeState == 'inactive')
        cy.get('@target-feature').within(($panel) => {
            cy.get('.corner-mark').should('not.exist')
        })
})
Then('the target Feature target type is {string}', function (targetType: string) {
    const tagDock = supportService.translateTag('dock')
    const tagFeature = supportService.translateTag('feature')
    if (targetType == 'page')
        cy.get('@target-feature').find('.corner-top').should('not.exist')
    if (targetType == 'dialog')
        cy.get('@target-feature').find('.corner-top').parent().find('.blue-mark').should('exist')
    if (targetType == 'drop')
        cy.get('@target-feature').find('.corner-top').parent().find('.blueviolet-mark').should('exist')
})

// ---------------------
Then('the Feature with label {string} opens a Dialog', function (label: string) {
    cy.get('v1-dock').find('v2-feature')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').parent().find('.blue-mark').should('exist')
})
Then('the Feature with label {string} opens a Page', function (label: string) {
    cy.get('v1-dock').find('v2-feature')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').should('not.exist')
})
Then('the Feature with label {string} opens a DropPage', function (label: string) {
    cy.get('v1-dock').find('v2-feature')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').parent().find('.blueviolet-mark').should('exist')
})
Then('the target Feature {string} changes to state {string}', function (featureLabel: string, state: string) {
    if (state == 'active')
        cy.get('@target-feature').within(($panel) => {
            cy.get('.corner-mark').should('exist')
        })
    if (state == 'inactive')
        cy.get('@target-feature').within(($panel) => {
            cy.get('.corner-mark').should('not.exist')
        })
})

// -  D I A L O G   B U T T O N S
When('there is a click on the {string} button of target dialog', function (buttonName: string) {
    cy.get('@target-dialog').find('[cy-name="' + buttonName + '"]').click('center')
})
// - D I A L O G   C O N T E N T S
Then('the dialog has the title {string}', function (title: string) {
    cy.get('@target-dialog').find('.panel-title').contains(title, { matchCase: false })
})
