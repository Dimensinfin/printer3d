// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { SupportService } from '../../support/SupportService.support';
import { StoreService } from '../../support/StoreService.support';

const supportService = new SupportService();
const storeService = new StoreService();

// - D A T A   G E T T E R S
When('store the {string} count on target', function (symbolicName: string) {
    cy.log(storeService.coilCount + '')
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target').find(tag).its('length').as('startCoilLength').then((coilsLength) => {
        cy.log(coilsLength + '')
        storeService.coilCount = coilsLength
    })
});
When('store the test coil count on target', function () {
    cy.log('After: ' + storeService.coilCount + '')
    const tag = supportService.translateTag('coil') // Do name replacement
    const fieldName = 'material'
    const fieldValue = 'TEST'
    cy.get('@target').find(tag).within(($item) => {
        cy.get('[cy-field-value="' + fieldName + '"]').contains(fieldValue, { matchCase: false }).its('length').as('testCoilLength')
            .then((coilsLength) => {
                cy.log(coilsLength + '')
                storeService.testCoilCount = coilsLength
            })
    })
});
Then('the test coil count increases by {int}', function (int) {
    cy.log('Before: ' + storeService.testCoilCount + '')
    const tag = supportService.translateTag('coil') // Do name replacement
    const fieldName = 'material'
    const fieldValue = 'TEST'
    cy.get('@target').find(tag).within(($item) => {
        cy.get('[cy-field-value="' + fieldName + '"]').contains(fieldValue, { matchCase: false }).its('length')
            .then((coilsLength) => {
                cy.log('After: ' + coilsLength + '')
                expect(storeService.testCoilCount).eq(coilsLength)
            })
    })
});
