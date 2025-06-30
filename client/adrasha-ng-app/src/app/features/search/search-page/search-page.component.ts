import { Component } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-search-page',
  imports: [MatIconModule, MatFormFieldModule, MatInputModule, RouterModule, MatListModule],
  templateUrl: './search-page.component.html',
})
export class SearchPageComponent {
  users = [
    { id: 1, name: 'Ramesh Jadhav', age: 35 },
    { id: 2, name: 'Sita Verma', age: 28 },
    { id: 3, name: 'John Doe', age: 40 },
    { id: 4, name: 'Ayesha Khan', age: 31 },
    { id: 5, name: 'Michael Smith', age: 45 },
    { id: 5, name: 'Michael Smith', age: 45 },
    { id: 5, name: 'Michael Smith', age: 45 },
    { id: 5, name: 'Michael Smith', age: 45 },
    { id: 5, name: 'Michael Smith', age: 45 },
  ];
}
