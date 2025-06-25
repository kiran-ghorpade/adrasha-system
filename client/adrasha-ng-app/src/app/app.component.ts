import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginComponent } from '@features/auth/login/login.component';
import { PrimeNG } from 'primeng/config';

@Component({
  selector: 'app-root',
  imports: [RouterModule, CommonModule, LoginComponent],
  standalone:true,
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit{

  constructor(private primeng:PrimeNG){}

  ngOnInit(): void {
     this.primeng.ripple.set(true);
  }
}
