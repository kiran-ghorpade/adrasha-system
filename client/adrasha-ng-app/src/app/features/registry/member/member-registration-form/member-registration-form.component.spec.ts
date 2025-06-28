import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberRegistrationFormComponent } from './member-registration-form.component';

describe('MemberRegistrationFormComponent', () => {
  let component: MemberRegistrationFormComponent;
  let fixture: ComponentFixture<MemberRegistrationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MemberRegistrationFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MemberRegistrationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
