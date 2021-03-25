// - CORE
import { ComponentFactoryResolver, Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { Subject } from 'rxjs'
import { BehaviorSubject } from 'rxjs'
import { v4 as uuidv4 } from 'uuid'
// - SERVICES
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { Coil } from '@domain/inventory/Coil.domain'

@Injectable({
    providedIn: 'root'
})
export class SupportInventoryService {
    private identifier: string
    constructor() {
        console.log('>[SupportInventoryService.<constructor>]')
        this.identifier = uuidv4()
    }
    public printIdentifier(): void {
        console.log('>[SupportInventoryService.printIdentifier]>Identifier: ' + this.identifier)
    }
    public apiv2_InventoryGetCoils(): Observable<Coil[]> {
        console.log('>[SupportInventoryService.apiv2_InventoryGetCoils]')
        this.printIdentifier()
        return new Observable(observer => {
            setTimeout(() => {
                observer.next([])
            }, 100)
        })
    }
    public apiv2_InventoryUpdateCoil(updatingCoil: Coil): Observable<Coil> {
        console.log('>[SupportInventoryService.apiv2_InventoryUpdateCoil]')
        return new Observable(observer => {
            setTimeout(() => {
                observer.next(undefined)
            }, 100)
        })
    }

    // - D A T A   P R O C E S S I N G
    public prepareResponse(request: string, responseRaw: any): any {
        console.log('>[SupportInventoryService.prepareResponse]>Request: ' + request)
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
                return new Observable(observer => {
                    setTimeout(() => {
                        observer.next(transformer.transform(responseRaw))
                    }, 100)
                })
            case 'inventory.update.coil':
                return new Observable(observer => {
                    setTimeout(() => {
                        observer.next(new Coil(responseRaw))
                    }, 100)
                })
            case 'Throw Error':
                return new Observable(observer => {
                    setTimeout(() => {
                        observer.error(responseRaw)
                    }, 100)
                })
        }

    }
    public directAccessMockResource(dataIdentifier: string): any {
        let rawdata = require('../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json')
        console.log(">[SupportBackendService.directAccessMockResource]> path: " +
            '../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json')
        return rawdata
    }
}
