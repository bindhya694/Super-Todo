package com.superloop.api.bo.impl;

import java.util.List;
import java.util.Locale;
import java.util.function.*;

import com.superloop.api.dao.TodoItemRepository;
import com.superloop.api.exception.ResourceNotFoundException;
import com.superloop.api.model.TodoItem;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.superloop.api.bo.TodoTaskBO;

import static com.superloop.api.util.TodoTaskConstants.COMPLETE_STATUS;
import static com.superloop.api.util.TodoTaskConstants.PENDING_STATUS;

/**
 * This class is used as a business logic to to support the rest services. the
 * methods are making the calls to Repository interfaces
 *
 * @author Bindhya Maya Version 1.0 Created Date 29/01/2021
 */

@Component("todoTaskBO")
public class TodoTaskBOImpl implements TodoTaskBO {
    private static final Logger logger = LoggerFactory
            .getLogger(TodoTaskBOImpl.class);

    @Autowired
    TodoItemRepository toDoItemRepository;

    /**
     * createToDoItem - Calls the repository to create a new TodoItem
     *
     * @param todoItem
     * @return TodoItem
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TodoItem createTodoItem(TodoItem todoItem) {
        TodoItem todoItemResponse = null;

        //
        if (StringUtils.isBlank(todoItem.getStatus())) {
            todoItem.setStatus(PENDING_STATUS);
        } else if (!todoItem.getStatus().toUpperCase().equals(PENDING_STATUS)) {
            todoItem.setStatus(PENDING_STATUS);
        }

        todoItemResponse = toDoItemRepository.save(todoItem);
        logger.info("\nTODO iTEM created successfully");
        return todoItemResponse;
    }

    /**
     * getAllTodos - Calls the repository to get list of TodoItems based on status
     *
     * @param status
     * @return List<TodoItem>
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<TodoItem> getAllTodos(String status) {
        if(StringUtils.isNotEmpty(status)){
            status = status.toUpperCase();
        }
        List<TodoItem> s = toDoItemRepository.findByStatus(status);
        return s;
    }

    /**
     * Delete the details of a single todoitem
     *
     * @param todoItem
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTodoDetails(TodoItem todoItem) {
        toDoItemRepository.delete(todoItem);
    }

    /**
     * Update the statuses of multiple todoitems
     *
     * @param todoItems
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTodos(List<TodoItem> todoItems) {
        todoItems.forEach(new Consumer<TodoItem>() {
            TodoItem existingTodoItem = null;

            @Override
            public void accept(TodoItem todoItem) {
                try {
                    existingTodoItem = toDoItemRepository.findById(todoItem.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("TodoItem not found for this name : " + todoItem.getName()));
                } catch (ResourceNotFoundException e) {
                    logger.error("resource not found..." + todoItem.getName());
                }
                if(existingTodoItem != null) {
                    existingTodoItem.setStatus(COMPLETE_STATUS);
                    toDoItemRepository.save(existingTodoItem);
                }
            }
        });
    }

    /**
     * Update the details of a single todoitem
     *
     * @param existingTodoItem
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTodo(TodoItem existingTodoItem, TodoItem updatedTodoItem) {
        existingTodoItem.setStatus(existingTodoItem.getStatus());
        existingTodoItem.setName(updatedTodoItem.getName());
        existingTodoItem.setDescription(updatedTodoItem.getDescription());
        existingTodoItem.setDueDate(updatedTodoItem.getDueDate());
        toDoItemRepository.save(existingTodoItem);
    }

    /**
     * View the details of a single todo based on identifier
     *
     * @param id
     * @return TodoItem
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public TodoItem getTodoDetailsWithId(Long id) throws ResourceNotFoundException {
        return toDoItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TodoItem not found for this name : " + id));
    }

    /**
     * make Todoitem Done
     * @param toDoItem
     * @return
     */
    public void makeTodoitemDone(TodoItem toDoItem) throws ResourceNotFoundException{
        TodoItem existingTodoItem = null;
        existingTodoItem = toDoItemRepository.findById(toDoItem.getId())
                .orElseThrow(() -> new ResourceNotFoundException("TodoItem not found for this name : " + toDoItem.getName()));
        existingTodoItem.setStatus(existingTodoItem.getStatus());
        existingTodoItem.setName(toDoItem.getName());
        existingTodoItem.setDescription(toDoItem.getDescription());
        existingTodoItem.setDueDate(toDoItem.getDueDate());
        existingTodoItem.setStatus(COMPLETE_STATUS);
        toDoItemRepository.save(existingTodoItem);
    }

}
