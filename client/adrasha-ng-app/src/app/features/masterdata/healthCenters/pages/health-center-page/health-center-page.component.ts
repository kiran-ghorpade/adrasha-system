import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RouterModule } from '@angular/router';
import { HealthCenterService } from '@core/api';
import { HealthCenterResponseDTO } from '@core/model/masterdataService';
import { LocationListComponent } from '@features/masterdata/locations/components';
import { PageWrapperComponent, PageHeaderComponent } from '@shared/components';
import { HealthCenterListComponent } from '../../components';
import { tap } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-health-center-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    RouterModule,
    HealthCenterListComponent,
    TranslateModule
  ],
  templateUrl: './health-center-page.component.html',
})
export class HealthCenterPageComponent {
  private readonly healthCenterService = inject(HealthCenterService);

  userId: string = '';
  healthCenterList = signal<HealthCenterResponseDTO[]>([]);

  // page metadata
  // paginated metadata
  pageIndex = signal(0);
  pageSize = signal(10);
  totalSize = signal(0);

  ngOnInit(): void {
    this.loadPaginatedData();
  }

  onPageChange(event: any) {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
    this.loadPaginatedData();
  }

  loadPaginatedData() {
    this.healthCenterService
      .getAllHealthCenters({
        filterDTO: {},
        pageable: {
          page: this.pageIndex(),
          size: this.pageSize(),
          sort: [],
        },
      })
      .pipe(
        tap((response) => {
          this.pageIndex.set(response.page?.number ?? 0);
          this.pageSize.set(response.page?.size ?? 0);
          this.totalSize.set(response.page?.totalElements ?? 0);
        })
      )
      .subscribe((centers) => {
        this.healthCenterList.set(centers.content || []);
      });
  }
}
