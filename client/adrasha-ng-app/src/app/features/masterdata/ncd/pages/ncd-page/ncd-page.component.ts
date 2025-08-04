import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RouterModule } from '@angular/router';
import { NcdService } from '@core/api';
import { NCDResponseDTO } from '@core/model/masterdataService';
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';
import { NcdListComponent } from '../../components';
import { tap } from 'rxjs';

@Component({
  selector: 'app-ncd-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    RouterModule,
    NcdListComponent,
  ],
  templateUrl: './ncd-page.component.html',
})
export class NcdPageComponent {
  private readonly locationService = inject(NcdService);

  userId: string = '';
  ncdList = signal<NCDResponseDTO[]>([]);

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
      .getAllNCD({
        filterDTO: {},
        pageable: {},
      })
      .pipe(
        tap((response) => {
          this.pageIndex.set(response.page?.number ?? 0);
          this.pageSize.set(response.page?.size ?? 0);
          this.totalSize.set(response.page?.totalElements ?? 0);
        })
      )
      .subscribe((response) => {
        this.ncdList.set(response.content || []);
      });
  }
}
