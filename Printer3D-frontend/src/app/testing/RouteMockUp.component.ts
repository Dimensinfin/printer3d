// - CORE
import { Component } from '@angular/core';
import { Routes } from '@angular/router';

/**
 * This is an empty component to be pointed with valid routes.
 *
 * @export
 * @class HomeComponent
 */
@Component({
    template: `Home`
})
export class RouteMockUpComponent {}
export const routes: Routes = [
    { path: '', redirectTo: '/', pathMatch: 'full' },
    { path: 'inventory', component: RouteMockUpComponent }
];
// - E N D   O F   R O U T I N G   C O M P O N E N T
