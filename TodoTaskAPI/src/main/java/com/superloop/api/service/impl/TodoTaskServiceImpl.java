package com.superloop.api.service.impl;

import com.superloop.api.exception.ResourceNotFoundException;
import com.superloop.api.model.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superloop.api.bo.TodoTaskBO;
import com.superloop.api.service.TodoTaskService;

import java.util.List;

/**
 * TodoTaskServiceImpl - This class acts as a service class and all
 * the method calls coming from Controller will redirect to Business layer to fetch or set the data.
 * 
 * @author Bindhya Maya
 * Version 1.0
 * Created Date 29/01/2021
 */

@Service("todoTaskService")
public class TodoTaskServiceImpl implements TodoTaskService {


	@Autowired
	TodoTaskBO todoTaskBO;

	/**
	 * createTodoItem - creates a TodoItem with pending status
	 * @param todoItem
	 * @return
	 */
	@Override
	public TodoItem createTodoItem(TodoItem todoItem) {
		return todoTaskBO.createTodoItem(todoItem);
	}

	/**
	 * getAllTodos - lists all TodoItem based on the status
	 * @param status
	 * @return List<TodoItem>
	 */
	@Override
	public List<TodoItem> getAllTodos(String status) {
		return todoTaskBO.getAllTodos(status);
	}

	/**
	 * View the details of a single todoitem based on identifier
	 * @param id
	 * @return TodoItem
	 */
	public TodoItem getTodoDetailsWithId(Long id) throws ResourceNotFoundException {
		return todoTaskBO.getTodoDetailsWithId(id) ;
	}

	/**
	 * Delete the details of a single todoitem
	 * @param toDoItem
	 * @return
	 */
	public void deleteTodoDetails(TodoItem toDoItem){
		 todoTaskBO.deleteTodoDetails(toDoItem);
	}

	/**
	 * Update the statuses of multiple todoitem
	 * @param toDoItem
	 * @return
	 */
	public void updateTodos(List<TodoItem> toDoItem){
		todoTaskBO.updateTodos(toDoItem);
	}

	/**
	 * make Todoitem Done
	 * @param toDoItem
	 * @return
	 */
	public void makeTodoitemDone(TodoItem toDoItem)  throws ResourceNotFoundException{
		todoTaskBO.makeTodoitemDone(toDoItem);
	}

	/**
	 * Update the details of a single todoitem
	 * @param existingTodoItem
	 * @param updatedTodoItem
	 * @return
	 */
	public void updateTodo(TodoItem existingTodoItem, TodoItem updatedTodoItem){
		todoTaskBO.updateTodo(existingTodoItem,updatedTodoItem);
	}
}
