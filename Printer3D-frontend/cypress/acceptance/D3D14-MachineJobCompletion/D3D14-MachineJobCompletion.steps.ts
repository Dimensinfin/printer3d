// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps"
import { When } from "cypress-cucumber-preprocessor/steps"
import { Then } from "cypress-cucumber-preprocessor/steps"
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support'
import { SupportService } from '../../support/SupportService.support'

const supportService = new SupportService()

// - T I M E   C O N T R O L
Given('a timed application Printer3DManager', function () {
    cy.clock()
    cy.viewport(1400, 900)
    cy.visit('/')
})
Then('advance time {string} minutes', function (minutes: number) {
    cy.tick(minutes * 60 * 1000)
})
// - M O V E M E N T
When('the mouse exits the target', function () {
    cy.get('@target').find('[cy-focus="mouseleave"]').trigger('mouseleave')
})

Then('field {string} has color {string}', function (fieldName: string, color: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]')
    .should('have.css', 'color').and('equal', color)
})
