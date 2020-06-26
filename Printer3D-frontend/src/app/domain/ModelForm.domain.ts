export class ModelForm {
    public label; string
    public price: number
    public stockLevel: number

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
