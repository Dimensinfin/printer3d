// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { ICollaboration } from './interfaces/core/ICollaboration.interface';
import { Node } from './Node.domain';
import { IContent } from './interfaces/IContent.interface';

export class Part extends Node implements IContent {
    public id: string;
    public label: string;
    public description: string;
    public material: string = 'PLA'
    public color: string;
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
    public getPrice(): number {
        return this.price;
    }
    public createNewId(): string {
        this.id = uuidv4();
        return this.id;
    }
    public getAvailable(): number {
        return this.stockAvailable
    }
    public composePartIdentifier(): string {
        return this.label + ':' + this.color;
    }
    public isExpandable(): boolean {
        return false;
    }
    public isActive(): boolean {
        return this.active;
    }

    // - I C O N T E N T
    /**
     * Return the number of instances that were found on the stock record.
     */
    public getStock(): number {
        return this.stockAvailable
    }
}
