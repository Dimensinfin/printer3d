// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Part } from '@domain/inventory/Part.domain';
import { PartRecord } from '@domain/PartRecord.domain';

export class UpdateCoilRequest {
    public id: string
    public weight: number

    constructor(values: Object = {}) {
        Object.assign(this, values)
    }
}
