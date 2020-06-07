// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';

import { AppPanelComponent } from './app-panel.component';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { GroupContainer } from '@domain/GroupContainer.domain';

describe('PANEL AppPanelComponent [Module: SHARED]', () => {
    let component: AppPanelComponent;
    let fixture: ComponentFixture<AppPanelComponent>;
    let isolationService: SupportIsolationService;
    let appStoreService: SupportAppStoreService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                AppPanelComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService },
            ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(AppPanelComponent);
        component = fixture.componentInstance;
        appStoreService = TestBed.get(AppStoreService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[shared/AppPanelComponent]> should be created');
            expect(component).toBeDefined('component has not been created.');
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [setters]', () => {
        it('setVariant.success: validate the set for the variant', () => {
            const expected = EVariant.FITTING_LIST;
            component.setVariant(expected);
            let obtained = component.getVariant();
            expect(obtained).toBe(expected);
        });
    });
    describe('Code Coverage Phase [getters]', () => {
        it('getFittingId.success: check the fitting identifier', () => {
            const expected = EVariant.FITTING_LIST;;
            component.variant = expected;
            let obtained = component.getVariant();
            expect(obtained).toBe(expected);
        });
        it('isDownloading.success: check the downloading flag', () => {
            const expected = false;
            let componentAny = component as any;
            componentAny.downloading = expected;
            let obtained = component.isDownloading();
            expect(obtained).toBe(expected);
        });
        it('getNodes2Render.success: check the number of nodes collaborated', () => {
            const expected = new GroupContainer();
            let componentAny = component as any;
            componentAny.dataModelRoot.push(expected);
            component.completeDowload();
            let obtained = component.getNodes2Render();
            expect(obtained).toBeDefined();
            expect(obtained.length).toBe(1);
        });
    });
    describe('Validating interfaces [IViewer]', () => {
        it('notifyDataChanged.success: check that nodes get processed when the root load completes', () => {
            const expected = new GroupContainer();
            let componentAny = component as any;
            componentAny.dataModelRoot.push(expected);
            component.completeDowload();
            let obtained = component.getNodes2Render();
            expect(obtained).toBeDefined();
            expect(obtained.length).toBe(1);
        });
    });
});
