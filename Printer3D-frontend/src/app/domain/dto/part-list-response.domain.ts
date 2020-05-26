import { PartRecord } from '@domain/PartRecord.domain'
import { Node } from '../Node'

export class PartListResponse extends Node {
    public partCount: number = 0;
    public records: PartRecord[] = [];

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = 'PartListResponse';
        // Transform input records.
        if (null != this['parts']) {
            const recordList: PartRecord[] = [];
            for (let entry of this['parts'])
                recordList.push(new PartRecord(entry));
            this.records = recordList;
        }
    }
}
