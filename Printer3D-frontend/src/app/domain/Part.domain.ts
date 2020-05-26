// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Node } from './Node';
import { PartRecordConverter } from './PartRecord.converter';
import { UniqueSelectionDispatcher } from '@angular/cdk/collections';
import { EColorCode } from './interfaces/EPack.enumerated';

export class Part extends Node {
    public id: string;
    public label: string;
    public description: string;
    public colorCode: string;
    public cost: number;
    public price: number;
    public buildTime: number;
    public stockLevel: number = 1;
    public stockAvailable: number = 0;
    public imagePath: string;
    public modelPath: string;
    public active: boolean = true;

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = "Part";
    }
    public createNewId(): void {
        this.id = uuidv4();
    }
    private composePartIdentifier(): string {
        return this.label + ':' + this.colorCode;
    }
}
