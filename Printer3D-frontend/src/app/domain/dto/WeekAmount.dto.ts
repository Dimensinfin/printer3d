// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Part } from '@domain/Part.domain';
import { PartRecord } from '@domain/PartRecord.domain';

export class WeekAmount {
    private year: number
    private week: number
    private amount: number

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

    // - G E T T E R S
    public getWeek(): number {
        return this.week
    }
    public getAmount(): number {
        return this.amount
    }
}
