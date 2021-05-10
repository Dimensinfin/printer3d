// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// - HTTP PACKAGE
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class AssetManagerService {
    constructor(protected http: HttpClient) { }

    /**
     * Reads a JSON formatted resource. There is no specific convertion to a types class and so can be done on the caller method.
     * @param request the location of the resource file to be read. The resource starts on the /assets application location.
     */
    public wrapLocalRESOURCECall(request: string): Observable<any> {
        console.log("><[AssetManagerService.wrapLocalRESOURCECall]> request: " + request);
        return this.http.get(request);
    }
}
