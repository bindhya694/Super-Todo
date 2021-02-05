import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListTodoitemComponent } from './list-todoitem.component';

describe('ListTodoitemComponent', () => {
  let component: ListTodoitemComponent;
  let fixture: ComponentFixture<ListTodoitemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListTodoitemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListTodoitemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
