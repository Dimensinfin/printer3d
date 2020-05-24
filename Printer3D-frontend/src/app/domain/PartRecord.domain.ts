// - DOMAIN
import { Node } from './node';
import { PartRecordConverter } from './PartRecord.converter';
import { UniqueSelectionDispatcher } from '@angular/cdk/collections';
import { EColorCode } from './interfaces/EPack.enumerated';

export class PartRecord extends Node {
    public id: string;
    public label: string;
    public description: string;
    public colorCode: EColorCode;
    public cost: number;
    public price: number = 1;
    public stockLevel: number = 1;
    public stockAvailable;
    public imagePath: string;
    public modelPath: string;
    public active: boolean = true;

    constructor(values: Object = {}) {
        super();
        const convertedValues: object = new PartRecordConverter().convertInstance(values);
        Object.assign(this, convertedValues);
        this.jsonClass = "PartRecord";
    }
}
