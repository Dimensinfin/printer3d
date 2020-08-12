// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { RequestItem } from '@domain/production/RequestItem.domain';
import { IContent } from './interfaces/IContent.interface';

export class RequestForm {
    public id: string = uuidv4();
    public label: string;
    public requestDate: Date = new Date();
    public contents: RequestItem[] = [];

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

    public addContent(newContent: IContent): void {
        for (let part of this.contents) {
            if (newContent.getId() == part.getId()) { // If the part is on the list increment the counter
                part.incrementCount()
                return
            }
        }
        this.contents.push(new RequestItem({
            itemId: newContent.getId(),
            type: newContent.getType(),
            quantity: 1,
            missing: 0,
            required: 1,
            content: newContent
        }).setRequired(1))
    }
    public getRequestContents(): RequestItem[] {
        return this.contents
    }
    /**
     * Removes a unit from the counter for this part identifier. If the counter reached 0 then the part is removed from the list.
     * @param content2Remove Part to be removed
     */
    public removeContent(content2Remove: RequestItem): void {
        const newContentList: RequestItem[] = []
        for (let content of this.contents) {
            if (content2Remove.getId() == content.getId()) {
                if (content.decrementCount() > 0) newContentList.push(content)
            } else
                newContentList.push(content)
        }
        this.contents = newContentList;
    }
}
