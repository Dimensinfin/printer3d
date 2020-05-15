// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';

@Component({
    selector: 'v1-dock',
    templateUrl: './v1-dock.component.html',
    styleUrls: ['./v1-dock.component.scss']
})
export class V1DockComponent implements OnInit {
    private activeFeatures: Feature[] = [];

    public ngOnInit(): void {
        this.activeFeatures.push(new Feature({ label: '/Inventory', active: true }));
        this.activeFeatures.push(new Feature({ label: '/New Part', active: false }));
    }
    public getActiveFeatures(): Feature[] {
        return this.activeFeatures;
    }
}
