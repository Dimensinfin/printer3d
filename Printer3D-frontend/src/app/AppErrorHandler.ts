// - CORE
import { Injectable } from '@angular/core';
import { ErrorHandler } from '@angular/core';
import { Injector } from '@angular/core';
// - DOMAIN
import { RollbarService } from 'angular-rollbar';

@Injectable()
export class AppErrorHandler implements ErrorHandler {
    constructor(private rollbar: RollbarService) { }

    handleError(err: any): void {
        this.rollbar.error(err);
    }
    private messageMap(exception: any): string {
        const messageMapped: boolean = false
        return 'Excepcion de tipo [' + exception.errorInfo + '] durante la ultima operacion.'
    }
}
