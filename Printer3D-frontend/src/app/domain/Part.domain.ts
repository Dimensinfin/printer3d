// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { ICollaboration } from './interfaces/core/ICollaboration.interface';
import { Node } from './Node.domain';

export class Part extends Node {
    public id: string;
    public label: string;
    public description: string;
    public material: string = 'PLA'
    public color: string = 'INDEFINIDO';
    public cost: number;
    public price: number;
    public buildTime: number;
    public stockLevel: number = 1;
    public stockAvailable: number = 0;
    public imagePath: string;
    public modelPath: string;
    public active: boolean = true;

    constructor(values: Object = {}) {
        super(values);
        Object.assign(this, values);
        this.jsonClass = 'Part';
    }

    // - G E T T E R S   &   S E T T E R S
    public getId(): string {
        return this.id;
    }
    public createNewId(): string {
        this.id = uuidv4();
        return this.id;
    }
    public composePartIdentifier(): string {
        if (null != this.color)
            return this.label + ':' + this.color;
        else
            return this.label + ':' + 'INDEFINIDO';
    }
    public isExpandable(): boolean {
        return false;
    }
    public isActive(): boolean {
        return this.active;
    }
}
