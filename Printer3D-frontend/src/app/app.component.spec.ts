// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { of, Subject } from 'rxjs'
import { Router } from '@angular/router'
import { Printer3DConstants } from './platform/Printer3DConstants.platform'
// - TESTING
import { inject } from '@angular/core/testing'
import { async } from '@angular/core/testing'
import { fakeAsync } from '@angular/core/testing'
import { tick } from '@angular/core/testing'
import { ComponentFixture } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component'
import { routes } from '@app/testing/RouteMockUp.component'
// - PROVIDERS
// - DOMAIN
import { AppComponent } from './app.component'
import { environment } from '../environments/environment'
import { BackendService } from './services/backend.service'
import { SupportBackendService } from './testing/SupportBackend.service'
import { HttpClientWrapperService } from './services/httpclientwrapper.service'
import { SupportHttpClientWrapperService } from './testing/SupportHttpClientWrapperService.service'
import { MatDialogRef } from '@angular/material/dialog'
import { MatDialog } from '@angular/material/dialog'
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto'

describe('COMPONENT AppComponent [Module: CORE]', () => {
    let component: AppComponent
    let dialog = { open: (dialog: any, config: any) => { } }
    let backendService: any = {apiActuatorInfo: () => { }}

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule
            ],
            declarations: [
                AppComponent
            ],
            providers: [
                { provide: MatDialogRef, useValue: { close: (dialogResult: any) => { } } },
                { provide: MatDialog, useValue: dialog },
                { provide: BackendService, useValue: backendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(AppComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('service has not been created.')
        })
        it('Initial state', () => {
            expect(component).toBeDefined('service has not been created.')
            expect(component.appTitle).toBeDefined('field "appTitle" not defined.')
            expect(component.appVersion).toBeDefined('field "appVersion" not defined.')
        })
    })

    // - G E T T E R   P H A S E
    describe('Getter Phase', () => {
        it('appTitle: check value declared for "appTitle"', () => {
            expect(component.getAppTitle()).toBeDefined('field "appTitle" not defined.')
            expect(component.getAppTitle()).toBe('Tetsuo3D - UI')
        })
        it('appVersion: check value declared for "appVersion"', () => {
            expect(component.getAppVersion()).toBeDefined('field "appVersion" not defined.')
            expect(component.getAppVersion()).toBe(environment.appVersion + ' ' + process.env.NODE_ENV)
        })
    })

    // - F I E L D   A C C E P T A N C E   P H A S E
    describe('Field Acceptance Phase', () => {
        it('appTitle: check value declared for "appTitle"', () => {
            expect(component.appTitle).toBeDefined('field "appTitle" not defined.')
            expect(component.appTitle).toBe('Tetsuo3D - UI')
        })
        it('appVersion: check value declared for "appVersion"', () => {
            expect(component.appVersion).toBeDefined('field "appVersion" not defined.')
            expect(component.appVersion).toBe(environment.appVersion + ' ' + process.env.NODE_ENV)
        })
    })

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.before: validate initial state', () => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
        })
        it('ngOnInit.after: validate initialization flow', fakeAsync(() => {
            console.log('>[AppComponent.ngOnInit.after]')
            spyOn(backendService, 'apiActuatorInfo').and.returnValue(of(new BackendInfoResponse({ build:{ version:'-VERSION-'} })))
            component.ngOnInit()
            tick(1000)
            expect(component.getBackendVersion()).toBe('<-VERSION- backend')
            console.log('<[AppComponent.ngOnInit.after]')
        }))
    })
})
