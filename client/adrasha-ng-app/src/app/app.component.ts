import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NotFoundComponent } from "./shared/widgets/not-found/not-found.component";

@Component({
  selector: 'app-root',
  imports: [
    RouterModule,
    NotFoundComponent
],
  standalone: true,
  templateUrl: './app.component.html',
})
export class AppComponent {}
