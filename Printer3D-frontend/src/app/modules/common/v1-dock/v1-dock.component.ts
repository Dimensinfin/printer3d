// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - ROUTER
import { Router } from '@angular/router';
// - SERVICES
import { DockService } from '@app/services/dock.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { AppComponent } from '@app/app.component';

const featureTransformer = new ResponseTransformer().setDescription('Do property transformation to "Feature" list.')
    .setTransformation((entrydata: any): Feature[] => {
        let results: Feature[] = [];
        if (entrydata instanceof Array) {
            for (let key in entrydata)
                results.push(new Feature(entrydata[key]));
        }
        return results;
    });

@Component({
    selector: 'v1-dock',
    templateUrl: './v1-dock.component.html',
    styleUrls: ['./v1-dock.component.scss']
})
export class V1DockComponent implements OnInit {
    private configuredFeatures: Feature[] = [];

    constructor(
        private dockService: DockService) {
    }
    
    public ngOnInit(): void {
        console.log('><[V1DockComponent.ngOnInit]');
        this.dockService.readDockConfiguration(
            new ResponseTransformer().setDescription('Do property transformation to "Feature" list.')
                .setTransformation((entrydata: any): Feature[] => {
                    let results: Feature[] = [];
                    if (entrydata instanceof Array) {
                        for (let key in entrydata)
                            results.push(new Feature(entrydata[key]));
                    }
                    return results;
                }))
            .subscribe((featureList: Feature[]) => {
                this.configuredFeatures = featureList
                console.log('->[V1DockComponent.ngOnInit]> Feature count: ' + this.configuredFeatures.length)
                this.dockService.clean()
            });
    }
    // - I N T E R A C T I O N
    public getActiveFeatures(): Feature[] {
        if (null != this.configuredFeatures) return this.configuredFeatures;
        else return [];
    }
}
