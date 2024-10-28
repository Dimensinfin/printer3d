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
import { UpdateCoilRequest } from '@domain/dto/UpdateCoilRequest.dto'
import { IsolationService } from '@app/platform/isolation.service'
import { CoilToUpdateCoilRequestConverter } from '@domain/converter/CoilToUpdateCoilRequest.converter'
import { PartListResponse } from '@domain/dto/PartListResponse.dto'
import { Part } from '@domain/inventory/Part.domain'

@Injectable({
    providedIn: 'root'
})
export class InventoryService extends BackendService {
    private INVENTORYAPIV1: string
    private INVENTORYAPIV2: string

    constructor(
        protected httpService: HttpClientWrapperService,
        protected isolationService: IsolationService) {
        super(httpService)
        this.INVENTORYAPIV1 = Printer3DConstants.APIVERSION1
        this.INVENTORYAPIV2 = Printer3DConstants.APIVERSION2
    }
    // - C O I L S
    public apiv2_InventoryGetCoils(): Observable<Coil[]> {
      console.log('>[Containers.12]')
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
        headers = headers.set('xApp-Api-Version', 'API v2');
        return this.httpService.wrapHttpGETCall(request, headers)
            // .pipe(map((data: any) => {
            //   console.log('>[Containers.13]')
            //   console.log(">[InventoryService.apiv2_InventoryGetCoils]> Transformation: " + transformer.description)
            //     return transformer.transform(data) as Coil[]
            // }))
    }
    public apiv2_InventoryUpdateCoil(updatingCoil: Coil): Observable<Coil> {
        const request = this.INVENTORYAPIV1 + '/inventory/coils'
        const updateCoilRequest: UpdateCoilRequest = new CoilToUpdateCoilRequestConverter().convert(updatingCoil)
        const transformer = new ResponseTransformer().setDescription('Do HTTP transformation to "Coil".')
            .setTransformation((entrydata: any): Coil => {
                const targetCoil: Coil = new Coil(entrydata)
                this.isolationService.successNotification('Filamento [' +
                    updatingCoil.getCoilIdentifier() +
                    '] actualizado correctamente.'
                    , '/INVENTARIO/ACTUALIZACION FILAMENTO/OK')
                return targetCoil
            })
        let headers = new HttpHeaders()
        headers = headers.set('xApp-Api-Version', 'API v2');
        return this.httpService.wrapHttpPATCHCall(request, JSON.stringify(updateCoilRequest), headers)
            .pipe(map((data: any) => {
                console.log(">[InventoryService.apiv2_InventoryUpdateCoil]> Transformation: " + transformer.description)
                return transformer.transform(data) as Coil
            }))
    }

    // - P A R T S
    public apiv1_InventoryGetParts(): Observable<PartListResponse> {
        const request = this.INVENTORYAPIV1 + '/inventory/parts'
        const transformer: ResponseTransformer = new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
            .setTransformation((entrydata: any): PartListResponse => {
                return new PartListResponse(entrydata)
            })
        return this.httpService.wrapHttpGETCall(request)
            .pipe(map((data: any) => {
                console.log(">[InventoryService.apiv1_GetInventoryParts]> Transformation: " + transformer.description)
                return transformer.transform(data) as PartListResponse
            }))
    }
    public apiv1_InventoryUpdatePart(updatingPart: Part): Observable<Part> {
        const request = this.INVENTORYAPIV1 + '/inventory/parts'
        const transformer = new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
            .setTransformation((entrydata: any): Part => {
                const targetPart: Part = new Part(entrydata)
                this.isolationService.successNotification('Pieza [' + targetPart.composePartIdentifier() + '] actualizada correctamente.', '/INVENTARIO/NUEVA PIEZA/OK')
                return targetPart
            })
        return this.httpService.wrapHttpPATCHCall(request, JSON.stringify(updatingPart))
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryUpdatePart_v1]> Transformation: " + transformer.description)
                return transformer.transform(data) as Part
            }))
    }
}
