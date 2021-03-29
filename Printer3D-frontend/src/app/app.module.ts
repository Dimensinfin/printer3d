// - CORE MODULES
import { ErrorHandler } from '@angular/core';
import { NgModule } from '@angular/core';
// - BROWSER & ANIMATIONS
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// - ROUTING
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
// - ADDITIONAL PACKAGES
import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import { ToastrModule } from 'ngx-toastr';
import { NgDragDropModule } from 'ng-drag-drop';
import { RollbarModule } from 'angular-rollbar'
import { RollbarService } from 'angular-rollbar'
// - SERVICES
import { IsolationService } from './platform/isolation.service';
import { DialogFactoryService } from './services/dialog-factory.service';
import { BackendService } from './services/backend.service';
// - COMPONENTS-CORE
import { AppComponent } from './app.component';

// - APPLICATION MODULES
import { AppCommonModule } from './modules/common/common.module';
import { SharedModule } from './modules/shared/shared.module';
import { RendersModule } from './modules/renders/renders.module';
import { InventoryModule } from './modules/inventory/inventory.module';
import { ProductionModule } from './modules/production/production.module';

// - LOCALES
import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
import { AppErrorHandler } from './AppErrorHandler';
import { HttpErrorInterceptor } from './HttpErrorInterceptor';
registerLocaleData(localeEs);

// - ERROR INTERCEPTION
// import * as Rollbar from 'rollbar';
// import { rollbarConfig } from '@app/rollbar-errorhandler.service';
// import { RollbarService } from '@app/rollbar-errorhandler.service';
// import { ErrorHandler } from '@angular/core';
// import { RollbarErrorHandler } from '@app/rollbar-errorhandler.service';
// import { HttpErrorInterceptor } from './security/httpErrorProcessing.interceptor';

@NgModule({
    imports: [
        // - BROWSER & ANIMATIONS
        FormsModule,
        ReactiveFormsModule,
        BrowserModule,
        BrowserAnimationsModule,
        // - ROUTING
        RouterModule,
        AppRoutingModule,
        // - ADDITIONAL MODULES
        HttpClientModule,
        ToastrModule.forRoot(),
        MatDialogModule,
        NgDragDropModule.forRoot(),
        // RollbarModule.forRoot({
        //     accessToken: '4b7515a4ac41496b931963f64ef666e2'
        // }),
        // - APPLICATION MODULES
        AppCommonModule,
        SharedModule,
        RendersModule,
        InventoryModule,
        ProductionModule
    ],
    declarations: [
        AppComponent,
    ],
    providers: [
        // - SERVICES
        { provide: IsolationService, useClass: IsolationService },
        { provide: BackendService, useClass: BackendService },
        { provide: DialogFactoryService, useClass: DialogFactoryService },
        // - ERROR INTERCEPTION
        // { provide: ErrorHandler, useClass: AppErrorHandler },
        // - HTTP INTERCEPTION
        { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true }
    ],
    bootstrap: [AppComponent],
})
export class AppModule { }
