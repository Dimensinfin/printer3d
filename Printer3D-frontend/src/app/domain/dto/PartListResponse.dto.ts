// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain'
import { Node } from '../Node'
import { Part } from '@domain/Part.domain';

export class PartListResponse {
    public partCount: number = 0;
    public records: Part[] = [];

    constructor(values: Object = {}) {
        Object.assign(this, values);
        // Transform input records.
        if (null != this['parts']) { // The API returns parts but we call it records
            const recordList: Part[] = [];
            for (let entry of this['parts'])
                recordList.push(new Part(entry));
            this.records = recordList;
        }
    }
}
