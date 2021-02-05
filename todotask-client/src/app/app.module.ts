import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateTodoitemComponent } from './create-todoitem/create-todoitem.component';
import { ListTodoitemComponent } from './list-todoitem/list-todoitem.component';
import { ShowdetailsTodoitemComponent } from './showdetails-todoitem/showdetails-todoitem.component';
import { HttpClientModule } from '@angular/common/http';
import { EditTodoitemComponent } from './edit-todoitem/edit-todoitem.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeTodoitemComponent } from './home-todoitem/home-todoitem.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
@NgModule({
  declarations: [
    AppComponent,
    CreateTodoitemComponent,
    ListTodoitemComponent,
    ShowdetailsTodoitemComponent,
    EditTodoitemComponent,
    HomeTodoitemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
