// - CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';

@Component({
    selector: 'v1-page-path-panel',
    templateUrl: './v1-page-path-panel.component.html',
    styleUrls: ['./v1-page-path-panel.component.scss']
})
export class V1PagePathPanelComponent {
    @Input() path: string = '/';

}
