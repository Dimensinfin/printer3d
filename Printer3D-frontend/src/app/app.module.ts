// - CORE MODULES
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
// - SERVICES
import { IsolationService } from './platform/isolation.service';
import { AppStoreService } from './services/app-store.service';
import { DialogFactoryService } from './factory/dialog-factory.service';
import { BackendService } from './services/backend.service';
// - COMPONENTS-CORE
import { AppComponent } from './app.component';

// - APPLICATION MODULES
import { SharedModule } from './modules/shared/shared.module';
import { InventoryModule } from './modules/inventory/inventory.module';
import { MaterialsModule } from './modules/materials/materials.module';

// - LOCALES
import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
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
        // - APPLICATION MODULES
        SharedModule,
        InventoryModule,
        MaterialsModule
    ],
    declarations: [
        AppComponent,
    ],
    providers: [
        // - SERVICES
        { provide: IsolationService, useClass: IsolationService },
        { provide: AppStoreService, useClass: AppStoreService },
        { provide: DialogFactoryService, useClass: DialogFactoryService },
        { provide: BackendService, useClass: BackendService },
        // { provide: AuthenticationService, useClass: AuthenticationService },
        // - ERROR INTERCEPTION
        // { provide: ErrorHandler, useClass: RollbarErrorHandler },
        // { provide: RollbarService, useFactory: rollbarFactory },
        // - HTTP INTERCEPTION
        // { provide: HTTP_INTERCEPTORS, useClass: AuthorizationInterceptor, multi: true },
        // { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
