// - DOMAIN
import { Node } from './node';
import { PartRecordConverter } from './PartRecord.converter';
import { UniqueSelectionDispatcher } from '@angular/cdk/collections';

export class PartRecord extends Node {
    public id: string;
    public label: string;
    public description: string;
    public imagePath: string;
    public modelPath: string;
    public stockLevel: number = 1;
    public cost: number;
    public price: number = 1;
    public active : boolean = true;

    constructor(values: Object = {}) {
        super();
        const convertedValues: object = new PartRecordConverter().convertInstance(values);
        Object.assign(this, convertedValues);
        this.jsonClass = "PartRecord";
    }
}
