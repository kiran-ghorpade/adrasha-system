import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AshaDashboardComponent } from './asha-dashboard.component';

describe('AshaDashboardComponent', () => {
  let component: AshaDashboardComponent;
  let fixture: ComponentFixture<AshaDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AshaDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AshaDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
