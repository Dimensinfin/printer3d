// - DOMAIN
import { Node } from '../Node.domain';
import { RequestState } from '@domain/interfaces/EPack.enumerated';
import { RequestItem } from '@domain/production/RequestItem.domain';

export class CustomerRequest extends Node {
    private id: string;
    private label: string;
    private requestDate: string;
    private state: RequestState = RequestState.OPEN;
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
        return this.contents
    }
    public getContentCount(): number {
        let count: number = 0
        for (let content of this.contents)
            count += content.getQuantity()
        return count
    }
    public getAmount(): number {
        let amount: number = 0.0
        for (let content of this.contents)
            amount += content.getPrice() * content.getQuantity();
        return amount
    }
    public getState(): RequestState {
        return this.state;
    }
}
