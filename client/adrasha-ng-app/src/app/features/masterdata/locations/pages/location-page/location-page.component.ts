import { Component, inject, signal } from '@angular/core';
import { PageWrapperComponent, PageHeaderComponent } from '@shared/components';
import { LocationListComponent } from '../../components';
import { LocationService } from '@core/api';
import { LocationResponseDTO } from '@core/model/masterdataService';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { tap } from 'rxjs';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-location-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    LocationListComponent,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    TranslatePipe,
    RouterModule,
  ],
  templateUrl: './location-page.component.html',
})
export class LocationPageComponent {
  private readonly locationService = inject(LocationService);

  userId: string = '';
  locationList = signal<LocationResponseDTO[]>([]);

  // page metadata
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
    this.locationService
      .getAllLocations({
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
      .subscribe((response) => {
        this.locationList.set(response.content || []);
      });
  }
}
