// - CUCUMBER-PREPROCESSOR
import { Given } from 'cypress-cucumber-preprocessor/steps'
import { When } from 'cypress-cucumber-preprocessor/steps'
import { Then } from 'cypress-cucumber-preprocessor/steps'
// - SERVICE
import { SupportService } from '../../support/SupportService.support'

const supportService = new SupportService()

// - T A R G E T   S E L E C T I O N
Given('the target is the request at position {int}', function (position: number) {
	cy.get('@target').find('v2-request').eq(position).as('request')
})
Given('the element id is {string}', function (id: number) {
	cy.get('@request')
		.get('#' + id)
		.should('exist')
})
