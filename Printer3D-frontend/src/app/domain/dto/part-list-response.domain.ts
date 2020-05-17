import { PartRecord } from '@domain/PartRecord.domain'
import { Node } from '../node'

export class PartListResponse extends Node {
    public partCount: number = 0;
    public records: PartRecord[] = [];

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = 'PartListResponse';
        // Transform input records.
        if (null != this.records) {
            const recordList: PartRecord[] = [];
            for (let entry of this.records)
                recordList.push(new PartRecord(entry));
            this.records = recordList;
        }
    }
}
