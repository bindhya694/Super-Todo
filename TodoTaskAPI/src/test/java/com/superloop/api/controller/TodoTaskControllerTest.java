package com.superloop.api.controller;

import com.superloop.api.model.TodoItem;
import com.superloop.api.service.TodoTaskService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.superloop.api.util.TodoTaskConstants.COMPLETE_STATUS;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.superloop.api.util.TodoTaskConstants.PENDING_STATUS;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoTaskControllerTest {

    @MockBean
    private TodoTaskService todoTaskService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setupHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }

    public static final String ROOT_URL = "http://localhost:";
    public static final String GET_TODO_LIST_PENDING = "/api/v1/todos/PENDING";
    public static final String GET_TODO_LIST_COMPLETE = "/api/v1/todos/COMPLETE";
    public static final String CREATE_TODO = "/api/v1/todo";
    public static final String GET_TODO_DETAILS = "/api/v1/todo/";
    public static final String DELETE_TODO_DETAILS = "/api/v1/todo/";
    public static final String UPDATE_TODO_DETAILS = "/api/v1/todos/";
    public static final String UPDATE_SINGLE_TODO_DETAIL = "/api/v1/todo/";
    public static final String MAKE_TODO_ITEM_COMPLETE = "/api/v1/todoStatus";

    @Test
    public void shouldReturnTodoItemsWithPendingStatus() throws Exception {
        List<TodoItem> getTodoData = getTodoTestDataForPending();
        when(todoTaskService.getAllTodos(PENDING_STATUS)).thenReturn(getTodoData);

        this.mockMvc.perform(get(GET_TODO_LIST_PENDING)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json(jsonResponsePendingStatus()));
    }

    @Test
    public void shouldReturnTodoItemsWithCompleteStatus() throws Exception {
        List<TodoItem> getTodoData = getTodoTestDataForComplete();
        when(todoTaskService.getAllTodos(COMPLETE_STATUS)).thenReturn(getTodoData);

        this.mockMvc.perform(get(GET_TODO_LIST_COMPLETE)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json(jsonResponseCompleteStatus()));
    }

    @Test
    public void shouldCreateTodoItem() throws Exception {
        TodoItem todoData = createOrReturnTodoTestData();
        when(todoTaskService.createTodoItem(todoData)).thenReturn(createOrReturnTodoTestData());

        this.mockMvc.perform(post(CREATE_TODO).contentType(MediaType.APPLICATION_JSON).
                content("{\"id\":1,\"name\":\"TestTODO1\"," +
                        "\"description\":\"This is another Test Todo\",\"status\":\"PENDING\",\"dueDate\":\"2021-01-30\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRetrieveTodoItemDetailsForASingleTodo() throws Exception {
        when(todoTaskService.getTodoDetailsWithId(1L)).thenReturn(createOrReturnTodoTestData());

        this.mockMvc.perform(get(GET_TODO_DETAILS + 1L).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json("{\"id\":1,\"name\":\"TestTODO1\"," +
                "\"description\":\"This is a Test Todo\",\"status\":\"PENDING\",\"dueDate\":\"2021-01-30\"}"));
    }

    @Test
    public void shouldDeleteTodoItemDetailsForASingleTodo() throws Exception {
        TodoItem todoData = createOrReturnTodoTestData();
        when(todoTaskService.getTodoDetailsWithId(1L)).thenReturn(createOrReturnTodoTestData());
        doNothing().when(todoTaskService).deleteTodoDetails(todoData);
        MvcResult result = this.mockMvc.perform(delete(DELETE_TODO_DETAILS + 1L).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String response = result.getResponse().getContentAsString();
        assertEquals(response, "{\"deleted\":true}");
    }

    @Test
    public void shouldUpdateTodoItemStatusForMultipleTodos() throws Exception {
        List<TodoItem> todoData = getTodoTestDataForPending();
        doNothing().when(todoTaskService).updateTodos(todoData);
        MvcResult result = this.mockMvc.perform(put(UPDATE_TODO_DETAILS).contentType(MediaType.APPLICATION_JSON).
                content("[{\"id\":1,\"name\":\"TestTODO1\"," +
                        "\"description\":\"This is another Test Todo\",\"status\":\"PENDING\",\"dueDate\":\"2021-01-30\"}]")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String response = result.getResponse().getContentAsString();
        assertEquals(response, "{\"updated\":true}");
    }

    @Test
    public void shouldUpdateTodoItemStatusForSingleTodo() throws Exception {
        List<TodoItem> todoData = getTodoTestDataForPending();
        doNothing().when(todoTaskService).updateTodos(todoData);
        MvcResult result = this.mockMvc.perform(put(UPDATE_SINGLE_TODO_DETAIL+1L).contentType(MediaType.APPLICATION_JSON).
                content("{\"id\":1,\"name\":\"TestTODO1\"," +
                        "\"description\":\"This is another Test Todo\",\"status\":\"PENDING\",\"dueDate\":\"2021-01-30\"}")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String response = result.getResponse().getContentAsString();
        assertEquals(response, "{\"updated\":true}");
    }

    @Test
    public void shouldMakeTodoitemDone() throws Exception {
        TodoItem todoData = createOrReturnTodoTestData();
        doNothing().when(todoTaskService).makeTodoitemDone(todoData);
        MvcResult result = this.mockMvc.perform(put(MAKE_TODO_ITEM_COMPLETE).contentType(MediaType.APPLICATION_JSON).
                content("{\"id\":1,\"name\":\"TestTODO1\"," +
                        "\"description\":\"This is another Test Todo\",\"status\":\"COMPLETE\",\"dueDate\":\"2021-01-30\"}")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String response = result.getResponse().getContentAsString();
        assertEquals(response, "{\"updated\":true}");
    }

    private TodoItem createOrReturnTodoTestData() {
        TodoItem todo = new TodoItem();
        todo.setId(1L);
        todo.setStatus("PENDING");
        todo.setDueDate(LocalDate.parse("2021-01-30"));
        todo.setName("TestTODO1");
        todo.setDescription("This is a Test Todo");
        return todo;
    }

    private String jsonResponsePendingStatus() {
        return "[{\"id\":1,\"name\":\"TestTODOPending\"," +
                "\"description\":\"This is another PENDING Test Todo\",\"status\":\"PENDING\",\"dueDate\":\"2021-01-30\"}]";
    }

    private List<TodoItem> getTodoTestDataForPending() {
        List<TodoItem> listOdTodo = new ArrayList<TodoItem>();
        TodoItem todo = new TodoItem();
        todo.setStatus("PENDING");
        todo.setId(1L);
        todo.setName("TestTODOPending");
        todo.setDueDate(LocalDate.parse("2021-01-30"));
        todo.setDescription("This is another PENDING Test Todo");
        listOdTodo.add(todo);
        return listOdTodo;
    }

    private List<TodoItem> getTodoTestDataForComplete() {
        List<TodoItem> listOdTodo = new ArrayList<TodoItem>();
        TodoItem todo = new TodoItem();
        todo.setStatus("COMPLETE");
        todo.setId(1L);
        todo.setName("TestTODOComplete");
        todo.setDueDate(LocalDate.parse("2021-01-30"));
        todo.setDescription("This is another COMPLETE Test Todo");
        listOdTodo.add(todo);
        return listOdTodo;
    }


    private String jsonResponseCompleteStatus() {
        return "[{\"id\":1,\"name\":\"TestTODOComplete\"," +
                "\"description\":\"This is another COMPLETE Test Todo\",\"status\":\"COMPLETE\",\"dueDate\":\"2021-01-30\"}]";
    }

}
