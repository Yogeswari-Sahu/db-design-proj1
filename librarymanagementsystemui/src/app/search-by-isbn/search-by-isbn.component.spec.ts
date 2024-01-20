import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchByIsbnComponent } from './search-by-isbn.component';

describe('SearchByIsbnComponent', () => {
  let component: SearchByIsbnComponent;
  let fixture: ComponentFixture<SearchByIsbnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchByIsbnComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchByIsbnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
