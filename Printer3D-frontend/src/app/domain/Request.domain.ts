// - DOMAIN
import { Node } from './Node.domain';
import { RequestState } from '@domain/interfaces/EPack.enumerated';
import { PartRequest } from './dto/PartRequest.dto';
import { IPartProvider } from './interfaces/IPartProvider.interface';
import { Part } from './Part.domain';
import { Part4Request } from './Part4Request.domain';
import { RequestItem } from './RequestItem.domain';
import { ICollaboration } from './interfaces/core/ICollaboration.interface';

export class Request extends Node {
    private id: string;
    private label: string;
    private requestDate: string;
    private state: RequestState = RequestState.OPEN;
    // V2 comapibility fields
    protected contents: RequestItem[] = []

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = 'Request'
    }

    public getId(): string {
        return this.id;
    }
    public getLabel(): string {
        return this.label;
    }
    public getRequestDate(): Date {
        return new Date(this.requestDate);
    }
    public getContents(): RequestItem[] {
        // const contents: ICollaboration[] = []
        // for (const item of this.contents) {
        //     contents.push(item.getContent())
        // }
        return this.contents
    }

    public getContentCount(): number {
        let count: number = 0
        for (let content of this.contents)
            count += content.getQuantity()
        return count
    }
    public getAmount(): string {
        let amount: number = 0.0
        for (let content of this.contents)
            amount += content.getPrice() * content.getQuantity();
        return amount + ' €';
    }
    public getState(): RequestState {
        return this.state;
    }
}
