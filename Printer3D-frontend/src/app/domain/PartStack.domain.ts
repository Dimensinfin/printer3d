// - DOMAIN
import { Part } from './inventory/Part.domain';

export class PartStack extends Part {
    private stackCount: number = 1

    constructor(values: Object = {}) {
        super()
        Object.assign(this, values)
        this.jsonClass='PartStack'
    }

    public getCount(): number {
        return this.stackCount
    }
    public incrementStack(): number {
        this.stackCount++
        return this.stackCount
    }
    public decrementStack(): number {
        this.stackCount--
        return this.stackCount
    }
}
