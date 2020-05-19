// - DOMAIN
import { Node } from './node';
import { PartRecordConverter } from './PartRecord.converter';
import { UniqueSelectionDispatcher } from '@angular/cdk/collections';

export class PartRecord extends Node {
    public id : string;
    public label: string;
    public description: string;
    public cost: string = "0.75";
    public pvp: string = "4.0€";

    constructor(values: Object = {}) {
        super();
        const convertedValues: object = new PartRecordConverter().convertInstance(values);
        Object.assign(this, convertedValues);
        this.jsonClass = "PartRecord";
    }
}
