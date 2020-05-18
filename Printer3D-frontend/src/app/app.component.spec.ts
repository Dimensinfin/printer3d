// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from './platform/platform-constants';
// - TESTING
import { inject } from '@angular/core/testing';
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
// - DOMAIN
import { AppComponent } from './app.component';

describe('COMPONENT AppComponent [Module: CORE]', () => {
    let component: AppComponent;
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule
            ],
            declarations: [
                AppComponent
            ],
        }).compileComponents();

        const fixture = TestBed.createComponent(AppComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentInstance = new AppComponent();
            expect(component).toBeTruthy('service has not been created.');
            expect(component.appTitle).toBeDefined('field "appTitle" not defined.');
            expect(component.appVersion).toBeDefined('field "appVersion" not defined.');
        });
    });

    // - G E T T E R   P H A S E
    describe('Getter Phase', () => {
        it('appTitle: check value declared for "appTitle"', () => {
            const componentInstance = new AppComponent();
            expect(componentInstance.getAppTitle()).toBeDefined('field "appTitle" not defined.');
            expect(componentInstance.getAppTitle()).toBe('3DPrinterManagement - UI');
        });
        it('appVersion: check value declared for "appVersion"', () => {
            const componentInstance = new AppComponent();
            expect(componentInstance.getAppVersion()).toBeDefined('field "appVersion" not defined.');
            expect(componentInstance.getAppVersion()).toBe('v0.1.0 dev');
        });
    });

    // - F I E L D   A C C E P T A N C E   P H A S E
    describe('Field Acceptance Phase', () => {
        it('appTitle: check value declared for "appTitle"', () => {
            expect(component.appTitle).toBeDefined('field "appTitle" not defined.');
            expect(component.appTitle).toBe('3DPrinterManagement - UI');
        });
        it('appVersion: check value declared for "appVersion"', () => {
            expect(component.appVersion).toBeDefined('field "appVersion" not defined.');
            expect(component.appVersion).toBe('v0.1.0 dev');
        });
    });
});
