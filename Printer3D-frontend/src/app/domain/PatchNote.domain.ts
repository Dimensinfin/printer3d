import { Node } from './Node.domain';
export class PatchNote extends Node {
    public note: string
 
    constructor(values: Object = {}) {
        super()
        Object.assign(this, values)
        this.jsonClass = 'PatchNote'
    }

    public getContent(): string {
        return this.note
    }
}
