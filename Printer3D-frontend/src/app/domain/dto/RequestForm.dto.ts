// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Part } from '@domain/Part.domain';

export class RequestForm {
    public id: string = uuidv4();
    public label: string;
    public requestDate: Date = new Date();
    public partsToServe: Part[] = [];

    public addPart(newPart: Part): void {
        this.partsToServe.push(newPart);
    }
    public removePart(part: Part): Part {
        // return this.partsToServe.reduce
        // this.items = this.items.filter(item => item.item_id !== item.item_id);
        return null;
    }
}
