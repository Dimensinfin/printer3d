// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Part } from '@domain/Part.domain';
import { PartRecord } from '@domain/PartRecord.domain';

export class RequestRequest{
    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
