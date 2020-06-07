// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN

export class Part {
    public id: string;
    public label: string;
    public description: string;
    public material: string = 'PLA'
    public colorCode: string = 'INDEFINIDO';
    public cost: number;
    public price: number;
    public buildTime: number;
    public stockLevel: number = 1;
    public stockAvailable: number = 0;
    public imagePath: string;
    public modelPath: string;
    public active: boolean = true;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
    public createNewId(): string {
        this.id = uuidv4();
        return this.id;
    }
    public composePartIdentifier(): string {
        if (null != this.colorCode)
            return this.label + ':' + this.colorCode;
        else
            return this.label + ':' + 'INDEFINIDO';
    }
}
