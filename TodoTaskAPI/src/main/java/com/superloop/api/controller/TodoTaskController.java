package com.superloop.api.controller;

import com.superloop.api.exception.ResourceNotFoundException;
import com.superloop.api.model.TodoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import com.superloop.api.service.TodoTaskService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TodoTaskController - This class acts as a controller and all the incoming rest
 * endpoints would hit this initially. This class may be redirected to
 * respective services to fetch or set the data.
 *
 * @author Bindhya Maya
 * Version 1.0
 * Created Date 29/01/2021
 */

@RestController
@RequestMapping("/api/v1")
@RequestScope
@Validated
public class TodoTaskController {
    private static final Logger logger = LoggerFactory.getLogger(TodoTaskController.class);

    @Autowired
    TodoTaskService todoTaskService;

    /**
     * createTodo - This method contains a rest resource endpoint which is fired
     * from the client when a new Todoitem creation request comes. It creates a new
     * Todoitem based on the parameters provided in the request with a status as PENDING
     *
     * @param todoItem request object containing new Todoitem details
     * @return TodoItem
     */
    @PostMapping("/todo")
    public TodoItem createTodo(@Valid @RequestBody TodoItem todoItem) {
        return todoTaskService.createTodoItem(todoItem);
    }

    /**
     * getAllTodos - This method contains a rest resource endpoint which is fired
     * from the client when a request to view all the PENDING or COMPLETE list of todo items occurs.
     *
     * @param status
     * @return List<TodoItem>
     */
    @GetMapping("/todos/{status}")
    public List<TodoItem> getAllTodos(@PathVariable(value = "status") String status) {
        List<TodoItem> testing = todoTaskService.getAllTodos(status);
        return testing;
    }

    /**
     * getTodoDetails - rest end point to view the details of a single todoitem
     *
     * @param id
     * @return TodoItem
     */
    @GetMapping("/todo/{id}")
    public TodoItem getTodoDetails(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return todoTaskService.getTodoDetailsWithId(id);
    }

    /**
     * deleteTodoDetails - rest end point to delete the details of a single todo
     *
     * @param id
     * @return Map<String, Boolean>
     */
    @DeleteMapping("/todo/{id}")
    public Map<String, Boolean> deleteTodo(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        //get
        TodoItem todoItem = todoTaskService.getTodoDetailsWithId(id);
        //delete
        todoTaskService.deleteTodoDetails(todoItem);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /**
     * updateTodos - rest end point to update the statuses of the list of TodoItems with PENDING to COMPLETE
     *
     * @param todoItems
     * @return Map<String, Boolean>
     */
    @PutMapping("/todos")
    public Map<String, Boolean> updateTodos(@Valid @RequestBody List<TodoItem> todoItems) {
        todoTaskService.updateTodos(todoItems);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return response;
    }

    /**
     * updateTodos - rest end point to update the statuses of the list of TodoItems with PENDING to COMPLETE
     *
     * @param todoItem
     * @return Map<String, Boolean>
     */
    @PutMapping("/todoStatus")
    public Map<String, Boolean> makeTodoItemDone(@Valid @RequestBody TodoItem todoItem) throws ResourceNotFoundException{
        todoTaskService.makeTodoitemDone(todoItem);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return response;
    }

    /**
     * updateTodo - rest end point to update the details of a single TodoItem from PENDING to COMPLETE
     *
     * @param updatedTodoItem
     * @return Map<String, Boolean>
     */
    @PutMapping("/todo/{id}")
    public Map<String, Boolean> updateTodo(@Valid @PathVariable(value = "id") Long id, @RequestBody TodoItem updatedTodoItem) throws ResourceNotFoundException {
        //get
        TodoItem exitingTodoItem = todoTaskService.getTodoDetailsWithId(id);
        todoTaskService.updateTodo(exitingTodoItem, updatedTodoItem);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return response;
    }
}
