// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Injectable } from '@angular/core';
import { ErrorHandler } from '@angular/core';
import { Injector } from '@angular/core';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { GridColumn } from '@domain/GridColumn.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { PartContainer } from '@domain/PartContainer.domain';
import { environment } from '@env/environment';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { BackgroundEnabledComponent } from './modules/shared/core/background-enabled/background-enabled.component';
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto';
import { IsolationService } from './platform/isolation.service';
import { HttpErrorResponse } from '@angular/common/http';
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
