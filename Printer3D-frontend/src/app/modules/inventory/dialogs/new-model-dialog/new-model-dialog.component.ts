import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'new-model-dialog',
    templateUrl: './new-model-dialog.component.html',
    styleUrls: ['./new-model-dialog.component.scss']
})
export class NewModelDialogComponent implements OnInit {
    public model: any = {}
    constructor() { }

    ngOnInit(): void {
    }
    public closeModal() { }
    public saveModel() { }
}
