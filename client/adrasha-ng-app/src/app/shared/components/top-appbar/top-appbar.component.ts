import { Component, OnInit } from '@angular/core';
// import { MatAvatarModule } from '@angular/material/avatar';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router } from '@angular/router';
import { AppLogoComponent } from "../../widgets/logo.component";
import { SearchbarComponent } from "../searchbar/searchbar.component";
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
    SearchbarComponent
],
})
export class TopAppBarComponent implements OnInit {
  anchorElUser: any = null;
  settings = [
    { label: 'Profile', toLink: '/profile', icon: 'person' },
    { label: 'Settings', toLink: '/settings', icon: 'settings' },
  ];

  constructor(
    public router: Router
  ) // private authService: AuthenticationService
  {}

  ngOnInit(): void {}

  handleOpenUserMenu(event: any) {
    this.anchorElUser = event.target;
  }

  handleCloseUserMenu() {
    this.anchorElUser = null;
  }

  logout() {
    // this.authService.logout();
    this.router.navigate(['/login']);
  }
}
