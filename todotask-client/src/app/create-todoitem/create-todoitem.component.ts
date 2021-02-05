import { TodotaskService } from '../todotask.service';
import { Todoitem } from '../todoitem';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-todoitem',
  templateUrl: './create-todoitem.component.html',
  styleUrls: ['./create-todoitem.component.css']
})
export class CreateTodoitemComponent implements OnInit {

  todoitem: Todoitem = new Todoitem();
  submitted = false;
  finalDate: any;

  constructor(private todotaskService: TodotaskService,
              private router: Router) { }

  ngOnInit() {
  }

  newTodoitem(): void {
    this.submitted = false;
    this.todoitem = new Todoitem();
  }

  save() {
    console.log('ppppppppppppp', this.todoitem);
    // tslint:disable-next-line:no-unused-expression
    console.log('oooooooooooo', this.finalDate);
    this.todoitem.dueDate = this.finalDate;
    console.log('bbbbbbbbbbbb', this.todoitem);
    this.todotaskService
      .createTodoItem(this.todoitem).subscribe(data => {
        console.log(data);
        this.todoitem = new Todoitem();
        this.gotoList(this.todoitem.status);
      },
      error => console.log(error));
  }

  onDateSelect(event) {
    const year = event.year;
    const month = event.month <= 9 ? '0' + event.month : event.month;
    const day = event.day <= 9 ? '0' + event.day : event.day;
    this.finalDate = year + '-' + month + '-' + day;
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  gotoList(status) {
    this.router.navigate(['todoitems', status]);
  }
}
