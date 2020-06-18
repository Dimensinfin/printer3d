// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Coil } from '@domain/Coil.domain';
import { CoilRecord } from '@domain/CoilRecord.domain';
import { RequestState } from '@domain/interfaces/EPack.enumerated';
import { PartRequest } from './PartRequest.dto';

export class Request {
    private id : string;
    private label: string;
    private requestDate : string;
    private state: RequestState=RequestState.OPEN;
    private partList : PartRequest[]=[];

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
