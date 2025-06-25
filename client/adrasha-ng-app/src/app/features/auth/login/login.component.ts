import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { IconFieldModule } from 'primeng/iconfield';
import { IftaLabelModule } from 'primeng/iftalabel';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { AuthLayoutComponent } from '../../../shared/layout/auth-layout/auth-layout.component';
import { PasswordModule } from 'primeng/password';
import { TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';

interface LoginData {
  username: string;
  password: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [TableModule, CommonModule],
})
export class LoginComponent {
  products!: [
    {
      id: '1000';
      code: 'f230fh0g3';
      name: 'Bamboo Watch';
      description: 'Product Description';
      image: 'bamboo-watch.jpg';
      price: 65;
      category: 'Accessories';
      quantity: 24;
      inventoryStatus: 'INSTOCK';
      rating: 5;
    },
    {
      id: '1000';
      code: 'f230fh0g3';
      name: 'Bamboo Watch';
      description: 'Product Description';
      image: 'bamboo-watch.jpg';
      price: 65;
      category: 'Accessories';
      quantity: 24;
      inventoryStatus: 'INSTOCK';
      rating: 5;
    },
    {
      id: '1000';
      code: 'f230fh0g3';
      name: 'Bamboo Watch';
      description: 'Product Description';
      image: 'bamboo-watch.jpg';
      price: 65;
      category: 'Accessories';
      quantity: 24;
      inventoryStatus: 'INSTOCK';
      rating: 5;
    }
  ];
}
