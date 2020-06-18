// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Coil } from '@domain/Coil.domain';
import { CoilRecord } from '@domain/CoilRecord.domain';
import { RequestForm } from '@domain/RequestForm.domain';

export class PartRequest {
    private partId: string;
    private quantity: number = 1;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

    public getPartId(): string {
        return this.partId;
    }
    public getQuantity(): number {
        return this.quantity;
    }
    public increaseCount(): number {
        this.quantity++;
        return this.quantity;
    }
}
