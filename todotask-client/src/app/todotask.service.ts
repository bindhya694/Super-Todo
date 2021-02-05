import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TodotaskService {

  private newTodoUrl = 'http://localhost:8080/api/v1/todo';
  private todoListUrl = 'http://localhost:8080/api/v1/todos';
  private todoDeleteUrl = 'http://localhost:8080/api/v1/todo';
  private todoDetailsUrl = 'http://localhost:8080/api/v1/todo';
  private todoUpdateUrl = 'http://localhost:8080/api/v1/todo';
  private todoUpdateStatusUrl = 'http://localhost:8080/api/v1/todos';
  private makeTodoStatusDoneUrl = 'http://localhost:8080/api/v1/todoStatus';

  constructor(private http: HttpClient) { }

  createTodoItem(todoitem: Object): Observable<Object> {
    return this.http.post(`${this.newTodoUrl}`, todoitem);
  }
  getTodoItemList(id: string): Observable<any> {
    return this.http.get(`${this.todoListUrl}/${id}`);
  }
  deleteTodoItem(id: number): Observable<any> {
    return this.http.delete(`${this.todoDeleteUrl}/${id}`, { responseType: 'text' });
  }
  getTodoDetails(id: number): Observable<any> {
    return this.http.get(`${this.todoDetailsUrl}/${id}`);
  }
  editTodoDetails(id: number, value: any): Observable<any> {
    return this.http.put(`${this.todoUpdateUrl}/${id}`, value);
  }
  updateTodoStatus(value: any[]): Observable<any> {
    return this.http.put(`${this.todoUpdateStatusUrl}`, value);
  }
  makeTodoStatusDone(value: any): Observable<any> {
    return this.http.put(`${this.makeTodoStatusDoneUrl}`, value);
  }
}
