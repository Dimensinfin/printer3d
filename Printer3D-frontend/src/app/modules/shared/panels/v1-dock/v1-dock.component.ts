// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - SERVICES
import { AppStoreService } from '@app/services/app-store.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';

@Component({
    selector: 'v1-dock',
    templateUrl: './v1-dock.component.html',
    styleUrls: ['./v1-dock.component.scss']
})
export class V1DockComponent implements OnInit {
    private activeFeatures: Feature[] = [];
    constructor(private appStore: AppStoreService) { }
    public ngOnInit(): void {
        this.activeFeatures.push(new Feature({ label: '/Inventory', active: true }));
        this.activeFeatures.push(new Feature({ label: '/New Part', active: false }));
        this.appStore.fireAccessDockConfiguration();
        this.appStore.synchronize2DockConfiguration()
            .subscribe((configuration: Feature[]) => {
                this.activeFeatures = configuration;
            });
    }
    public getActiveFeatures(): Feature[] {
        if (null != this.activeFeatures) return this.activeFeatures;
        else return [];
    }
}
