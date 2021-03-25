// - CORE
import { ComponentFactoryResolver, Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { Subject } from 'rxjs'
import { BehaviorSubject } from 'rxjs'
// - SERVICES
import { SupportHttpClientWrapperService } from './SupportHttpClientWrapperService.service'
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { PartListResponse } from '@domain/dto/PartListResponse.dto'
import { Part } from '@domain/inventory/Part.domain'
import { Coil } from '@domain/inventory/Coil.domain'
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto'
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto'
import { Machine } from '@domain/production/Machine.domain'
import { RequestForm } from '@domain/RequestForm.domain'
import { JobRequest } from '@domain/dto/JobRequest.dto'
import { ModelRequest } from '@domain/dto/ModelRequest.dto'
import { RequestRequest } from '@domain/dto/RequestRequest.dto'
import { HttpErrorResponse, HttpHeaders } from '@angular/common/http'
import { WeekAmount } from '@domain/dto/WeekAmount.dto'
import { Model } from '@domain/inventory/Model.domain'
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
import { IsolationService } from '@app/platform/isolation.service'
import { SupportBackendService } from './SupportBackend.service'
import { data } from 'cypress/types/jquery'
import { UpdateCoilRequest } from '@domain/dto/UpdateCoilRequest.dto'
import { CoilToUpdateCoilRequestConverter } from '@domain/converter/CoilToUpdateCoilRequest.converter'

@Injectable({
    providedIn: 'root'
})
export class SupportInventoryService extends SupportBackendService {
    private channel = new BehaviorSubject<any>([])
    constructor(
        protected httpService: SupportHttpClientWrapperService,
        protected isolationService: IsolationService) {
        super(httpService)
    }
    public apiv2_InventoryGetCoils(): Observable<Coil[]> {
        console.log('>[SupportInventoryService.apiv2_InventoryGetCoils]')
        const transformer: ResponseTransformer = new ResponseTransformer().setDescription('Transforms the list of Coils from the backend.')
            .setTransformation((entrydata: any): Coil[] => {
                const coilList: Coil[] = []
                if (Array.isArray(entrydata))
                    for (let entry of entrydata)
                        coilList.push(new Coil(entry))
                return coilList
            })
        return this.channel
    }
    public apiv2_InventoryUpdateCoil(updatingCoil: Coil): Observable<Coil> {
        console.log('>[SupportInventoryService.apiv2_InventoryUpdateCoil]')
        const updateCoilRequest: UpdateCoilRequest = new CoilToUpdateCoilRequestConverter().convert(updatingCoil)
        const transformer = new ResponseTransformer().setDescription('Do HTTP transformation to "Coil".')
            .setTransformation((entrydata: any): Coil => {
                const targetCoil: Coil = new Coil(entrydata)
                this.isolationService.successNotification('Rollo [' +
                    updatingCoil.getCoilIdentifier() +
                    '] actualizado correctamente.'
                    , '/INVENTARIO/ACTUALIZACION ROLLO/OK')
                return targetCoil
            })
        return this.channel
    }
    public postResponse(request: string): void {
        console.log('>[SupportInventoryService.postResponse]>Request: ' + request)
        switch (request) {
            case 'inventory.coils':
                const transformer: ResponseTransformer = new ResponseTransformer()
                    .setDescription('Transforms the list of Coils from the backend.')
                    .setTransformation((entrydata: any): Coil[] => {
                        const coilList: Coil[] = []
                        console.log('step 02')
                        if (Array.isArray(entrydata))
                            for (let entry of entrydata)
                                coilList.push(new Coil(entry))
                        return coilList
                    })
                const data = this.directAccessMockResource(request)
                console.log('[SupportInventoryService.postResponse.inventory.coils]>Data: ' + JSON.stringify(data))
                this.channel.next(transformer.transform(data))
                console.log('step 03')
                break
            case 'NOT_FOUND':
                const httpError = new HttpErrorResponse({
                    error: 'This is the error message',
                    headers: new HttpHeaders(),
                    status: 401,
                    statusText: "NOT_FOUND",
                    url: "url"
                })
                console.log('step 04')
                this.channel.error(httpError)
                break
        }
    }
    public setResponse(data: any): void {
        this.channel.next(data)
    }
    public complete() {
        console.log('>[SupportInventoryService.complete]')
        this.channel.complete()
    }
}
