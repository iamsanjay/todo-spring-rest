package org.techkaksha.todo.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.techkaksha.todo.model.Todos;
import org.techkaksha.todo.service.TodosService;

import java.util.Arrays;
import java.util.Optional;


@WebMvcTest(TodosController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TodosControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    TodosService todosService;

    @BeforeEach
    public void setUp(){

    }
    @Test
    public void todosShouldReturnsSomeData() throws Exception{
        Todos todos = new Todos("123", "Hello World!");
        Mockito.when(todosService.getTodos(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(Arrays.asList(todos));
        this.mockMvc.perform(get("/api/v1/todos")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World!")));
    }
    @Test
    public void todosShouldReturnsOkWhenTodosExists() throws  Exception{
        Todos todos = new Todos("123", "Hello World!");
        Mockito.when(todosService.updateTodos(Mockito.any(String.class), Mockito.any(Todos.class))).thenReturn(Optional.of(todos));
        this.mockMvc
                .perform(put("/api/v1/todos/123")
                        .content(requestJsonForObject(todos))
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(todos.getContent())));
    }

    @Test
    public void todosShouldReturnNotFoundWhenTodosNotExists() throws Exception{
        Todos todos = new Todos("123", "Hello World!");
        Mockito.when(todosService.updateTodos(Mockito.any(String.class), Mockito.any(Todos.class))).thenReturn(Optional.empty());
        this.mockMvc
                .perform(put("/api/v1/todos/123")
                        .content(requestJsonForObject(todos))
                        .contentType("application/json")
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void todosShouldReturnOKWhenTodosCreated() throws Exception{
        Todos todos = new Todos("123", "Hello World!");
        Mockito.when(todosService.createTodos(Mockito.any(Todos.class))).thenReturn(todos);
        this.mockMvc
                .perform(post("/api/v1/todos")
                        .content(requestJsonForObject(todos))
                        .contentType("application/json")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Hello World!")));
    }


    private String requestJsonForObject(Object anObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(anObject );
        return requestJson;
    }
}
