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
            const contents: RequestItem[] = []
            const itemContent = new RequestItem(item)
            const content: IContent = this.contentProvider.findById(itemContent.getId(), itemContent.getType())
            if (null != content) {
                itemContent.setContent(content) // Fill the stub with the real instance
                contents.push(itemContent)
            }
            onConstructionAsAny.contents = contents
        }
        return onConstruction
    }
}
