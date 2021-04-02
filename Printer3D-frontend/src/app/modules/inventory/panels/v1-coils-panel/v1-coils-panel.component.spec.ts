// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { of } from 'rxjs'
// - TESTING
import { async, tick, fakeAsync } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
// - DOMAIN
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { V1CoilsPanelComponent } from './v1-coils-panel.component'
import { InventoryService } from '../../service/inventory.service'
import { SupportInventoryService } from '@app/testing/SupportInventory.service'
import { HttpErrorResponse, HttpHeaders } from '@angular/common/http'
import { Coil } from '@domain/inventory/Coil.domain'

describe('COMPONENT V1CoilsPanelComponent [Module: INVENTORY]', () => {
    let component: V1CoilsPanelComponent
    let fixture: any
    let isolationService: SupportIsolationService = new SupportIsolationService()
    let inventoryService: SupportInventoryService = new SupportInventoryService()

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1CoilsPanelComponent
            ],
            providers: [
                { provide: IsolationService, useValue: isolationService },
                { provide: InventoryService, useValue: inventoryService }
            ]
        }).compileComponents()

        fixture = TestBed.createComponent(V1CoilsPanelComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            expect(component.filter).toBeDefined()
            expect(component.filter).toBe('')
            expect(component.filterInactive).toBeTrue()
            const componentAsAny = component as any
            expect(componentAsAny.coilList).toBeDefined()
            expect(componentAsAny.coilList.length).toBe(0)
        })
    })

    // - O N I N I T I A L I Z A T I O N   P H A S E
    describe('On Initialization Phase', async () => {
        it('ngOnInit.before: validate initialization flow', () => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            expect(component.isDownloading()).toBeTrue()
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
        })
        it('ngOnInit.after: validate initialization flow', fakeAsync(() => {
            console.log('>[V1CoilsPanelComponent.ngOnInit.after]')
            spyOn(inventoryService, 'apiv2_InventoryGetCoils').and
                .callFake(function () {
                    return inventoryService.prepareResponse('inventory.coils', inventoryService.directAccessMockResource('inventory.coils'))
                })
            component.ngOnInit()
            tick(1000)
            const componentAsAny = component as any
            expect(component.getVariant()).toBe(EVariant.COIL_LIST)
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(componentAsAny.dataModelRoot.length).toBe(13)
            expect(componentAsAny.renderNodeList.length).toBe(13)
            expect(component.isDownloading()).toBeFalse()
            console.log('<[V1CoilsPanelComponent.ngOnInit.after]')
        }))
        it('ngOnInit.failure: check the result when there is an error', fakeAsync(() => {
            console.log('>[V1CoilsPanelComponent.ngOnInit.failure]')
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            expect(component.isDownloading()).toBeTrue()
            spyOn(inventoryService, 'apiv2_InventoryGetCoils').and
                .callFake(function () {
                    return inventoryService.prepareResponse('Throw Error', new HttpErrorResponse({
                        error: 'This is the error message',
                        headers: new HttpHeaders(),
                        status: 401,
                        statusText: "NOT_FOUND",
                        url: "url"
                    }))
                })
            spyOn(isolationService, 'processException')
            component.ngOnInit()
            tick(1000)
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(componentAsAny.dataModelRoot.length).toBe(0)
            expect(isolationService.processException).toHaveBeenCalled()
        }))
    })

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Interactions]', () => {
        it('changeFilter.success: signal the change on the active filter', () => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            component.changeFilter()
            expect(componentAsAny.backendConnections.length).toBe(1)
        })
        it('nodeCounter.success: check the coil counter value', () => {
            const componentAsAny = component as any
            expect(component.nodeCounter()).toBe(0)
            componentAsAny.coilList = [new Coil(), new Coil(), new Coil()]
            expect(component.nodeCounter()).toBe(3)
        })
    })
    describe('Code Coverage Phase [AppPanelComponent]', () => {
        it('getNodes2Render.success: validate the use of filters to return the list of Coils', fakeAsync(() => {
            console.log('>[V1CoilsPanelComponent.ngOnInit.after]')
            // - Load the list of Coils
            const componentAsAny = component as any
            spyOn(inventoryService, 'apiv2_InventoryGetCoils').and
                .callFake(function () {
                    return inventoryService.prepareResponse('inventory.coils', inventoryService.directAccessMockResource('inventory.coils'))
                })
            component.ngOnInit()
            tick(1000)
            expect(component.getNodes2Render().length).toBe(13)
            expect(component.getNodes2Render('tpu').length).toBe(1)
        }))
        it('getNodes2Render.success: validate the cancel of filters', fakeAsync(() => {
            console.log('>[V1CoilsPanelComponent.ngOnInit.after]')
            // - Load the list of Coils
            const componentAsAny = component as any
            spyOn(inventoryService, 'apiv2_InventoryGetCoils').and
                .callFake(function () {
                    return inventoryService.prepareResponse('inventory.coils', inventoryService.directAccessMockResource('inventory.coils'))
                })
            component.filterInactive = false
            component.ngOnInit()
            tick(1000)
            expect(component.getNodes2Render().length).toBe(23)
            expect(component.getNodes2Render('tpu').length).toBe(3)
        }))
    })
})
