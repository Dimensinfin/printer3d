// - CORE
import { Component } from '@angular/core'
import { OnInit } from '@angular/core'
// - DOMAIN
import { Feature } from '../../domain/Feature.domain'
import { DockService } from '../../service/dock.service'

@Component({
    selector: 'v1-dock',
    templateUrl: './v1-dock.component.html',
    styleUrls: ['./v1-dock.component.scss']
})
export class V1DockComponent implements OnInit {
    private configuredFeatures: Feature[] = []

    constructor(private dockService: DockService) { }

    public ngOnInit(): void {
        console.log('>[V1DockComponent.ngOnInit]')
        this.dockService.readDockConfiguration()
            .subscribe((featureList: Feature[]) => {
                console.log('<[V1DockComponent.ngOnInit.subscribe]')
                this.configuredFeatures = featureList
                console.log('->[V1DockComponent.ngOnInit]> Feature count: ' + this.configuredFeatures.length)
                this.dockService.clean()
            })
        console.log('<[V1DockComponent.ngOnInit]')
    }
    // - I N T E R A C T I O N
    public getActiveFeatures(): Feature[] {
        if (null != this.configuredFeatures) return this.configuredFeatures
        else return []
    }
}
