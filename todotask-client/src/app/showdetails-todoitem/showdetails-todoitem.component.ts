import { TodotaskService } from '../todotask.service';
import { Todoitem } from '../todoitem';
import { Component, OnInit, Input } from '@angular/core';
import { ListTodoitemComponent } from '../list-todoitem/list-todoitem.component';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-todoitem-details',
  templateUrl: './showdetails-todoitem.component.html',
  styleUrls: ['./showdetails-todoitem.component.css']
})
export class ShowdetailsTodoitemComponent implements OnInit {

  id: number;
  todoitem: Todoitem;

  constructor(private route: ActivatedRoute, private router: Router,
              private todotaskService: TodotaskService) { }

  ngOnInit() {
    this.todoitem = new Todoitem();

    this.id = this.route.snapshot.params['id'];
    this.todoItemDetails(this.id);
  }

  list(status: string) {
    this.router.navigate(['todoitems', status]);
  }
  updateStatus(todoitem) {
    this.todotaskService.makeTodoStatusDone(todoitem)
      .subscribe(data => {
        console.log(data);
        this.todoitem = data;
        this.todoItemDetails(data.id);
      }, error => console.log(error));
  }

  todoItemDetails(id: number) {
    this.todotaskService.getTodoDetails(this.id)
      .subscribe(data => {
        console.log(data);
        this.todoitem = data;
      }, error => console.log(error));
  }
}
