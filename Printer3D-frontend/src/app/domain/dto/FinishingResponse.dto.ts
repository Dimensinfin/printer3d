import { Node } from '../Node'

export class FinishingResponse {
    public materials: any[] = [];

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
    public getMaterials(): any[] {
        return this.materials;
    }
}
