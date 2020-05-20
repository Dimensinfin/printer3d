// - CORE
import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
// - STORAGE
import { LOCAL_STORAGE } from 'ngx-webstorage-service';
import { SESSION_STORAGE } from 'ngx-webstorage-service';
import { StorageService } from 'ngx-webstorage-service';

@Injectable({
    providedIn: 'root'
})
export class IsolationService {

    constructor(
        @Inject(LOCAL_STORAGE) protected storage: StorageService,
        @Inject(SESSION_STORAGE) protected sessionStorage: StorageService,
    ) { }
    
    // - S T O R A G E
    public getFromStorage(key: string): string {
        return this.storage.get(key);
    }
    public setToStorageObject(key: string, object: any): void {
        this.storage.set(key, JSON.stringify(object));
    }
}
