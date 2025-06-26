import { Component, ViewEncapsulation } from '@angular/core';
import { RouterModule } from '@angular/router';
import {MatToolbarModule} from '@angular/material/toolbar'

@Component({
  selector: 'auth-layout',
  templateUrl: './auth-layout.component.html',
  encapsulation: ViewEncapsulation.None,
  imports: [RouterModule, MatToolbarModule],
})
export class AuthLayoutComponent {}
