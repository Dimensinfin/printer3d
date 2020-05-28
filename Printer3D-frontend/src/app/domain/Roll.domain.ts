// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Node } from './Node';

export class Roll {
    public id: string;
    public label: string;
    public material: string;
    public color: string;
    public weight: number = 1000;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
    public createNewId(): string {
        this.id = uuidv4();
        return this.id;
    }

}
