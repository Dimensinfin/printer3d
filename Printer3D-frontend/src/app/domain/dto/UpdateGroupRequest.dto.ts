// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Part } from '@domain/Part.domain';
import { PartRecord } from '@domain/PartRecord.domain';

export class UpdateGroupRequest {
    public label: string

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
