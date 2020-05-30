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
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { AppComponent } from '@app/app.component';

const featureTransformer = new ResponseTransformer().setDescription('Do property transformation to "Feature" list.')
    .setTransformation((entrydata: any): Feature[] => {
        let results: Feature[] = [];
        if (entrydata instanceof Array) {
            for (let key in entrydata)
                results.push(new Feature(entrydata[key]));
        }
        return results;
    });

@Component({
    selector: 'v1-dock',
    templateUrl: './v1-dock.component.html',
    styleUrls: ['./v1-dock.component.scss']
})
export class V1DockComponent implements OnInit {
    @Input() routerDetector: AppComponent;
    public self: V1DockComponent;
    private activeFeature: Feature;
    private configuredFeatures: Feature[] = [];

    constructor(private appStore: AppStoreService,
        protected router: Router) {
        this.self = this;
    }
    public ngOnInit(): void {
        console.log('><[V1DockComponent.ngOnInit]');
        this.appStore.readDockConfiguration()
            .subscribe((configuration: any) => {
                this.configuredFeatures = featureTransformer.transform(configuration);
                console.log('->[V1DockComponent.ngOnInit]> Feature count: ' + this.configuredFeatures.length);
                this.activateFeature(this.activeFeature);
            });
    }
    // - I N T E R A C T I O N
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
        if (null == target) {
            this.activeFeature = null;
            this.pageChange('/');
        } else
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
                }
            } else {
                // 1. Activate the selected feature
                target.activate();
                this.activeFeature = target;
            }
        // IMPROVEMENT: Do not detect if the Feature target is the same.
        // 2. Page change
        if (null != this.activeFeature) this.pageChange(this.activeFeature.getRoute());
    }
    public clean(): void {
        this.activeFeature = null;
        this.ngOnInit();
    }
    /**
     * Save the new dock configuration so if the applciation is restarted this is the new default start point.
     * @param route the new route path to be set as destination.
     */
    private pageChange(route: string): void {
        console.log('><[V1DockComponent.pageChange]> Route: ' + route);
        this.router.navigate([route]);
        if (null != this.routerDetector) this.routerDetector.refresh();
    }
}
