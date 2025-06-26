import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NotFoundComponent } from "../../widgets/not-found/not-found.component";
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-page-not-found',
  imports: [CommonModule, NotFoundComponent, MatButtonModule, RouterModule],
  templateUrl: './page-not-found.component.html',
})
export class PageNotFoundComponent {

}
