// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - DOMAIN
import { V1RequestDetailPanelComponent } from './v1-request-detail-panel.component';
import { CustomerRequest } from '@domain/production/CustomerRequest.domain';

describe('COMPONENT V1RequestDetailPanelComponent [Module: PRODUCTION]', () => {
    let component: V1RequestDetailPanelComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1RequestDetailPanelComponent,
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1RequestDetailPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.')
            expect(component.selectedRequest).toBeUndefined()
            expect(component.container).toBeUndefined()
        });
    });
    // - C O V E R A G E   P H A S E
    describe('Coverage contract [Methods]', () => {
        it('cleanSelectedRequest: clean the request selected', () => {
            expect(component.selectedRequest).toBeUndefined()
            component.selectRequest(new CustomerRequest())
            expect(component.selectedRequest).toBeDefined()
            component.cleanSelectedRequest()
            expect(component.selectedRequest).toBeUndefined()
        });
        it('selectRequest: set the selected request', () => {
            expect(component.selectedRequest).toBeUndefined()
            component.selectRequest(new CustomerRequest())
            expect(component.selectedRequest).toBeDefined()
        });
    });
});
