// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async, tick, fakeAsync } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { InventoryService } from '@app/modules/inventory/service/inventory.service'
// - DOMAIN
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { V1AvailablePartsPanelComponent } from './v1-available-parts-panel.component'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { PartListResponse } from '@domain/dto/PartListResponse.dto'
import { Observable } from 'rxjs'

describe('COMPONENT V1AvailablePartsPanelComponent [Module: PRODUCTION]', () => {
    let component: V1AvailablePartsPanelComponent
    let isolationService = new SupportIsolationService()
    let inventoryService = { apiv1_InventoryGetParts: () => { } }

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1AvailablePartsPanelComponent
            ],
            providers: [
                { provide: IsolationService, useValue: isolationService },
                { provide: InventoryService, useValue: inventoryService }
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1AvailablePartsPanelComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
    })

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.before: validate initialization flow', async () => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            expect(component.isDownloading()).toBeTrue()
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
        })
        it('ngOnInit.after: validate initialization flow', fakeAsync(() => {
            console.log('>[V1AvailablePartsPanelComponent.ngOnInit.after]')
            spyOn(inventoryService, 'apiv1_InventoryGetParts').and
                .callFake(function () {
                    const responseRaw = isolationService.directAccessTestResource('inventory.parts')
                    console.log('size: ' + responseRaw['count'])
                    const transformer: ResponseTransformer = new ResponseTransformer()
                        .setDescription('Transforms Inventory Part list form backend.')
                        .setTransformation((entrydata: any): PartListResponse => {
                            return new PartListResponse(entrydata)
                        })
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(transformer.transform(responseRaw))
                        }, 100)
                    })
                })
            component.ngOnInit()
            tick(3000)
            console.log('>[V1AvailablePartsPanelComponent.Post OnInit]')
            const componentAsAny = component as any
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(componentAsAny.dataModelRoot.length).toBe(16)
            expect(componentAsAny.renderNodeList.length).toBe(16)
            expect(component.isDownloading()).toBeFalse()
            console.log('<[V1AvailablePartsPanelComponent.ngOnInit.after]')
        }))
    })
})
