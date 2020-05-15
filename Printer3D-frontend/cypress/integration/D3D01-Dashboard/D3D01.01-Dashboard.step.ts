// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
// import { SelectedFittingData } from '../../support/page-objects/SelectedFittingData.panel';
// import { V1FittingBuyList } from '../../support/page-objects/V1FittingBuyList.panel';
// import { V1JobProductionAccounting } from '../../support/page-objects/V1JobProductionAccounting.panel';
// import { V1ManufactureSchedule } from '../../support/page-objects/V1ManufactureSchedule.panel';
// import { V1Logistics } from '../../support/page-objects/V1Logistics.panel';
// import { ShipYardLocation } from '../../support/page-objects/ShipYardLocation.panel';

const FITTING_BY_ID = 'FITTING-BY-ID';
const TITLE_VALIDATION = 'Printer3DFrontend';

Given('the application Printer3DManager', function () {
    console.log('[GIVEN] the application Printer3DManager');
    // let urlRequest = '/';
    new IsolationService().doLandingPage(); // Load the landing page.
    // console.log('[GIVEN] the application Printer3DManager> urlRequest=' + urlRequest);
    // cy.get('title').contains(TITLE_VALIDATION);
    cy.title().should('eq', TITLE_VALIDATION);
});
When('the application starts the default route is the DashboardPage', function () {
    console.log('[WHEN] the application starts the default route is the DashboardPage');
    let urlRequest = '/dashboard';
    console.log('[WHEN] the application starts the default route is the DashboardPage> urlRequest=' + urlRequest);
    cy.visit(urlRequest);
});


// When('the CorporationShipRequestPage is activated with the request id {string} and the fitting id {string} and quantity {string}', function (activationCode, fittingIdentifier, quantity) {
//     console.log('[WHEN] the CorporationShipRequestPage is activated with the request id {string} and the fitting id {string} and quantity {string}');
//     switch (activationCode) {
//         case FITTING_BY_ID:
//             let urlRequest = '/fittings/fitting/' + fittingIdentifier + '/quantity/' + quantity;
//             new IsolationService().doLoginPage(); // Authenticate the acceptance environment.
//             cy.log('-[[WHEN] the CorporationShipRequestPage is activated with the request id {string} and the fitting id {string} and quantity {string}]> urlRequest=' + urlRequest);
//             cy.visit(urlRequest);
//             break;
//     }
// });
// Given('one instance of SelectedFittingData', function () {
//     console.log('[GIVEN] one instance of SelectedFittingData');
//     const selectedFittingData: SelectedFittingData = new SelectedFittingData();
//     expect(selectedFittingData).to.not.be.null;
//     cy.get('app-root').find('v1-selected-fitting-data').should('have.length', 1)
// });
// Given('one instance of ShipYardLocation', function () {
//     console.log('[GIVEN] one instance of ShipYardLocation');
//     const shipYardLocation: ShipYardLocation = new ShipYardLocation();
//     expect(shipYardLocation).to.not.be.null;
//     cy.get('app-root').find('v1-ship-yard-location').should('have.length', 1)
// });
// Given('one instance of V1FittingBuyList', function () {
//     console.log('[GIVEN] one instance of V1FittingBuyList');
//     const v1FittingBuyList: V1FittingBuyList = new V1FittingBuyList();
//     expect(v1FittingBuyList).to.not.be.null;
//     cy.get('app-root').find('v1-fitting-buy-list').should('have.length', 1)
// });
// Given('one instance of V1ManufactureSchedule', function () {
//     console.log('[GIVEN] one instance of V1ManufactureSchedule');
//     const v1ManufactureSchedule: V1ManufactureSchedule = new V1ManufactureSchedule();
//     expect(v1ManufactureSchedule).to.not.be.null;
//     cy.get('app-root').find('v1-manufacture-schedule').should('have.length', 1)
// });
// Given('one instance of V1Logistics', function () {
//     console.log('[GIVEN] one instance of V1Logistics');
//     const v1Logistics: V1Logistics = new V1Logistics();
//     expect(v1Logistics).to.not.be.null;
//     cy.get('app-root').find('v1-logistics').should('have.length', 1)
// });
// Given('one instance of V1JobProductionAccounting', function () {
//     console.log('[GIVEN] one instance of V1JobProductionAccounting');
//     const v1JobProductionAccounting: V1JobProductionAccounting = new V1JobProductionAccounting();
//     expect(v1JobProductionAccounting).to.not.be.null;
//     cy.get('app-root').find('v1-job-production-accounting').should('have.length', 1)
// });
// Then('there is a panel title with the next text {string}', function (title) {
//     console.log('[THEN] there is a panel title with the next text {string}');
//     cy.get('v1-job-production-accounting').find('div').find('.panel-title').contains(title);
// });
// Then('there is a section title with the next text {string}', function (section) {
//     console.log('[THEN] there is a section title with the next text {string}');
//     cy.get('v1-job-production-accounting').find('div').find('.section-title').contains(section);
// });
