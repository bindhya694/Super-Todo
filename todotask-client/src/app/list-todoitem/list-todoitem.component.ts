import { Observable } from 'rxjs';
import { TodotaskService } from '../todotask.service';
import { Todoitem } from '../todoitem';
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-list-todoitem',
  templateUrl: './list-todoitem.component.html',
  styleUrls: ['./list-todoitem.component.css']
})
export class ListTodoitemComponent implements OnInit {
  todoitems: Todoitem[] = [];
  status: any;
  isButtonVisible = false;
  checkedIDs: any;
  isAllChecked: boolean;
  checkedCategoryList: any = [];
  constructor(private todotaskService: TodotaskService,
              private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.status = this.route.snapshot.params['status'];
    this.reloadData(this.status);
  }
  reloadData(status) {
    this.todoitems = [];
    this.status = status;
    console.log(status);
    this.todotaskService.getTodoItemList(status).subscribe(products => {
      this.todoitems = products;
      if (this.todoitems.length > 0) {
        console.log('sdfsfsdfsdfdsf', this.todoitems);
        this.isButtonVisible = true;
      } else {
        this.isButtonVisible = false;
      }
    });
  }

  deleteTodoItem(id: number) {
    this.todotaskService.deleteTodoItem(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData(this.status);
        },
        error => console.log(error));
  }

  todoItemDetails(id: number) {
    this.router.navigate(['details', id]);
  }

  editTodoItem(id: number) {
    this.router.navigate(['edit', id]);
  }
  checkAllCheckBox(ev) {
    this.checkedCategoryList = [];
    this.todoitems.forEach(x => {
      x.checked = ev.target.checked;
      // console.log('gggggggggggg', x);

    });

  }

  isAllCheckBoxChecked() {
    this.checkedCategoryList = [];

    this.todoitems.forEach(p => {
      if (p.checked) {
        // console.log('pppppppppp', p);
        this.checkedCategoryList.push(p);
        // console.log(' this.checkedCategoryList', this.checkedCategoryList);

      }
    });
  }

  onSubmit() {
    console.log('bindhyaaa' , this.checkedCategoryList);
    this.todotaskService.updateTodoStatus(this.checkedCategoryList)
      .subscribe(
        data => {
          console.log(data);
          // this.reloadData(this.status);
        },
        error => console.log(error));
  }
}
