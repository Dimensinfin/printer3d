// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { waitForAsync } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - DOMAIN
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
import { Part } from '@domain/inventory/Part.domain';
import { V1DropPartPanelComponent } from './v1-drop-part-panel.component';
import { PartStack } from '@domain/PartStack.domain';

describe('COMPONENT V1DropPartPanelComponent [Module: PRODUCTION]', () => {
    let component: V1DropPartPanelComponent;

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1DropPartPanelComponent,
            ],
            providers: [
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1DropPartPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.')
            const componentAsAny = component as any
            expect(component.self).toBeUndefined()
            expect(component.droppedParts).toBeDefined()
            expect(component.droppedParts.length).toBe(0)
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.empty: validate initialization flow', () => {
            const componentAsAny = component as any;
            component.ngOnInit();
            expect(component.self).toBeDefined();
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('startEditing: this is used when editing an existing container', () => {
            expect(component.droppedParts.length).toBe(0)
            component.startEditing([new PartStack()])
            expect(component.droppedParts.length).toBe(1)
        });
        it('addPart: add a new part to the container', () => {
            expect(component.droppedParts.length).toBe(0)
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            expect(component.droppedParts.length).toBe(1)
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            expect(component.droppedParts.length).toBe(1)
            component.addPart(new Part({ id: "3f7d2477-59ff-4444-bfb8-8c836be4ae5c" }))
            expect(component.droppedParts.length).toBe(2)
        });
        it('onDrop: drop a part on the container', () => {
            expect(component.droppedParts.length).toBe(0)
            component.onDrop({ dragData: new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }) })
            expect(component.droppedParts.length).toBe(1)
            component.onDrop({ dragData: new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }) })
            expect(component.droppedParts.length).toBe(1)
            component.onDrop({ dragData: new Part({ id: "3f7d2477-59ff-4444-bfb8-8c836be4ae5c" }) })
            expect(component.droppedParts.length).toBe(2)
        });
        it('removePart.single: remove a part form the container', () => {
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            component.addPart(new Part({ id: "3f7d2477-59ff-4444-bfb8-8c836be4ae5c" }))
            expect(component.droppedParts.length).toBe(2)
            component.removePart(new Part({ id: "3f7d2477-59ff-4444-bfb8-8c836be4ae5c" }))
            expect(component.droppedParts.length).toBe(1)
        });
        it('removePartmultiple: remove a part form the container', () => {
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            component.addPart(new Part({ id: "3f7d2477-59ff-4444-bfb8-8c836be4ae5c" }))
            expect(component.droppedParts.length).toBe(2)
            component.removePart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            expect(component.droppedParts.length).toBe(2)
            component.removePart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            expect(component.droppedParts.length).toBe(1)
        });
        it('getDroppedParts: get the list of parts', () => {
            expect(component.getDroppedParts()).toBeDefined()
            expect(component.getDroppedParts().length).toBe(0)
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            expect(component.getDroppedParts()).toBeDefined()
            expect(component.getDroppedParts().length).toBe(1)
        });
        it('getPartIdList: get the list of part identifiers', () => {
            expect(component.getPartIdList()).toBeDefined()
            expect(component.getPartIdList().length).toBe(0)
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            component.addPart(new Part({ id: "3f7d2477-59ff-4444-bfb8-8c836be4ae5c" }))
            expect(component.getPartIdList()).toBeDefined()
            console.log('> id list: ' + JSON.stringify(component.getPartIdList()))
            expect(component.getPartIdList().length).toBe(3)
        });
    });
});
