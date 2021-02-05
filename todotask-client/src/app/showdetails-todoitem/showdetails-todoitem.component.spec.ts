import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowdetailsTodoitemComponent } from './showdetails-todoitem.component';

describe('ShowdetailsTodoitemComponent', () => {
  let component: ShowdetailsTodoitemComponent;
  let fixture: ComponentFixture<ShowdetailsTodoitemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowdetailsTodoitemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowdetailsTodoitemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
