import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RouterModule } from '@angular/router';
import { HealthCenterService } from '@core/api';
import { HealthCenterResponseDTO } from '@core/model/masterdataService';
import { LocationListComponent } from '@features/masterdata/locations/components';
import { PageWrapperComponent, PageHeaderComponent } from "@shared/components";
import { HealthCenterListComponent } from "../../components";

@Component({
  selector: 'app-health-center-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    RouterModule,
    HealthCenterListComponent
],
  templateUrl: './health-center-page.component.html',
})
export class HealthCenterPageComponent {
  private readonly healthCenterService = inject(HealthCenterService);

  userId: string = '';
  healthCenterList = signal<HealthCenterResponseDTO[]>([]);

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
    this.healthCenterService
      .getAllHealthCenters({
        filterDTO: {},
        pageable: {
          page: 1,
          size: 10,
          sort: [],
        },
      })
      .subscribe((centers) => {
        this.healthCenterList.set(centers.content || []);
        this.totalSize.set(centers.numberOfElements || 0);
      });
  }
}
