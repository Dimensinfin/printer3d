// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { Constructor } from '@domain/interfaces/Constructor.interface';
import { Part } from '@domain/Part.domain';
import { Request } from '@domain/Request.domain';
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface';
import { IContent } from '@domain/interfaces/IContent.interface';
import { RequestItem } from '@domain/RequestItem.domain';
import { RequestContentType } from '@domain/interfaces/EPack.enumerated';

export class RequestConstructor implements Constructor<Request> {
    private contentProvider: IContentProvider

    constructor(contentProvider: IContentProvider) {
        this.contentProvider = contentProvider
    }
    /**
     * The backend data for the request does not come with the full instances data but only with the references and the types.
     * The Constructor will make use of the ContentProvider to locate that references and to add them to the request before going to render.
     * @param input The backend raw data. The references should be converted to instances.
     */
    public construct(input: any): Request {
        const onConstruction: Request = new Request(input)
        const onConstructionAsAny = onConstruction as any
        for (const item of onConstructionAsAny.contents) {
            const content: IContent = this.contentProvider.findById(item.getId(), item.getType())
            if (null != content) {
                item.setContent(content) // Fill the stub with the real instance
                if (item.getType() == RequestContentType.PART) // Check the existence of parts to complete the quatity required.
                    item.setMissing(item.getQuantity() - content.getStock())
                // item.setMissing(item.getQuantity() - content.getStock()) // This data should be calculated at the backend
            }
        }
        return onConstruction
    }
}
