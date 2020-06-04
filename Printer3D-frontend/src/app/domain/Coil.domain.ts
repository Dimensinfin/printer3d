// - CORE
import { v4 as uuidv4 } from 'uuid';

export class Coil {
    public id: string;
    public material: string;
    public color: string;
    public weight: number;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
    public createNewId(): string {
        this.id = uuidv4();
        return this.id;
    }
    public getCoildIdentifier(): string {
        return this.material + ':' + this.color;
    }
}
