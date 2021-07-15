// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// - HTTP PACKAGE
import { HttpHeaders } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class MockResourceAccess{
    public directAccessMockResource(dataIdentifier: string): any {
        console.log(">[MockResourceAccess.directAccessMockResource]> dataIdentifier: " + dataIdentifier);
        let rawdata = require('../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json');
        console.log(">[MockResourceAccess.directAccessMockResource]> path: " +
            '../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json');
        return rawdata
    }
    public wrapHttpGETCall(_request: string, _requestHeaders?: HttpHeaders): Observable<any> {
        return null
    }
    public wrapHttpPOSTCall(_request: string, body: any, _requestHeaders?: HttpHeaders): Observable<any> {
        return null
    }
    public wrapHttpPATCHCall(_request: string, body: any, _requestHeaders?: HttpHeaders): Observable<any> {
        return null
    }
}
