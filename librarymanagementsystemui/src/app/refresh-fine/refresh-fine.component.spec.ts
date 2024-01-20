import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefreshFineComponent } from './refresh-fine.component';

describe('RefreshFineComponent', () => {
  let component: RefreshFineComponent;
  let fixture: ComponentFixture<RefreshFineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefreshFineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefreshFineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
