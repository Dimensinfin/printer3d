// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';
// - SERVICES
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { ResponseTransformer } from './support/ResponseTransformer';
// - ENVIRONMENT
import { environment } from '@env/environment';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { Coil } from '@domain/Coil.domain';
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { MachineListResponse } from '@domain/dto/MachineListResponse.dto';
import { Machine } from '@domain/Machine.domain';
import { Job } from '@domain/Job.domain';
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto';
import { Request } from '@domain/Request.domain';
import { JobRequest } from '@domain/dto/JobRequest.dto';
import { ModelRequest } from '@domain/dto/ModelRequest.dto';
import { ModelForm } from '@domain/inventory/ModelForm.domain';
import { RequestRequest } from '@domain/dto/RequestRequest.dto';
import { UpdateGroupRequest } from '@domain/dto/UpdateGroupRequest.dto';
import { UpdateCoilRequest } from '@domain/dto/UpdateCoilRequest.dto';
import { Feature } from '@domain/Feature.domain';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class DockService {
    private activeFeature: Feature;
    private featureList: Feature[];

    constructor(
        protected router: Router,
        protected httpService: HttpClientWrapperService) { }

    public readDockConfiguration(transformer: ResponseTransformer): Observable<Feature[]> {
        let request = '/assets/properties/config/DefaultDockFeatureMap.json'
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
        this.activeFeature = undefined;
        this.deactivateAllFeatures()
    }
    public deactivateAllFeatures(): void {
        for (const feature of this.featureList) {
            feature.deactivate()
        }
    }
    /**
     * Save the new dock configuration so if the applciation is restarted this is the new default start point.
     * @param route the new route path to be set as destination.
     */
    private pageChange(route: string): void {
        console.log('><[DockService.pageChange]> Route: ' + route);
        this.router.navigate([route]);
    }
}
