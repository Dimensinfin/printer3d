// - CORE
import { v4 as uuidv4 } from 'uuid';

export class ModelForm {
    private id
    public label; string
    public price: number
    public stockLevel: number

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

    public getId(): string {
        return this.id
    }
    public generateNewId(): ModelForm {
        this.id = uuidv4()
        return this
    }
}
