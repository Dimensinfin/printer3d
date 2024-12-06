import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend-pocws';
  constructor(private router: Router) {}

  goToLandingPage() {
    console.log("Going to landing page...")
    this.router.navigate(['/landing']); // Navigate to the landing page
  }
}
