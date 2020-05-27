// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Node } from './Node';
import { PartRecordConverter } from './PartRecord.converter';
import { UniqueSelectionDispatcher } from '@angular/cdk/collections';
import { EColorCode } from './interfaces/EPack.enumerated';
export class Roll extends Node {
    public id: string;
    public label: string;
    public material: string;
    public color: string;
    public weight: string = '1 Kg';

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = "Part";
    }
    public createNewId(): string {
        this.id = uuidv4();
        return this.id;
    }

}
