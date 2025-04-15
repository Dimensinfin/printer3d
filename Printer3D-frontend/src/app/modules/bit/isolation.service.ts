// - CORE
import { Injectable } from '@angular/core'
import { Inject } from '@angular/core'
import { ToastrService } from 'ngx-toastr'
// - STORAGE
import { LOCAL_STORAGE } from 'ngx-webstorage-service'
import { SESSION_STORAGE } from 'ngx-webstorage-service'
import { StorageService } from 'ngx-webstorage-service'

/**
 * The responsibility for this service is to isolate the internal application api from the external implementation when using libraries that can change over time like the Toaster. It will also provide a simplification when accessing some common features like the environment, global functions or storage.
 */
@Injectable({
    providedIn: 'root'
})
export class IsolationService {
    constructor(
        @Inject(LOCAL_STORAGE) protected storage: StorageService,
        @Inject(SESSION_STORAGE) protected sessionStorage: StorageService,
        protected notifier: ToastrService
    ) { }

    // - S T O R A G E
    public getFromStorage(_key: string): any {
        return this.storage.get(_key);
    }
    public setToStorage(_key: string, _content: any): any {
        return this.storage.set(_key, _content)
    }
    public removeFromStorage(_key: string): any {
        this.storage.remove(_key);
    }
    public getFromSession(_key: string): any {
        console.log('>[AppStoreService.getFromSession]> key: ' + _key);
        return this.sessionStorage.get(_key);
    }
    public setToSession(_key: string, _content: any): any {
        return this.sessionStorage.set(_key, _content)
    }
    public removeFromSession(_key: string): any {
        this.sessionStorage.remove(_key);
    }

    // - N O T I F I C A T I O N S
    private notifierConfiguration: any = {
        autoDismiss: false,
        toastTimeout: 8000,
        newestOnTop: true,
        position: 'bottom-right',
        toastClass: 'notifier-box',
        titleClass: 'notifier-title',
        messageClass: 'notifier-message',
        animate: 'slideFromLeft'
    }
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

    // - R A M D O M
    public generateRandomNum(min: number, max: number) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    };
    public generateRandomString(length: number): string {
        var string = '';
        var letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz' //Include numbers if you want
        for (let i = 0; i < length; i++) {
            string += letters.charAt(Math.floor(Math.random() * letters.length));
        }
        return string;
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
    public dateAdd(date: any, interval: any, units: any) {
        var ret = new Date(date); //don't change original date
        var checkRollover = function () { if (ret.getDate() != date.getDate()) ret.setDate(0); };
        if (null != interval)
            switch (interval.toLowerCase()) {
                case 'year': ret.setFullYear(ret.getFullYear() + units); checkRollover(); break;
                case 'quarter': ret.setMonth(ret.getMonth() + 3 * units); checkRollover(); break;
                case 'month': ret.setMonth(ret.getMonth() + units); checkRollover(); break;
                case 'week': ret.setDate(ret.getDate() + 7 * units); break;
                case 'day': ret.setDate(ret.getDate() + units); break;
                case 'hour': ret.setTime(ret.getTime() + units * 3600000); break;
                case 'minute': ret.setTime(ret.getTime() + units * 60000); break;
                case 'second': ret.setTime(ret.getTime() + units * 1000); break;
            }
        return ret;
    }
    public deleteAllCookies(): void {
        let cookies = document.cookie.split(";");
        for (let i = 0; i < cookies.length; i++) {
            let cookie = cookies[i];
            let eqPos = cookie.indexOf("=");
            let name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
            document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
        }
    }
}
