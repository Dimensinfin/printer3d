// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
// - ENVIRONMENT
import { environment } from '@env/environment';
import { platformconstants } from '@app/platform/platform-constants';
// - ROUTER
import { Router } from '@angular/router';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { ResponseTransformer } from './support/ResponseTransformer';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';

// const DOCK_CURRENT_CONFIGURATION = '-DOCK_CURRENT_CONFIGURATION-';
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
export class AppStoreService {
    private dockActiveConfiguration: BehaviorSubject<Feature[]> = new BehaviorSubject<Feature[]>([]);

    constructor(
        protected router: Router,
        protected isolationService: IsolationService,
        protected httpService: HttpClientWrapperService) { }

    // - D O C K
    /**
     * Reads the Dock configuration from local storage. If the configuration is not found then is an initialization event and the initial list is read
     * from the default dock configuration list on the application resources. If found it is feeded into the Subject so all listeners will be informed about changes on the Dock to be updated.
     */
    public fireAccessDockConfiguration(): void {
        const currentConfiguration = this.isolationService.getLocalStorage(platformconstants.DOCK_CURRENT_CONFIGURATION);
        if (this.isEmpty(currentConfiguration)) {
            this.accessProperty('/config/DefaultDockFeatureMap')
                .subscribe((fileData: any) => {
                    const defaultConfiguration: Feature[] = featureTransformer.transform(fileData) as Feature[];
                    this.isolationService.setLocalStorageObject(platformconstants.DOCK_CURRENT_CONFIGURATION, defaultConfiguration);
                    this.dockActiveConfiguration.next(defaultConfiguration);
                });
        } else {
            const defaultConfiguration = featureTransformer.transform(JSON.parse(currentConfiguration));
            this.dockActiveConfiguration.next(defaultConfiguration);
        }
    }
    public synchronize2DockConfiguration(): BehaviorSubject<Feature[]> {
        return this.dockActiveConfiguration;
    }
    public saveDockConfiguration(configuration: Feature[]): void {
        this.isolationService.setLocalStorageObject(platformconstants.DOCK_CURRENT_CONFIGURATION, configuration);
    }

    // - G L O B A L   S U P P O R T   M E T H O D S
    public isEmpty(target?: any): boolean {
        if (null == target) return true;
        if (Object.keys(target).length == 0) return true;
        if (target.length == 0) return true;
        return false;
    }
    public accessProperty(propertyName: string): any {
        console.log("><[AppStoreService.accessProperty]> Property: " + propertyName);
        // Construct the request to call the internal server.
        let request = '/assets/properties' + propertyName + '.json';
        return this.httpService.wrapHttpRESOURCECall(request)
            .pipe(data => {
                return data as any;
            });
    }
}
