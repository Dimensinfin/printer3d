// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
// - ROUTER
import { Router } from '@angular/router';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';

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
     * Read the initial dock configuration from the applciation properties. THis is the default Dock setup to be seen when the application starts and it is read once at the intialization step.
     */
    public readDockConfiguration(): Observable<Feature[]> {
        return this.accessProperty('config/DefaultDockFeatureMap')
    }

    // - G L O B A L   S U P P O R T   M E T H O D S
    public isEmpty(target?: any): boolean {
        if (null == target) return true;
        if (Object.keys(target).length == 0) return true;
        return false;
    }
    public accessProperty(propertyName: string): Observable<any> {
        console.log("><[AppStoreService.accessProperty]> Property: " + propertyName);
        // Construct the request to call the internal server.
        let request = '/assets/properties/' + propertyName + '.json';
        return this.httpService.wrapHttpRESOURCECall(request)
            .pipe(data => {
                return data as any;
            });
    }
}
