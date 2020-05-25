// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - ROUTER
import { Router } from '@angular/router';
// - SERVICES
import { AppStoreService } from '@app/services/app-store.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';

@Component({
    selector: 'v1-dock',
    templateUrl: './v1-dock.component.html',
    styleUrls: ['./v1-dock.component.scss']
})
export class V1DockComponent implements OnInit {
    public self: V1DockComponent;
    private activeFeature: Feature;
    private configuredFeatures: Feature[] = [];

    constructor(private appStore: AppStoreService,
        protected router: Router) {
        this.self = this;
    }
    public ngOnInit(): void {
        console.log('><[V1DockComponent.ngOnInit]');
        // this.appStore.fireAccessDockConfiguration();
        this.appStore.readDockConfiguration()
            .subscribe((configuration: Feature[]) => {
                this.configuredFeatures = configuration;
                console.log('->[V1DockComponent.ngOnInit]> Feature count: ' + this.configuredFeatures.length);
            });
    }
    public getActiveFeatures(): Feature[] {
        if (null != this.configuredFeatures) return this.configuredFeatures;
        else return [];
    }
    /**
     * There is a change on the active Feature. The process should do:
     * 1. Deactivate all features
     * 2. Activate the target feature
     * 3. Start a page change
     * @param target the new active Feature.
     */
    public activateFeature(target: Feature): void {
        console.log('><[V1DockComponent.activateFeature]> Feature: ' + JSON.stringify(target));
        if (null != this.activeFeature) {
            if (!this.activeFeature.equals(target)) {
                // Change the active feature following the requirements.
                // 1. Deactivate all Features and activate this target
                for (let feature of this.configuredFeatures) {
                    feature.deactivate();
                    if (target.equals(feature)) {
                        feature.activate();
                        this.activeFeature = feature;
                    }
                }
                // 2. Page change
                this.pageChange(this.activeFeature.getRoute());
            }
        } else {
            // 1. Activate the selected feature
            target.activate();
            this.activeFeature = target;
            // 2. Page change
            this.pageChange(this.activeFeature.getRoute());
        }
    }
    public clean () : void {
        this.activeFeature=null;
        this.ngOnInit();
        // this.appStore.fireAccessDockConfiguration();
    }
    /**
     * Save the new dock configuration so if the applciation is restarted this is the new default start point.
     * @param route the new route path to be set as destination.
     */
    private pageChange(route: string): void {
        // this.appStore.saveDockConfiguration(this.configuredFeatures);
        console.log('><[V1DockComponent.pageChange]> Route: ' + route);
        this.router.navigate([route]);
    }
}
