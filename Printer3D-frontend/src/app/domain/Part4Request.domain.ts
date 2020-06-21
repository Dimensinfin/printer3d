// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { ICollaboration } from './interfaces/core/ICollaboration.interface';
import { Node } from './Node.domain';
import { Part } from './Part.domain';

export class Part4Request extends Part {
    private required: number;
    // private missing: number = 0;

    constructor(values: Object = {}) {
        super(values);
        Object.assign(this, values);
        this.jsonClass = 'Part4Request';
    }

    public getMissed(): number {
        return Math.min(0, this.getAvailable() - this.getRequired()) * -1
    }
    public getRequired(): number {
        return this.required
    }
    public setRequired(requiredCount: number): Part4Request {
        this.required = requiredCount
        return this
    }
    // public setMissing(missingParts: number): Part4Request {
    //     this.missing = missingParts
    //     return this
    // }
}
