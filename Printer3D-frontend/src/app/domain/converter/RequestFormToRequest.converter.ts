// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Coil } from '@domain/Coil.domain';
import { CoilRecord } from '@domain/CoilRecord.domain';
import { RequestForm } from '@domain/RequestForm.domain';
import { Request } from '@domain/dto/Request.dto';
import { PartRequest } from '@domain/dto/PartRequest.dto';
import { RequestState } from '@domain/interfaces/EPack.enumerated';

export class RequestFormToRequestConverter implements Converter<RequestForm, Request> {
    public convert(input: RequestForm): Request {
        let partMap: Map<string, PartRequest> = new Map<string, PartRequest>();
        // console.log('>[RequestFormToRequestConverter.convert]> input.partList: ' + JSON.stringify(input.partList))
        for (let part of input.partList) {
            console.log('>[RequestFormToRequestConverter.convert]> partId: ' + part.getId())
            console.log('>[RequestFormToRequestConverter.convert]> partMap: ' + JSON.stringify(partMap))
            let hit = partMap.get(part.getId())
            console.log('>[RequestFormToRequestConverter.convert]> hit: ' + hit)
            if (null == hit) {
                hit = new PartRequest({
                    partId: part.getId(),
                    quantity: 0
                })
                console.log('>[RequestFormToRequestConverter.convert]> PartRequest: ' + JSON.stringify(hit))
                partMap.set(part.getId(), hit)
                console.log('>[RequestFormToRequestConverter.convert]> partMap2: ' + JSON.stringify(partMap))
            }
            hit.increaseCount()
        }
        console.log('>[RequestFormToRequestConverter.convert]> partMap: ' + JSON.stringify(partMap))
        const partReferences: PartRequest[] = []
        for (let id of partMap.values())
            partReferences.push(id)
        return new Request({
            id: input.id,
            label: input.label,
            requestDate: input.requestDate,
            state: RequestState.OPEN,
            partList: partReferences
        })
    }
}
