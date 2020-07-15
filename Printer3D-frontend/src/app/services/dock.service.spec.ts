// - CORE
import { NO_ERRORS_SCHEMA, ɵbypassSanitizationTrustStyle } from '@angular/core';
import { Router } from '@angular/router';
// - TESTING
import { async } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
import { Location } from "@angular/common";
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { DockService } from './dock.service';
import { ResponseTransformer } from './support/ResponseTransformer';
import { HttpClientWrapperService } from './httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
import { features } from 'process';

xdescribe('service DockService [Module: SERVICES]', () => {
    let service: DockService;
    let routerDetector: any = { refresh: () => { } };
    let location: Location;
    let router: Router;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes),
            ],
            declarations: [
            ],
            providers: [
                // { provide: Router, useClass: Router },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        service = TestBed.get(DockService);
        location = TestBed.get(Location)
        router = TestBed.get(Router)
        router.initialNavigation()
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            console.log('><[core/DockService]> should be created');
            expect(service).toBeTruthy('service has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [DOCK]', () => {
        it('readDockConfiguration: read and return the list of configured Features', async () => {
            jasmine.clock().install();
            const serviceAsAny = service as any
            expect(serviceAsAny.activeFeature).toBeUndefined()
            expect(serviceAsAny.featureList).toBeUndefined()
            await service.readDockConfiguration(new ResponseTransformer().setDescription('Do property transformation to "Feature" list.')
                .setTransformation((entrydata: any): Feature[] => {
                    let results: Feature[] = [];
                    if (entrydata instanceof Array) {
                        for (let key in entrydata)
                            results.push(new Feature(entrydata[key]));
                    }
                    return results;
                }))
                .subscribe((response: Feature[]) => {
                    expect(response).toBeDefined();
                    expect(response.length).toBe(2);
                })
            jasmine.clock().tick(1100);
            expect(serviceAsAny.featureList.length).toBe(2);
            jasmine.clock().uninstall()
        });
        it('activateFeature.null: no feature selected for activation', async () => {
            jasmine.clock().install();
            const serviceAsAny = service as any;
            serviceAsAny.activeFeature = new Feature()
            serviceAsAny.featureList = [new Feature({
                "label": "/Rollos",
                "enabled": true,
                "active": true,
                "interaction": "PAGEROUTE",
                "route": "inventory/coillist"
            }),
            new Feature({
                "label": "/Inventario",
                "enabled": true,
                "active": true,
                "interaction": "PAGEROUTE",
                "route": "/inventory/partlist"
            })
            ]
            await service.activateFeature(null)
            jasmine.clock().tick(200);
            expect(serviceAsAny.activeFeature).toBeUndefined()
            expect(serviceAsAny.featureList).toBeDefined()
            expect(serviceAsAny.featureList[0].active).toBeFalse()
            expect(serviceAsAny.featureList[1].active).toBeFalse()
            expect(location.path()).toBe('/');
            jasmine.clock().uninstall()
        });
        xit('activateFeature.new feature: activate a new selected feature', async () => {
            jasmine.clock().install();
            const serviceAsAny = service as any;
            serviceAsAny.activeFeature = undefined
            serviceAsAny.featureList = [new Feature({
                "label": "/Rollos",
                "enabled": true,
                "active": false,
                "interaction": "PAGEROUTE",
                "route": "inventory/coillist"
            }),
            new Feature({
                "label": "/Inventario",
                "enabled": true,
                "active": false,
                "interaction": "PAGEROUTE",
                "route": "/inventory/partlist"
            })
            ]
            expect(serviceAsAny.activeFeature).toBeUndefined()
            await service.activateFeature(new Feature({
                "label": "/Rollos",
                "enabled": true,
                "active": true,
                "interaction": "PAGEROUTE",
                "route": "inventory/coillist"
            }))
            jasmine.clock().tick(200);
            expect(serviceAsAny.activeFeature).toBeDefined()
            expect(serviceAsAny.activeFeature.label).toBe("/Rollos")
            expect(serviceAsAny.featureList).toBeDefined()
            console.log('>Features: '+JSON.stringify(serviceAsAny.featureList))
            expect(serviceAsAny.featureList[0].active).toBeTrue()
            expect(serviceAsAny.featureList[1].active).toBeFalse()
            expect(location.path()).toBe('/inventory/partlist');
            jasmine.clock().uninstall()
        });
        xit('activateFeature.replace feature: activate a new selected feature', async () => {
            jasmine.clock().install();
            const serviceAsAny = service as any;
            serviceAsAny.activeFeature = new Feature({
                "label": "/Rollos",
                "enabled": true,
                "active": false,
                "interaction": "PAGEROUTE",
                "route": "inventory/coillist"
            })
            serviceAsAny.featureList = [new Feature({
                "label": "/Rollos",
                "enabled": true,
                "active": false,
                "interaction": "PAGEROUTE",
                "route": "inventory/coillist"
            }),
            new Feature({
                "label": "/Inventario",
                "enabled": true,
                "active": false,
                "interaction": "PAGEROUTE",
                "route": "/inventory/partlist"
            })
            ]
            expect(serviceAsAny.activeFeature).toBeDefined()
            expect(serviceAsAny.activeFeature.label).toBe("/Rollos")
            await service.activateFeature(new Feature({
                "label": "/Inventario",
                "enabled": true,
                "active": true,
                "interaction": "PAGEROUTE",
                "route": "/inventory/partlist"
            }))
            jasmine.clock().tick(200);
            expect(serviceAsAny.activeFeature).toBeDefined()
            expect(serviceAsAny.activeFeature.label).toBe("/Inventario")
            expect(serviceAsAny.featureList).toBeDefined()
            expect(serviceAsAny.featureList[0].active).toBeFalse()
            expect(serviceAsAny.featureList[1].active).toBeFalse()
            expect(location.path()).toBe('/inventory/partlist');
            jasmine.clock().uninstall()
        });
        // it('activateFeature.firstTime: activate a new feature when there is none active', () => {
        //     const featureA = new Feature({ label: '/Inventario', active: false, route: 'inventory' });
        //     const featureB = new Feature({ label: '/Nueva Pieza', active: false });
        //     const serviceAsAny = service as any;
        //     serviceAsAny.routerDetector = routerDetector;
        //     serviceAsAny.configuredFeatures.push(featureA);
        //     serviceAsAny.configuredFeatures.push(featureB);
        //     expect(serviceAsAny.activeFeature).toBeUndefined();
        //     service.activateFeature(featureA);
        //     expect(serviceAsAny.activeFeature).toBe(featureA);
        // });
        // it('activateFeature.active: activate a new feature when there is one active', () => {
        //     const featureA = new Feature({ label: '/Inventario', active: false, route: 'inventory' });
        //     const featureB = new Feature({ label: '/Nueva Pieza', active: false });
        //     const serviceAsAny = service as any;
        //     serviceAsAny.routerDetector = routerDetector;
        //     serviceAsAny.configuredFeatures.push(featureA);
        //     serviceAsAny.configuredFeatures.push(featureB);
        //     expect(serviceAsAny.activeFeature).toBeUndefined();
        //     service.activateFeature(featureA);
        //     expect(serviceAsAny.activeFeature).toBe(featureA);
        //     service.activateFeature(featureB);
        //     expect(serviceAsAny.activeFeature).toBe(featureB);
        // });
        // xit('clean: clean the Dock configuration', () => {
        //     const serviceAsAny = service as any;
        //     serviceAsAny.configuredFeatures = []
        //     serviceAsAny.configuredFeatures.push(new Feature());
        //     serviceAsAny.configuredFeatures.push(new Feature());
        //     serviceAsAny.configuredFeatures.push(new Feature());
        //     serviceAsAny.configuredFeatures.push(new Feature());
        //     expect(serviceAsAny.configuredFeatures.length).toBe(4);
        //     service.clean();
        //     expect(serviceAsAny.configuredFeatures.length).toBe(2);
        // });
    });
});
