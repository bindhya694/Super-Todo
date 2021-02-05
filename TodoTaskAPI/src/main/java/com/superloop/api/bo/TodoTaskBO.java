package com.superloop.api.bo;

import com.superloop.api.exception.ResourceNotFoundException;
import com.superloop.api.model.TodoItem;

import java.util.List;

/**
 * TodoTaskBO - is the interface which have the tasks and todoitem business logics defined in
 * its implementation
 * 
 * @author Bindhya Maya
 * Version 1.0
 * Created Date 29/01/2021
 */

public interface TodoTaskBO {

	/**
	 * createTodoItem - creates a new TodoItem
	 * @param todoItem
	 * @return TodoItem
	 */
	public TodoItem createTodoItem(TodoItem todoItem);

	/**
	 * getAllTodos - lists all TodoItem
	 * @param status
	 * @return List<TodoItem>
	 */
	public List<TodoItem> getAllTodos(String status);

	/**
	 * Delete the details of a single todo
	 * @param todoItem
	 * @return
	 */
	public void deleteTodoDetails(TodoItem todoItem);
	/**
	 * Update the details of multiple todo
	 * @param todoItem
	 * @return
	 */
	public void updateTodos(List<TodoItem> todoItem) ;
	/**
	 * Update the details of a single todo
	 * @param existingTodoItem
	 * @param updatedTodoItem
	 * @return
	 */
	public void updateTodo(TodoItem existingTodoItem, TodoItem updatedTodoItem);
	/**
	 * Make todo item Done
	 * @param todoItem
	 */
	public void makeTodoitemDone(TodoItem todoItem)  throws ResourceNotFoundException;

	/**
	 * View the details of a single todo
	 * @param id
	 * @return TodoItem
	 */
	TodoItem getTodoDetailsWithId(Long id) throws ResourceNotFoundException;
}
