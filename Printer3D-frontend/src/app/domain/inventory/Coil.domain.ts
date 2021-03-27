// - CORE
import { IFiltered } from '@domain/interfaces/IFiltered.interface';
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Node } from '../Node.domain';

export class Coil extends Node implements IFiltered {
    public id: string;
    public material: string;
    public tradeMark: string
    public color: string;
    public weight: number;
    public label: string;
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
    /**
     * Generates a user identifer that will be recognizd by the operator as a valid identifier for a Coil.
     * @returns a user frindly Coil identifier that should be valid even it would not be unique.
     */
    public getCoilIdentifier(): string {
        return this.material + ':' + this.color;
    }

    // - I F I L T E R E D
    public getRepresentation(): string {
        return this.material + '|' + this.color + '|' + this.label
    }
}
