import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TodotaskService } from '../todotask.service';
import { Todoitem } from '../todoitem';
@Component({
  selector: 'app-edit-todoitem',
  templateUrl: './edit-todoitem.component.html',
  styleUrls: ['./edit-todoitem.component.css']
})
export class EditTodoitemComponent implements OnInit {

  id: number;
  todoitem: Todoitem;

  constructor(private route: ActivatedRoute, private router: Router,
              private todotaskService: TodotaskService) { }

  ngOnInit() {
    this.todoitem = new Todoitem();

    this.id = this.route.snapshot.params['id'];

    this.todotaskService.getTodoDetails(this.id)
      .subscribe(data => {
        console.log(data);
        this.todoitem = data;
      }, error => console.log(error));
  }

  editTodoItem(todoitem: any) {
    this.todotaskService.editTodoDetails(this.id, this.todoitem)
      .subscribe(data => {
        console.log(data);
        this.todoitem = todoitem;
        console.log(this.todoitem.status);
        this.gotoList(this.todoitem.status);
      }, error => console.log(error));
  }

  onSubmit() {
    this.editTodoItem(this.todoitem);
  }

  gotoList(status: string) {
    this.router.navigate(['todoitems', status]);
  }
}
