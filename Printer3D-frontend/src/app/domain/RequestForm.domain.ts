// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Part } from '@domain/Part.domain';
import { Part4Request } from './Part4Request.domain';

export class RequestForm {
    public id: string = uuidv4();
    public label: string;
    public requestDate: Date = new Date();
    public partList: Part4Request[] = [];

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

    public addPart(newPart: Part): void {
        for (let part of this.partList) {
            if (newPart.getId() == part.getId()) { // If the part is on the list increment the counter
                part.incrementCount()
                return
            }
        }
        this.partList.push(new Part4Request(newPart).setRequired(1))
    }
    public getRequestParts(): Part4Request[] {
        return this.partList
    }
    /**
     * Removes a unit from the counter for this part identifier. If the counter reached 0 then the part is removed from the list.
     * @param part2Remove Part to be removed
     */
    public removePart(part2Remove: Part): void {
        const newPartList: Part4Request[] = []
        for (let part of this.partList) {
            if (part2Remove.getId() == part.getId()) {
                if (  part.decrementCount() >0 ) newPartList.push(part)
            } else
                newPartList.push(part)
        }
        this.partList = newPartList;
    }
}
