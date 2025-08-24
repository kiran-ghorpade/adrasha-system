import { Component, computed, inject, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '@core/services';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent } from '@shared/components';
import { MemberDataService } from '@core/api/member-data/member-data.service';
import { MemberResponseDTO } from '@core/model/dataService';
import { toSignal } from '@angular/core/rxjs-interop';
import { map } from 'rxjs';
import { MemberFormComponent } from '../../components';

@Component({
  selector: 'app-member-edit-page',
  imports: [PageHeaderComponent, MemberFormComponent, TranslatePipe],
  template: `
    <div class="flex flex-col gap-5">
      @if(isUpdate()){
      <app-page-header
        title="{{ 'app.features.registry.member.forms.updateTitle' | translate }}"
      />
      } @else {
      <app-page-header
        title="{{ 'app.features.registry.member.forms.addTitle' | translate }}"
      />
      }
      <app-member-form
        [userId]="userId() ?? ''"
        [id]="memberId() ?? ''"
        [familyId]="familyId() ?? ''"
        [isUpdate]="isUpdate()"
        [entity]="memberData() ?? {}"
      />
    </div>
  `,
})
export class MemberFormPageComponent {
  // depedencies
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly memberService = inject(MemberDataService);

  // states
  userId = toSignal(
    this.authService.currentUser.pipe(map((user) => user?.id ?? null)),
    { initialValue: null }
  );

  memberId = signal<string | null>(null);
  familyId = signal<string | null>(null);
  memberData = signal<MemberResponseDTO | null>(null);
  isUpdate = computed(() => this.memberId() !== null);

  // initilize states
  ngOnInit() {
    this.memberId.set(this.activatedRoute.snapshot.paramMap.get('id') ?? null);
    this.familyId.set(
      this.activatedRoute.snapshot.queryParamMap.get('familyId') ?? null
    );

    if (this.memberId()) {
      this.loadMemberData(this.memberId()!);
    }
  }

  // helpers
  private loadMemberData(id: string): void {
    this.memberService.getMember(id).subscribe((member) => {
      this.memberData.set(member);
    });
  }
}
