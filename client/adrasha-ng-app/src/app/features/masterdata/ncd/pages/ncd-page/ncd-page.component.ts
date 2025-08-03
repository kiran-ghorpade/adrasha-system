import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RouterModule } from '@angular/router';
import { NcdService } from '@core/api';
import { NCDResponseDTO } from '@core/model/masterdataService';
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';
import { NcdListComponent } from '../../components';

@Component({
  selector: 'app-ncd-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    RouterModule,
    NcdListComponent
],
  templateUrl: './ncd-page.component.html',
})
export class NcdPageComponent {
  private readonly locationService = inject(NcdService);

  userId: string = '';
  ncdList = signal<NCDResponseDTO[]>([]);

  // page metadata
  totalSize = signal(0);
  page = signal(0);

  ngOnInit(): void {
    this.loadRoleRequests();
  }

  onPageChange(event: any) {
    this.loadRoleRequests();
  }

  loadRoleRequests() {
    this.locationService
      .getAllNCD({
        filterDTO: {},
      })
      .subscribe((rolerequests) => {
        this.ncdList.set(rolerequests.content || []);
        this.totalSize.set(rolerequests.numberOfElements || 0);
      });
  }
}
