import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatecertComponent } from './createcert.component';

describe('CreatecertComponent', () => {
  let component: CreatecertComponent;
  let fixture: ComponentFixture<CreatecertComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatecertComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatecertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
