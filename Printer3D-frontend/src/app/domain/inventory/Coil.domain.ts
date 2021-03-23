// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Node } from '../Node.domain';

export class Coil extends Node {
    public id: string;
    public material: string;
    public color: string;
    public weight: number;
    public colorSet: string;
    public active: boolean = true

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = 'Coil';
    }

    public createNewId(): string {
        this.id = uuidv4();
        return this.id;
    }
    public getId(): string {
        return this.id
    }
    public getCoilIdentifier(): string {
        return this.material + ':' + this.color;
    }
}
