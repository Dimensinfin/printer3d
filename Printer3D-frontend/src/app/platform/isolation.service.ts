// - CORE
import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
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
        private notifier: ToastrService
    ) { }

    // - S T O R A G E
    public getFromStorage(key: string): string {
        return this.storage.get(key);
    }
    public setToStorage(key: string, object: any): void {
        this.storage.set(key, object);
    }
    public setToStorageObject(key: string, object: any): void {
        this.storage.set(key, JSON.stringify(object));
    }
    public removeFromStorage(key: string): string {
        const data = this.storage.get(key);
        this.storage.remove(key);
        return data;
    }

    // - N O T I F I C A T I O N S
    private notifierConfiguration: any = {
        autoDismiss: false,
        toastTimeout: 5000,
        newestOnTop: true,
        position: 'bottom-right',
        toastClass: 'notifier-box',
        titleClass: 'notifier-title',
        messageClass: 'notifier-message',
        animate: 'slideFromLeft'
    };
    public successNotification(message: string, title?: string, options?: any): void {
        // Join options configuration.
        let notConf;
        if (null != options) notConf = { ...this.notifierConfiguration, ...options };
        else notConf = this.notifierConfiguration;
        this.notifier.success(message, title, notConf);
    }
    public errorNotification(message: string, title?: string, options?: any): void {
        // Join options configuration.
        let notConf;
        if (null != options) notConf = { ...this.notifierConfiguration, ...options };
        else notConf = this.notifierConfiguration;
        notConf.toastTimeout = 15000;
        this.notifier.error(message, title, notConf);
    }
    public warningNotification(message: string, title?: string, options?: any): void {
        // Join options configuration.
        let notConf;
        if (null != options) notConf = { ...this.notifierConfiguration, ...options };
        else notConf = this.notifierConfiguration;
        this.notifier.warning(message, title, notConf);
    }
    public infoNotification(message: string, title?: string, options?: any): void {
        // Join options configuration.
        let notConf;
        if (null != options) notConf = { ...this.notifierConfiguration, ...options };
        else notConf = this.notifierConfiguration;
        this.notifier.info(message, title, notConf);
    }

    // - U T I L I T I E S
    /**
    * Adds time to a date. Modelled after MySQL DATE_ADD function.
    * Example: dateAdd(new Date(), 'minute', 30)  //returns 30 minutes from now.
    * https://stackoverflow.com/a/1214753/18511
    *
    * @param date  Date to start with
    * @param interval  One of: year, quarter, month, week, day, hour, minute, second
    * @param units  Number of units of the given interval to add.
    */
    public dateAdd(date, interval, units) {
        var ret = new Date(date); //don't change original date
        var checkRollover = function () { if (ret.getDate() != date.getDate()) ret.setDate(0); };
        switch (interval.toLowerCase()) {
            case 'year': ret.setFullYear(ret.getFullYear() + units); checkRollover(); break;
            case 'quarter': ret.setMonth(ret.getMonth() + 3 * units); checkRollover(); break;
            case 'month': ret.setMonth(ret.getMonth() + units); checkRollover(); break;
            case 'week': ret.setDate(ret.getDate() + 7 * units); break;
            case 'day': ret.setDate(ret.getDate() + units); break;
            case 'hour': ret.setTime(ret.getTime() + units * 3600000); break;
            case 'minute': ret.setTime(ret.getTime() + units * 60000); break;
            case 'second': ret.setTime(ret.getTime() + units * 1000); break;
            default: ret = undefined; break;
        }
        return ret;
    }
}
