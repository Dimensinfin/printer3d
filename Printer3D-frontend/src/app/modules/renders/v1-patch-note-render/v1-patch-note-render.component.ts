import { Component, OnInit } from '@angular/core';
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';

@Component({
    selector: 'v1-patch-note',
    templateUrl: './v1-patch-note-render.component.html',
    styleUrls: ['./v1-patch-note-render.component.scss']
})
export class V1PatchNoteRenderComponent extends NodeContainerRenderComponent {
    public getContent(): string {
        const note = this.node as any
        if (note) return note.getContent()
        else return '-'
    }
}
