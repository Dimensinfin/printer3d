// - CORE
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { Subject } from 'rxjs';
// - ENVIRONMENT
import { environment } from '@env/environment';
import { platformconstants } from '@app/platform/platform-constants';
import * as jwt_decode from 'jwt-decode';
// - ROUTER
import { Router } from '@angular/router';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';

const featureTransformer = new ResponseTransformer().setDescription('Do property transformation to "Feature" list.')
    .setTransformation((entrydata: any): Feature[] => {
        let results: Feature[] = [];
        if (entrydata instanceof Array) {
            for (let key in entrydata)
                results.push(new Feature(entrydata[key]));
        } else
            results.push(new Feature(entrydata));

        return results;
    });

@Injectable({
    providedIn: 'root'
})
export class SupportAppStoreService {
    constructor(protected isolationService: IsolationService, ) { }

    // - M O C K   D A T A   A C C E S S
    public directAccessMockResource(dataIdentifier: string): Observable<any> {
        console.log(">>[SupportAppStoreService.directAccessMockResource]> dataIdentifier: " + dataIdentifier);
        let rawdata = require('./mock-data/' + dataIdentifier.toLowerCase() + '.json');
        return Observable.create((observer) => {
            observer.next(rawdata);
            observer.complete();
        });
    }

    // - G L O B A L   A C C E S S   M E T H O D S
    public isEmpty(target?: any): boolean {
        if (null == target) return true;
        if (Object.keys(target).length > 0) return false;
        if (target.length > 0) return true;
        return true;
    }

    // - D O C K
    private dockActiveConfiguration: BehaviorSubject<Feature[]> = new BehaviorSubject<Feature[]>([]);
    public fireAccessDockConfiguration(): BehaviorSubject<Feature[]> {
        const currentConfiguration = this.isolationService.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION);
        if (this.isEmpty(currentConfiguration)) {
            this.accessProperty('/config/DefaultDockFeatureMap')
                .subscribe((fileData: any) => {
                    const defaultConfiguration: Feature[] = featureTransformer.transform(fileData) as Feature[];
                    this.isolationService.setToStorageObject(platformconstants.DOCK_CURRENT_CONFIGURATION, defaultConfiguration);
                    this.dockActiveConfiguration.next(defaultConfiguration);
                });
        } else {
            const defaultConfiguration = featureTransformer.transform(JSON.parse(currentConfiguration));
            this.dockActiveConfiguration.next(defaultConfiguration);
        }
        return this.dockActiveConfiguration;
    }
    public synchronize2DockConfiguration(): BehaviorSubject<Feature[]> {
        return this.dockActiveConfiguration;
    }
    public saveDockConfiguration(configuration: Feature[]): void {
        this.isolationService.setToStorageObject(platformconstants.DOCK_CURRENT_CONFIGURATION, configuration);
    }

    // - G L O B A L   A C C E S S   M E T H O D S
    public isNonEmptyString(str: string): boolean {
        return str && str.length > 0; // Or any other logic, removing whitespace, etc.
    }
    public isNonEmptyArray(data: any[]): boolean {
        if (null == data) return false;
        if (data.length < 1) return false;
        return true;
    }
    public isEmptyString(str: string): boolean {
        let empty = str && str.length > 0; // Or any other logic, removing whitespace, etc.
        return !empty;
    }
    public isEmptyArray(data: any[]): boolean {
        if (null == data) return true;
        if (data.length < 1) return true;
        return false;
    }
    public accessProperty(propertyName: string): Observable<any> {
        console.log("><[SupportAppStoreService.accessProperties]");
        // Construct the request to call the backend.
        let request = require('./mock-data/assets/properties/' + propertyName + '.json');
        console.log("><[SupportAppStoreService.accessProperties]> request=" + JSON.stringify(request));
        return Observable.create((observer) => {
            observer.next(request);
            observer.complete();
        });
    }

    // - J W T   D E C O D E
    public JWTDecode2AccountName(codedToken: string): string {
        const token = this.JWTDecode(codedToken);
        return token["accountName"];
    }
    public JWTDecode2UniqueId(codedToken: string): string {
        const token = this.JWTDecode(codedToken);
        return token["uniqueId"];
    }
    // - J W T
    public JWTDecode(token: string): any {
        return jwt_decode(token);
    }
}
