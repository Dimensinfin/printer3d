// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Part } from '@domain/Part.domain';

export class RequestForm {
    public id: string = uuidv4();
    public label: string;
    public requestDate: Date = new Date();
    public partList: Part[] = [];

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

    public addPart(newPart: Part): void {
        this.partList.push(newPart);
    }
    /**
     * Removed all the copies of a Part. It it was added 3 times the Part is removed 3 times
     * @param part2Remove Part to be removed
     */
    public removePart(part2Remove: Part): void {
        const newPartList: Part[] = []
        for (let part of this.partList) {
            if (JSON.stringify(part) == JSON.stringify(part2Remove)) continue;
            newPartList.push(part);
        }
        this.partList = newPartList;
    }
}
