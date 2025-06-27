import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { PaperComponent } from '@core/components/paper/paper.component';
import { MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-registry-page',
  imports: [
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    PaperComponent,
    MatCardModule,
    MatListModule,
    RouterModule,
    MatPaginatorModule,
  ],
  templateUrl: './registry-page.component.html',
})
export class RegistryPageComponent {
  users = [
    { id: 1, name: 'Ramesh Jadhav', age: 35 },
    { id: 2, name: 'Sita Verma', age: 28 },
    { id: 3, name: 'John Doe', age: 40 },
    { id: 4, name: 'Ayesha Khan', age: 31 },
    { id: 5, name: 'Michael Smith', age: 45 },
  ];
}
