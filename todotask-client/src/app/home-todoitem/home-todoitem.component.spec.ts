import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeTodoitemComponent } from './home-todoitem.component';

describe('HomeTodoitemComponent', () => {
  let component: HomeTodoitemComponent;
  let fixture: ComponentFixture<HomeTodoitemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeTodoitemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeTodoitemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
