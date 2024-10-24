// - DOMAIN
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface';
import { IContent } from '@domain/interfaces/IContent.interface';
import { RequestItem } from '@domain/production/RequestItem.domain';
import { Converter } from '@domain/interfaces/Converter.interface';
import { CustomerRequestResponse } from '../domain/dto/CustomerRequestResponse.dto';

export class DataToRequestConverter implements Converter<any, CustomerRequestResponse> {
    private contentProvider: IContentProvider

    constructor(contentProvider: IContentProvider) {
        this.contentProvider = contentProvider
    }
    /**
     * The backend data for the request does not come with the full instances data but only with the references and the types.
     * The Constructor will make use of the ContentProvider to locate that references and to add them to the request before going to render.
     * @param input The backend raw data. The references should be converted to instances.
     */
    public convert(input: any): CustomerRequestResponse {
        const onConstruction: CustomerRequestResponse = new CustomerRequestResponse(input)
        const onConstructionAsAny = onConstruction as any
        const contents: RequestItem[] = []
        for (const item of onConstructionAsAny.contents) {
            const itemContent = new RequestItem(item)
            const content: IContent = this.contentProvider.findById(itemContent.getId(), itemContent.getType())
            if (null != content) {
                itemContent.setContent(content) // Fill the stub with the real instance
                contents.push(itemContent)
            }
        }
        onConstructionAsAny.contents = contents
        return onConstruction
    }
}
