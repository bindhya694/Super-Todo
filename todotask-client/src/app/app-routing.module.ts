import { CreateTodoitemComponent } from './create-todoitem/create-todoitem.component';
import { ListTodoitemComponent } from './list-todoitem/list-todoitem.component';
import { ShowdetailsTodoitemComponent } from './showdetails-todoitem/showdetails-todoitem.component';
import { EditTodoitemComponent } from './edit-todoitem/edit-todoitem.component';
import { HomeTodoitemComponent } from './home-todoitem/home-todoitem.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: 'todoitems/:status', component: ListTodoitemComponent },
  { path: 'add', component: CreateTodoitemComponent },
  { path: 'home', component: HomeTodoitemComponent },
  { path: 'details/:id', component: ShowdetailsTodoitemComponent },
  { path: 'edit/:id', component: EditTodoitemComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
