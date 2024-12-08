// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { INode } from './interfaces/INode.interface';
import { ICollaboration } from './interfaces/core/ICollaboration.interface';
import { IExpandable } from './interfaces/core/IExpandable.interface';
import { ISelectable } from './interfaces/core/ISelectable.interface';

export class Node implements INode, ICollaboration, IExpandable, ISelectable {
    private uniqueIdentifier: string = uuidv4();
    protected jsonClass: string = 'Node';
    protected expanded: boolean = false;
    protected selected: boolean = false;

    constructor(values: object = {}) {
        Object.assign(this, values)
        this.jsonClass = 'Node'
        this.uniqueIdentifier = uuidv4()
        this.decode()
    }
    public decode():void{}

    protected isEmpty(target?: any): boolean {
        if (null == target) return true;
        if (Object.keys(target).length > 0) return false;
        return true;
    }

    // - I N O D E
    public getJsonClass(): string {
        return this.jsonClass;
    }
    public isActive(): boolean {
        return true;
    }
    // -  I C O L L A B O R A T I O N
    public collaborate2View(): ICollaboration[] {
        return [this];
    }

    // - I E X P A N D A B L E
    public isExpandable(): boolean {
        return false;
    }
    public isExpanded(): boolean {
        return this.expanded;
    }
    public collapse(): boolean {
        this.expanded = false;
        return this.isExpanded();
    }
    public expand(): boolean {
        this.expanded = true;
        return this.isExpanded();
    }
    public toggleExpanded() {
        this.expanded = !this.expanded;
        console.log('toggleExpanded: ' + this.expanded)
    }

    // - I S E L E C T A B L E
    public toggleSelected(): boolean {
        this.selected = !this.selected;
        return this.selected;
    }
    public isSelected(): boolean {
        if (this.selected) return true;
        else return false;
    }
    public select(): void {
        this.selected = true;
    }
    public unselect(): void {
        this.selected = false;
    }
    /**
     * Tests if two nodes are the same using the node unique identifier to detect equality.
     * @param target the other node to be tested for equeality
     */
    public equalRef(target: ISelectable): boolean {
        return (this.uniqueIdentifier === target.getUniqueId())
    }
    /**
     * Returns the unique identifier and generates a new one of not found. This unique identifier is used to detect identical nodes during selection.
     */
    public getUniqueId(): string {
        if (null == this.uniqueIdentifier)
            this.uniqueIdentifier = uuidv4()
        return this.uniqueIdentifier
    }
}
