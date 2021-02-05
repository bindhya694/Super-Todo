package com.superloop.api.service;

import com.superloop.api.exception.ResourceNotFoundException;
import com.superloop.api.model.TodoItem;

import java.util.List;

/**
 * TodoTaskService - is the interface which calls the TodoTaskServiceImpl
 * @author Bindhya Maya
 * Version 1.0
 * Created Date 29/01/2021
 */
public interface TodoTaskService {

	/**
	 * createToDoItem - creates a new TodoItem
	 * @param todoItem
	 * @return
	 */
	public TodoItem createTodoItem(TodoItem todoItem);

	/**
	 * getAllTodos - lists all TodoItems based on status
	 * @param status
	 * @return
	 */
	public List<TodoItem> getAllTodos(String status);

	/**
	 * View the details of a single todoitem based on the identifier
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public TodoItem getTodoDetailsWithId(Long id) throws ResourceNotFoundException;

	/**
	 * Delete the details of a single todoitem
	 * @param todoItem
	 */
	public void deleteTodoDetails(TodoItem todoItem);

	/**
	 * Update the statuses of multiple todoitems
	 * @param todoItem
	 */
	public void updateTodos(List<TodoItem> todoItem);

	/**
	 * Make todo item Done
	 * @param todoItem
	 */
	public void makeTodoitemDone(TodoItem todoItem)  throws ResourceNotFoundException;

	/**
	 * Update the status of a single todoitem
	 * @param updatedTodoItem
	 * @param existingTodoItem
	 * @throws ResourceNotFoundException
	 */
	public void updateTodo(TodoItem existingTodoItem, TodoItem updatedTodoItem);

}
