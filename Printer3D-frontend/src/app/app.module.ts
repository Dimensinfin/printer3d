// - CORE MODULES
import { NgModule } from '@angular/core';
// - BROWSER & ANIMATIONS
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// - HTTP CLIENT
import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
// - ROUTING
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
// - SERVICES
import { IsolationService } from './platform/isolation.service';
// import { AppStoreService } from './services/appstore.service';
// import { BackendService } from './services/backend.service';
// - COMPONENTS-CORE
import { AppComponent } from './app.component';

// - APPLICATION MODULES
// import { DashboardModule } from './modules/dashboard/dashboard.module';

// - LOCALES
import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
// import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
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
        // - HTTP CLIENT
        HttpClientModule,
        // - APPLICATION MODULES
        // DashboardModule,
        // - ROUTING
        RouterModule,
        AppRoutingModule
    ],
    declarations: [
        AppComponent,
        // DashboardComponent,
        DashboardPageComponent
    ],
    providers: [
        // - SERVICES
        { provide: IsolationService, useClass: IsolationService },
        // { provide: AppStoreService, useClass: AppStoreService },
        // { provide: BackendService, useClass: BackendService },
        // { provide: BackendNeoItemService, useClass: BackendNeoItemService },
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
