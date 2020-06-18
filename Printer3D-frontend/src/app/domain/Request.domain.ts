// - DOMAIN
import { Node } from './Node.domain';
import { RequestState } from '@domain/interfaces/EPack.enumerated';
import { PartRequest } from './dto/PartRequest.dto';

export class Request extends Node {
    private id: string;
    private label: string;
    private requestDate: string;
    private state: RequestState = RequestState.OPEN;
    private partList: PartRequest[] = [];

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.transformInput();
        this.jsonClass = 'Request'
    }
    private transformInput(): void {
        if (null != this.partList) {
            const newPartList: PartRequest[] = [];
            for (let entry of this.partList)
                newPartList.push(new PartRequest(entry))
            this.partList = newPartList;
        }
    }
}
