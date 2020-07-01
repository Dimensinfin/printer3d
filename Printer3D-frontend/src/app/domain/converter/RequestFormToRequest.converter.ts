// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { RequestForm } from '@domain/RequestForm.domain';
import { RequestRequest } from '@domain/dto/RequestRequest.dto';

export class RequestFormToRequestConverter implements Converter<RequestForm, RequestRequest> {
    public convert(input: RequestForm): RequestRequest {
       const requestContents: any[] = []
        for (const content of input.getRequestContents())
            requestContents.push({
                itemId: content.getId(),
                type: content.getType(),
                quantity: content.getQuantity(),
                missing: 0
            })
        return new RequestRequest({
            id: input.id,
            label: input.label,
            requestDate: input.requestDate,
            state: 'OPEN',
            contents: requestContents
        })
    }
}
