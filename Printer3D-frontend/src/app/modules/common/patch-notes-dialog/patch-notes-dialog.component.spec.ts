// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { Observable } from 'rxjs'
// - TESTING
import { async } from '@angular/core/testing'
import { fakeAsync } from '@angular/core/testing'
import { tick } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
// - DOMAIN
import { PatchNotesDialogComponent } from './patch-notes-dialog.component'
import { MatDialogRef } from '@angular/material/dialog'
import { EVariant } from '@domain/interfaces/EPack.enumerated'

describe('COMPONENT PatchNotesDialogComponent [Module: RENDER]', () => {
    let component: PatchNotesDialogComponent
    let httpWrapper = { wrapHttpRESOURCECall: () => { } }
    let dialogRef = { close: () => { } }
    // let isolationService: SupportIsolationService = new SupportIsolationService()

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                PatchNotesDialogComponent
            ],
            providers: [
                { provide: MatDialogRef, useValue: dialogRef },
                { provide: HttpClientWrapperService, useValue: httpWrapper }
            ]
        }).compileComponents()

        component = TestBed.createComponent(PatchNotesDialogComponent).componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
    })

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.before: validate initialization flow', () => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            expect(component.isDownloading()).toBeTrue()
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
        })
        it('ngOnInit.after: validate initialization flow', fakeAsync(() => {
            console.log('>[PatchNotesDialogComponent.ngOnInit.after]')
            spyOn(httpWrapper, 'wrapHttpRESOURCECall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next([{
                                "jsonClass": "PatchChange",
                                "name": "➰ Anotaciones cambio 0.14.1",
                                "changes": [
                                    "✅ Se ha cambiado el tipo de la fuente principal de la aplicación a Open Sans."
                                ]
                            }])
                        }, 100)
                    })
                })
            component.ngOnInit()
            tick(1000)
            const componentAsAny = component as any
            expect(component.getVariant()).toBe(EVariant.PATCH_NOTES)
            expect(componentAsAny.backendConnections.length).toBe(1)
            console.log('<[PatchNotesDialogComponent.ngOnInit.after]')
        }))
    })

    // - I N T E R A C T I O N S   P H A S E
    describe('Interactions Phase', () => {
        it('closeModal: close the patch notes dialog', () => {
            // Given
            const componentAsAny = component as any
            expect(componentAsAny.dialogRef).toBeDefined()
            spyOn(dialogRef, 'close')
            // Test
            component.closeModal()
            // Expect
            expect(dialogRef.close).toHaveBeenCalled()
        })
    })
})
