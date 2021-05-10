// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';
// - SERVICES
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { ResponseTransformer } from './support/ResponseTransformer';
// - ENVIRONMENT
import { Feature } from '@bit/innovative.innovative-core.feature-dock/domain/Feature.domain';
import { Router } from '@angular/router';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';

@Injectable({
    providedIn: 'root'
})
export class DockService {
    private activeFeature: Feature;
    private featureList: Feature[];
    private routedComponent: Refreshable;

    constructor(
        protected router: Router,
        protected httpService: HttpClientWrapperService) { }

    public readDockConfiguration(): Observable<Feature[]> {
        const request = '/assets/properties/config/DefaultDockFeatureMap.json'
        const transformer = new ResponseTransformer().setDescription('Do property transformation to "Feature" list.')
            .setTransformation((entrydata: any): Feature[] => {
                let results: Feature[] = [];
                if (entrydata instanceof Array) {
                    for (let key in entrydata)
                        results.push(new Feature(entrydata[key]));
                }
                return results;
            })
        return this.httpService.wrapHttpRESOURCECall(request)
            .pipe(
                map((data) => {
                    this.featureList = transformer.transform(data)
                    return this.featureList
                })
            )
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
            this.activeFeature = undefined
            this.deactivateAllFeatures()
            this.pageChange('/')
        } else
            if (null != this.activeFeature) {
                if (!this.activeFeature.equals(target)) {
                    // Change the active feature following the requirements.
                    // 1. Deactivate all Features and activate this target
                    for (let feature of this.featureList) {
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
        if (null != this.activeFeature) console.log('><[DockService.clean]> ActiveFeature: ' + this.activeFeature.label);
        this.activeFeature = undefined;
        this.deactivateAllFeatures()
    }
    public deactivateAllFeatures(): void {
        for (const feature of this.featureList) {
            feature.deactivate()
        }
    }

    // - E V E N T S
    public activatePage(pageRef: Refreshable): void {
        this.routedComponent = pageRef
    }
    public refresh(): void {
        try {
            if (null != this.routedComponent) this.routedComponent.refresh();
        } catch (Exception) {
            console.log('[AppComponent.refresh]> Component is not refreshable.')
        }
    }
    /**
     * Save the new dock configuration so if the applciation is restarted this is the new default start point.
     * @param route the new route path to be set as destination.
     */
    private pageChange(route: string): void {
        console.log('><[DockService.pageChange]> Route: ' + route);
        if (this.router.url == route)
            this.refresh()
        else
            this.router.navigate([route]);
    }
}
