// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async, tick } from '@angular/core/testing'
import { fakeAsync } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { BackendService } from '@app/services/backend.service'
import { SupportBackendService } from '@app/testing/SupportBackend.service'
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service'
// - DOMAIN
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { V1CoilsPanelComponent } from './v1-coils-panel.component'
import { InventoryService } from '../../service/inventory.service'
import { SupportInventoryService } from '@app/testing/SupportInventory.service'

describe('COMPONENT V1CoilsPanelComponent [Module: INVENTORY]', () => {
    let component: V1CoilsPanelComponent
    let fixture: any
    let service: SupportInventoryService
    let isolationService: SupportIsolationService

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1CoilsPanelComponent
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: InventoryService, useClass: SupportInventoryService }
            ]
        }).compileComponents()

        fixture = TestBed.createComponent(V1CoilsPanelComponent)
        component = fixture.componentInstance
        service = TestBed.inject(SupportInventoryService)
        isolationService = TestBed.inject(SupportIsolationService)
        // fixture.detectChanges()
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.')
        })
    })

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', async () => {
        it('ngOnInit.before: validate initialization flow', () => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            expect(component.isDownloading()).toBeTrue()
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
        })
        it('ngOnInit.after: validate initialization flow', fakeAsync(() => {
            // fixture.detectChanges()
            console.log('>[V1CoilsPanelComponent.ngOnInit.after]')
            service.postResponse('inventory.coils')
            component.ngOnInit()
            tick(1000)
            // fixture.whenStable().then(() => {
            console.log('-[V1CoilsPanelComponent.ngOnInit.after]>Awaiting')
            const componentAsAny = component as any
            expect(component.getVariant()).toBe(EVariant.COIL_LIST)
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(componentAsAny.dataModelRoot.length).toBe(23)
            expect(componentAsAny.renderNodeList.length).toBe(23)
            expect(component.isDownloading()).toBeFalse()
            // })
            console.log('<[V1CoilsPanelComponent.ngOnInit.after]')
        }))
        it('ngOnInit.failure: check the result when there is an error', async () => {
            // fixture.detectChanges()
            console.log('>[V1CoilsPanelComponent.ngOnInit.failure]')
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            expect(component.isDownloading()).toBeTrue()
            spyOn(isolationService, 'processException')
            service.postResponse('NOT_FOUND')
            component.ngOnInit()
            fixture.whenStable().then(() => {
                expect(componentAsAny.backendConnections.length).toBe(1)
                expect(componentAsAny.dataModelRoot.length).toBe(0)
                // This cannot be verified with the current Jasmine code, but works
                // expect(isolationService.processException).toHaveBeenCalled()
            })
        })
    })
})
