import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MovementsCreateComponent } from './movements-create.component';

describe('MovementsCreateComponent', () => {
  let component: MovementsCreateComponent;
  let fixture: ComponentFixture<MovementsCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MovementsCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovementsCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
