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

import { ViewerPanelComponent } from './viewer-panel.component';

describe('PANEL ViewerPanelComponent [Module: SHARED]', () => {
    let component: ViewerPanelComponent;
    let fixture: ComponentFixture<ViewerPanelComponent>;
    // let isolationService: SupportIsolationService;
    // let appStoreService: SupportAppStoreService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                ViewerPanelComponent,
            ],
            // providers: [
            //     { provide: IsolationService, useClass: SupportIsolationService },
            //     { provide: AppStoreService, useClass: SupportAppStoreService },
            // ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(ViewerPanelComponent);
        component = fixture.componentInstance;
        // appStoreService = TestBed.get(AppStoreService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[header/ViewerPanelComponent]> should be created');
            expect(component).toBeDefined('component has not been created.');
        });
    });
});
