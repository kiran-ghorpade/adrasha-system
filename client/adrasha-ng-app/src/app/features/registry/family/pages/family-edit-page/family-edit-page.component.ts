import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { ActivatedRoute } from '@angular/router';
import { FamilyDataService } from '@core/api/family-data/family-data.service';
import { FamilyResponseDTO } from '@core/model/dataService';
import { AuthService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { map } from 'rxjs';
import { FamilyFormComponent } from '../../components/family-form/family-edit-form.component';

@Component({
  selector: 'app-family-edit-page',
  imports: [FamilyFormComponent, PageHeaderComponent, TranslatePipe],
  template: ` <div class="flex flex-col gap-5">
    <app-page-header
      [title]="'registry.family.updateFamilyTitle' | translate"
    />
    <app-family-edit-form
      [id]="userId() ?? ''"
      [familyId]="id() ?? ''"
      [entity]="data() ?? {}"
    />
  </div>`,
})
export class FamilyEditPageComponent {
  // depedencies
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly familyDataService = inject(FamilyDataService);

  // states
  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id ?? null)),
    { initialValue: null }
  );

  id = signal<string | null>(null);
  data = signal<FamilyResponseDTO | null>(null);

  // initilize states
  ngOnInit() {
    this.id.set(this.activatedRoute.snapshot.paramMap.get('id') ?? null);

    if (this.id()) {
      this.loadMemberData(this.id()!);
    }
  }

  // helpers
  private loadMemberData(id: string): void {
    this.familyDataService.getFamily(id).subscribe((data) => {
      this.data.set(data);
    });
  }
}
