import { Component, OnInit } from '@angular/core';
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';

@Component({
    selector: 'v1-part-stack',
    templateUrl: './v1-part-stack-render.component.html',
    styleUrls: ['./v1-part-stack-render.component.scss']
})
export class V1PartStackRenderComponent extends NodeContainerRenderComponent {

    constructor() {
        super()
    }

    ngOnInit(): void {
    }

}
