// - CORE
import { Injectable } from '@angular/core';
import * as jwt_decode from 'jwt-decode';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SupportIsolationService {
    private storage = new Map();

    // - M O C K   D A T A   A C C E S S
    public directAccessMockResource(dataIdentifier: string): any {
        console.log(">>[SupportIsolationService.directAccessMockResource]> dataIdentifier: " + dataIdentifier);
        let rawdata = require('./mock-data' + dataIdentifier.toLowerCase() + '.json');
        return rawdata;
    }

    // - S T O R A G E
    public getFromStorage(_key: string): any {
        return this.storage.get(_key);
    }
    public setToStorage(_key: string, _content: any): any {
        return this.storage.set(_key, _content)
    }
    public setToStorageObject(key: string, object: any): void {
        this.setToStorage(key, JSON.stringify(object));
    }
    public removeFromStorage(_key: string): void {
        this.storage.set(_key, null);
    }
    public getFromSession(_key: string): any {
        return this.storage.get(_key);
    }
    public setToSession(_key: string, _content: any): any {
        console.log('--[SupportIsolationService.setToSession]> Storing: ' + _key + '=' +
            JSON.stringify(_content));
        return this.storage.set(_key, JSON.stringify(_content))
    }
    public removeFromSession(_key: string): any {
        this.storage.set(_key, null);
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

    // - J W T
    public JWTDecode(token: string): any {
        return jwt_decode(token);
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

    // - N O T I F I C A T I O N S
    private notifierConfiguration: any = {
        toastTimeout: 5000,
        newestOnTop: true,
        position: 'bottom-right',
        messageClass: 'notifier-message',
        titleClass: 'notifier-title',
        animate: 'slideFromLeft'
    };
    public successNotification(message: string, title?: string, options?: any): void {
    }
    public errorNotification(message: string, title?: string, options?: any): void {
    }
    public warningNotification(message: string, title?: string, options?: any): void {
    }
    public infoNotification(message: string, title?: string, options?: any): void {
    }
}
