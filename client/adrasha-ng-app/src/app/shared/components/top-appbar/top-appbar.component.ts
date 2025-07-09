import { Component, inject, OnInit, signal } from '@angular/core';
// import { MatAvatarModule } from '@angular/material/avatar';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router } from '@angular/router';
import { AuthService } from '@core/services';
import { AppLogoComponent } from '../../widgets/logo.component';
// import { AuthenticationService } from 'src/app/services/authentication.service'; // import your auth service

@Component({
  selector: 'app-top-appbar',
  templateUrl: './top-appbar.component.html',
  imports: [
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatDividerModule,
    MatTooltipModule,
    MatListModule,
    AppLogoComponent,
    CommonModule,
  ],
})
export class TopAppBarComponent implements OnInit {
  private readonly authService = inject(AuthService);
  readonly router = inject(Router);

  loggedIn = signal(false);

  settings = [
    { label: 'Profile', toLink: '/profile', icon: 'person' },
    { label: 'Settings', toLink: '/settings', icon: 'settings' },
  ];

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe((status) => {
      this.loggedIn.set(status);
      console.log(this.loggedIn());
      
    });
  }

  isDashboard() {
    return this.router.url === '/dashboard';
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}
