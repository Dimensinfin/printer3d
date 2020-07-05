export class ModelRequest {
    public id: string
    public label: string
    public partList: string[]
    public price: number
    public stockLevel: number
    public active: boolean = true

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
