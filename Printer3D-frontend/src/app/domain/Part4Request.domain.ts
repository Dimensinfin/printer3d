// - DOMAIN
import { Part } from './inventory/Part.domain';

export class Part4Request extends Part {
    private required: number = 0;

    constructor(values: Object = {}) {
        super(values);
        Object.assign(this, values);
        this.jsonClass = 'Part4Request';
    }

    public getMissed(): number {
        return Math.abs(Math.min(0, this.getAvailable() - this.getRequired()))
    }
    public getRequired(): number {
        return this.required
    }
    public setRequired(requiredCount: number): Part4Request {
        this.required = requiredCount
        return this
    }
    public decrementCount(): number {
        this.required--
        return this.required
    }
    public incrementCount(): number {
        this.required++
        return this.required
    }
}
