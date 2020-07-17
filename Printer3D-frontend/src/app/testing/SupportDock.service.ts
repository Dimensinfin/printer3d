// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Feature } from '@domain/Feature.domain';

export class SupportDockService {
    public readDockConfiguration(transformer: ResponseTransformer): Observable<Feature[]> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('defaultdockfeaturemap')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public clean(): void {
    }

    private directAccessMockResource(dataIdentifier: string): any {
        let rawdata = require('./mock-data/' + dataIdentifier + '.json');
        console.log(">[SupportDockService.directAccessMockResource]> path: " + './mock-data/' + dataIdentifier + '.json');
        return rawdata
    }
}
