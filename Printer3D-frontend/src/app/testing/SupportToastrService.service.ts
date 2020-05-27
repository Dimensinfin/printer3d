import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class SupportToastrService {
    public successCount: number = 0;
    public errorCount: number = 0;
    public warningCount: number = 0;
    public infoCount: number = 0;

    public clea(): void {
        this.successCount = 0;
        this.errorCount = 0;
        this.warningCount = 0;
        this.infoCount = 0;
    }
    public success(message: string, title?: string, options?: any): void {
        this.successCount++;
        console.log('[SupportToastrService.success]> successCount: ' + this.successCount)
    }
    public error(message: string, title?: string, options?: any): void {
        this.errorCount++;
    }
    public warning(message: string, title?: string, options?: any): void {
        this.warningCount++;
    }
    public info(message: string, title?: string, options?: any): void {
        this.infoCount++;
    }
}
