// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { Part } from '@domain/Part.domain';
import { PartStack } from '@domain/PartStack.domain';

@Component({
    selector: 'v1-drop-part-panel',
    templateUrl: './v1-drop-part-panel.component.html',
    styleUrls: ['./v1-drop-part-panel.component.scss']
})
export class V1DropPartPanelComponent implements OnInit {
    public self: V1DropPartPanelComponent
    public droppedParts: PartStack[] = []

    public ngOnInit(): void {
        this.self = this
    }
    
    public addPart(newPart: Part): void {
        for (let part of this.droppedParts) {
            if (newPart.getId() == part.getId()) { // If the part is on the list increment the stack
                part.incrementStack()
                return
            }
        }
        this.droppedParts.push(new PartStack(newPart)) // Add the new part to the list
    }
    /**
     * Removes a unit from the counter for this part identifier. If the counter reached 0 then the part is removed from the list.
     * @param part2Remove Part to be removed
     */
    public removePart(part2Remove: Part): void {
        const newPartList: PartStack[] = []
        for (let part of this.droppedParts) {
            if (part2Remove.getId() == part.getId()) {
                if (part.decrementStack() > 0) newPartList.push(part)
            } else
                newPartList.push(part)
        }
        this.droppedParts = newPartList;
    }
    public onDrop(drop: any): void {
        console.log('>[V1DropPartPanelComponent.onDrop]> Drop: ' + JSON.stringify(drop))
        this.addPart(drop.dragData as Part)
        console.log('<[V1DropPartPanelComponent.onDrop]')
    }
    public getDroppedParts(): PartStack[] {
        return this.droppedParts
    }
    /**
     * Constructs a list of the contained Part ids. If a Part is 5 times then that id is inserted 5 times.
     */
    public getPartIdList(): string[] {
        const ids: string[] = []
        for (let stack of this.droppedParts)
            for (let index = 0; index < stack.getCount(); index++)
                ids.push(stack.getId())
        return ids
    }
}
