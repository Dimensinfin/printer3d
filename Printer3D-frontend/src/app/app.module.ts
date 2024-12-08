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
import { HttpErrorInterceptor } from './HttpErrorInterceptor';
import { FeatureDockModule } from './modules/innovative/feature-dock/feature-dock.module';
registerLocaleData(localeEs);

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
        AppCommonModule,
        SharedModule,
        RendersModule,
        InventoryModule,
        ProductionModule,
        FeatureDockModule
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
