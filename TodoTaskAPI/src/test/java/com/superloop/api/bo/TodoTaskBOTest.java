package com.superloop.api.bo;

import com.superloop.api.bo.impl.TodoTaskBOImpl;
import com.superloop.api.dao.TodoItemRepository;
import com.superloop.api.exception.ResourceNotFoundException;
import com.superloop.api.model.TodoItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TodoTaskBOTest {

    private static final String TODO_ITEM_NAME = "TODOTest1";
    private static final String PENDING_STATUS = "PENDING";


    @Mock
    TodoItemRepository todoItemRepository;

    @InjectMocks
    TodoTaskBO todoTaskBO = new TodoTaskBOImpl();

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

        when(todoItemRepository.findByStatus("PENDING")).thenReturn(list);
        //test
        List<TodoItem> todoList = todoTaskBO.getAllTodos(PENDING_STATUS);

        assertEquals(3, todoList.size());
        verify(todoItemRepository, times(1)).findByStatus(PENDING_STATUS);
    }

    @Test
    public void testGetTodoDetails() throws ResourceNotFoundException {
        TodoItem item1 = new TodoItem(1L, TODO_ITEM_NAME, TODO_ITEM_NAME, PENDING_STATUS, LocalDate.now());

        when(todoItemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        //test
        TodoItem todoItem = todoTaskBO.getTodoDetailsWithId(1L);
        assertNotNull(todoItem);
        verify(todoItemRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTodoDetailsResourceNotFound()  {
        TodoItem item1 = new TodoItem(1L, TODO_ITEM_NAME, TODO_ITEM_NAME, PENDING_STATUS, LocalDate.now());

        when(todoItemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        assertThrows(ResourceNotFoundException.class,
                () -> {
                    todoTaskBO.getTodoDetailsWithId(2L);
                });
    }

    @Test
    public void testCreateTodo()  {
        TodoItem item1 = new TodoItem(1L, TODO_ITEM_NAME, TODO_ITEM_NAME, PENDING_STATUS, LocalDate.now());

        when(todoItemRepository.save(item1)).thenReturn(item1);
        //test
        TodoItem todoItem = todoTaskBO.createTodoItem(item1);
        assertEquals(todoItem.getName(), TODO_ITEM_NAME);
        assertEquals(todoItem.getDescription(), TODO_ITEM_NAME);
        assertEquals(todoItem.getStatus(), PENDING_STATUS);
        verify(todoItemRepository, times(1)).save(item1);
    }

    @Test
    public void testCreateTodoWithStatusPending()  {
        TodoItem item1 = new TodoItem(1L, TODO_ITEM_NAME, TODO_ITEM_NAME, "", LocalDate.now());

        when(todoItemRepository.save(item1)).thenReturn(item1);
        //test
        TodoItem todoItem = todoTaskBO.createTodoItem(item1);
        assertEquals(todoItem.getName(), TODO_ITEM_NAME);
        assertEquals(todoItem.getDescription(), TODO_ITEM_NAME);
        assertEquals(todoItem.getStatus(), PENDING_STATUS);
        verify(todoItemRepository, times(1)).save(item1);
    }

    @Test
    public void testCreateTodoWithCompleteStatusToPending() {
        TodoItem item1 = new TodoItem(1L, TODO_ITEM_NAME, TODO_ITEM_NAME, "COMPLETE", LocalDate.now());

        when(todoItemRepository.save(item1)).thenReturn(item1);
        //test
        TodoItem todoItem = todoTaskBO.createTodoItem(item1);
        assertEquals(todoItem.getName(), TODO_ITEM_NAME);
        assertEquals(todoItem.getDescription(), TODO_ITEM_NAME);
        assertEquals(todoItem.getStatus(), PENDING_STATUS);
        verify(todoItemRepository, times(1)).save(item1);
    }

    @Test
    public void testDeleteTodoItemDetails() throws ResourceNotFoundException {
        TodoItem item1 = new TodoItem(1L, TODO_ITEM_NAME, TODO_ITEM_NAME, PENDING_STATUS, LocalDate.now());

        doNothing().when(todoItemRepository).delete(item1);
        //test
        todoTaskBO.deleteTodoDetails(item1);
        verify(todoItemRepository, times(1)).delete(item1);
    }

    @Test
    public void testUpdateMultipleTodoItemDetails() {
        List<TodoItem> list = new ArrayList<TodoItem>();
        TodoItem item1 = new TodoItem(1L, "TODOTest1", "TODOTest1", "PENDING", LocalDate.now());

        list.add(item1);

        when(todoItemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        when(todoItemRepository.save(item1)).thenReturn(item1);

        //test
        todoTaskBO.updateTodos(list);
        verify(todoItemRepository, times(1)).findById(1L);
        verify(todoItemRepository, times(1)).save(item1);

    }

    @Test
    public void testUpdateMultipleTodoItemDetailsNumberFormatException() {
        List<TodoItem> list = new ArrayList<TodoItem>();
        TodoItem item1 = new TodoItem(1L, "TODOTest1", "TODOTest1", "PENDING", LocalDate.now());
        list.add(item1);

        when(todoItemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        List<TodoItem> list2 = new ArrayList<TodoItem>();
        TodoItem item2 = new TodoItem(1L, "TODOTest2", "TODOTest1", "PENDING", LocalDate.now());
        list2.add(item2);
        when(todoItemRepository.save(item1)).thenReturn(item1);

        //test
        todoTaskBO.updateTodos(list2);
        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("One");
        });
    }

    @Test
    public void testUpdateSingleTodoItemDetails() {
        TodoItem item1 = new TodoItem(1L, "TODOTest1", "TODOTest1", "PENDING", LocalDate.now());
        TodoItem item2 = new TodoItem(1L, "TODOTest11", "TODOTest1", "PENDING", LocalDate.now());

        when(todoItemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        when(todoItemRepository.save(item1)).thenReturn(item1);
        //test
        todoTaskBO.updateTodo(item1,item2);
        verify(todoItemRepository, times(1)).save(item1);
    }

    @Test
    public void testMakeTodoitemDone() throws ResourceNotFoundException {
        TodoItem item1 = new TodoItem(1L, "TODOTest2", "TODOTest1", "COMPLETE", LocalDate.now());
        when(todoItemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        when(todoItemRepository.save(item1)).thenReturn(item1);
        //test
        todoTaskBO.makeTodoitemDone(item1);
        verify(todoItemRepository, times(1)).save(item1);
    }

    @Test
    public void testMakeTodoitemDoneResourceNotFound()  {
        TodoItem item1 = new TodoItem(1L, TODO_ITEM_NAME, TODO_ITEM_NAME, PENDING_STATUS, LocalDate.now());

        when(todoItemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        assertThrows(ResourceNotFoundException.class,
                () -> {
                    todoTaskBO.getTodoDetailsWithId(2L);
                });
    }

    @Test
    public void testUpdateMultipleTodoItemDetailsResourceNotFound()  {
        List<TodoItem> list = new ArrayList<TodoItem>();
        TodoItem item1 = new TodoItem(1L, "TODOTest1", "TODOTest1", "PENDING", LocalDate.now());

        List<TodoItem> list1 = new ArrayList<TodoItem>();
        TodoItem item2 = new TodoItem(2L, "TODOTest1", "TODOTest1", "PENDING", LocalDate.now());

        list.add(item1);
        list1.add(item2);

        when(todoItemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        when(todoItemRepository.save(item1)).thenReturn(item1);

        //test
        todoTaskBO.updateTodos(list1);
        assertThrows(ResourceNotFoundException.class,
                () -> {
                    todoTaskBO.getTodoDetailsWithId(2L);
                });
    }
}
