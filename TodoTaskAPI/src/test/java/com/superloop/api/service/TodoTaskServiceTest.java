package com.superloop.api.service;

import com.superloop.api.bo.TodoTaskBO;
import com.superloop.api.exception.ResourceNotFoundException;
import com.superloop.api.model.TodoItem;
import com.superloop.api.service.impl.TodoTaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class TodoTaskServiceTest {

    private static final String TODO_ITEM_NAME = "TODOTest1";
    private static final String PENDING_STATUS = "PENDING";


    @InjectMocks
    TodoTaskService todoTaskService = new TodoTaskServiceImpl();

    @Mock
    TodoTaskBO todoTaskBO;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTodos() {
        List<TodoItem> list = new ArrayList<TodoItem>();
        TodoItem item1 = new TodoItem(1L, "TODOTest1", "TODOTest1", "PENDING", LocalDate.now());
        TodoItem item2 = new TodoItem(2L, "TODOTest2", "TODOTest2", "PENDING", LocalDate.now());
        TodoItem item3 = new TodoItem(3L, "TODOTest3", "TODOTest3", "COMPLETE", LocalDate.now());

        list.add(item1);
        list.add(item2);
        list.add(item3);

        when(todoTaskBO.getAllTodos("PENDING")).thenReturn(list);
        //test
        List<TodoItem> todoList = todoTaskService.getAllTodos(PENDING_STATUS);

        assertEquals(3, todoList.size());
        verify(todoTaskBO, times(1)).getAllTodos(PENDING_STATUS);
    }

    @Test
    public void testGetTodoDetails() throws ResourceNotFoundException {
        TodoItem item1 = new TodoItem(1L, TODO_ITEM_NAME, TODO_ITEM_NAME, PENDING_STATUS, LocalDate.now());

        when(todoTaskBO.getTodoDetailsWithId(1L)).thenReturn(item1);
        //test
        TodoItem todoItem = todoTaskService.getTodoDetailsWithId(1L);
        assertNotNull(todoItem);
        verify(todoTaskBO, times(1)).getTodoDetailsWithId(1L);
    }

    @Test
    public void testCreateTodo() throws ResourceNotFoundException {
        TodoItem item1 = new TodoItem(1L, TODO_ITEM_NAME, TODO_ITEM_NAME, PENDING_STATUS, LocalDate.now());

        when(todoTaskBO.createTodoItem(item1)).thenReturn(item1);
        //test
        TodoItem todoItem = todoTaskService.createTodoItem(item1);
        assertEquals(todoItem.getName(), TODO_ITEM_NAME);
        assertEquals(todoItem.getDescription(), TODO_ITEM_NAME);
        assertEquals(todoItem.getStatus(), PENDING_STATUS);
        verify(todoTaskBO, times(1)).createTodoItem(item1);
    }

    @Test
    public void testDeleteTodoItemDetails() throws ResourceNotFoundException {
        TodoItem item1 = new TodoItem(1L, TODO_ITEM_NAME, TODO_ITEM_NAME, PENDING_STATUS, LocalDate.now());

        doNothing().when(todoTaskBO).deleteTodoDetails(item1);
        //test
        todoTaskService.deleteTodoDetails(item1);
        verify(todoTaskBO, times(1)).deleteTodoDetails(item1);
    }

    @Test
    public void testUpdateMultipleTodoItemDetails() throws ResourceNotFoundException {
        List<TodoItem> list = new ArrayList<TodoItem>();
        TodoItem item1 = new TodoItem(1L, "TODOTest1", "TODOTest1", "PENDING", LocalDate.now());
        TodoItem item2 = new TodoItem(2L, "TODOTest2", "TODOTest2", "PENDING", LocalDate.now());
        TodoItem item3 = new TodoItem(3L, "TODOTest3", "TODOTest3", "COMPLETE", LocalDate.now());

        list.add(item1);
        list.add(item2);
        list.add(item3);

        doNothing().when(todoTaskBO).updateTodos(list);
        //test
        todoTaskService.updateTodos(list);
        verify(todoTaskBO, times(1)).updateTodos(list);
    }

    @Test
    public void testUpdateSingleTodoItemDetails() throws ResourceNotFoundException {
        TodoItem item1 = new TodoItem(1L, "TODOTest1", "TODOTest1", "PENDING", LocalDate.now());
        TodoItem item2 = new TodoItem(1L, "TODOTest11", "TODOTest1", "PENDING", LocalDate.now());

        doNothing().when(todoTaskBO).updateTodo(item1, item2);
        //test
        todoTaskService.updateTodo(item1,item2);
        verify(todoTaskBO, times(1)).updateTodo(item1,item2);
    }

    @Test
    public void testMakeTodoitemDone() throws ResourceNotFoundException {
        TodoItem item1 = new TodoItem(1L, "TODOTest1", "TODOTest1", "COMPLETE", LocalDate.now());

        doNothing().when(todoTaskBO).makeTodoitemDone(item1);
        //test
        todoTaskService.makeTodoitemDone(item1);
        verify(todoTaskBO, times(1)).makeTodoitemDone(item1);
    }
}
