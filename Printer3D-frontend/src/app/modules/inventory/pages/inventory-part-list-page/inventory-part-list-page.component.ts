import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'inventory-part-list-page',
    templateUrl: './inventory-part-list-page.component.html',
    styleUrls: ['./inventory-part-list-page.component.scss']
})
export class InventoryPartListPageComponent implements OnInit {
    public pagePath: string = '/Inventory/Part List';
    constructor() { }

    ngOnInit(): void {
    }

}
