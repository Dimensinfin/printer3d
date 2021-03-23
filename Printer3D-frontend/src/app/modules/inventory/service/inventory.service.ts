// - CORE
import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'
import { HttpHeaders } from '@angular/common/http'
// - SERVICES
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
// - DOMAIN
import { Printer3DConstants } from "@app/platform/Printer3DConstants.platform"
import { BackendService } from "@app/services/backend.service"
import { Coil } from '@domain/inventory/Coil.domain'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto'
import { environment } from '@env/environment'

@Injectable({
    providedIn: 'root'
})
export class InventoryService extends BackendService {
    private INVENTORYAPIV1: string
    private INVENTORYAPIV2: string

    constructor(
        protected httpService: HttpClientWrapperService) {
        super(httpService)
        this.INVENTORYAPIV1 = Printer3DConstants.APIVERSION1
        this.INVENTORYAPIV2 = Printer3DConstants.APIVERSION2
    }
    // - C O I L S
    public apiv2_InventoryGetCoils(): Observable<Coil[]> {
        const request = this.INVENTORYAPIV2 + '/inventory/coils'
        const transformer: ResponseTransformer = new ResponseTransformer().setDescription('Transforms the list of Coils from the backend.')
            .setTransformation((entrydata: any): Coil[] => {
                const coilList: Coil[] = []
                if (Array.isArray(entrydata))
                    for (let entry of entrydata)
                        coilList.push(new Coil(entry))
                return coilList
            })
            let headers = new HttpHeaders()
            headers.set('xApp-Name', environment.appName)
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[InventoryService.apiv2_InventoryGetCoils]> Transformation: " + transformer.description)
                return transformer.transform(data) as Coil[]
            }))
    }
}
