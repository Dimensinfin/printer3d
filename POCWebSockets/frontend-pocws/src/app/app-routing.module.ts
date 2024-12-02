import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingpageComponent } from './landingpage/landingpage.component';

// ... existing code ...
const routes: Routes = [
  // { path: '', redirectTo: '/landing', pathMatch: 'full' }, // Redirect to landing page
  { path: 'landing', component: LandingpageComponent } // Add landing page route
];

// ... existing code ...
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
