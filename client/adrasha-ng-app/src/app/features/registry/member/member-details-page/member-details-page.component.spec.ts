import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberDetailsPageComponent } from './member-details-page.component';

describe('MemberDetailsPageComponent', () => {
  let component: MemberDetailsPageComponent;
  let fixture: ComponentFixture<MemberDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MemberDetailsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MemberDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
