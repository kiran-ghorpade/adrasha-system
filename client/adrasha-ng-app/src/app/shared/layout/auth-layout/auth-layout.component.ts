import { Component, ViewEncapsulation } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Footer } from 'primeng/api';
import { Toolbar } from 'primeng/toolbar';

@Component({
  selector: 'auth-layout',
  templateUrl: './auth-layout.component.html',
  styleUrl: './auth-layout.component.scss',
  encapsulation: ViewEncapsulation.None,
  imports: [RouterModule],
})
export class AuthLayoutComponent {}
