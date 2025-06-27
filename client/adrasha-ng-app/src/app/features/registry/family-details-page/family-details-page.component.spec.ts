import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FamilyDetailsPageComponent } from './family-details-page.component';

describe('FamilyDetailsPageComponent', () => {
  let component: FamilyDetailsPageComponent;
  let fixture: ComponentFixture<FamilyDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FamilyDetailsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FamilyDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
