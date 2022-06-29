package org.techkaksha.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.techkaksha.todo.exception.EntityNotFoundException;
import org.techkaksha.todo.model.Todos;
import org.techkaksha.todo.service.TodosService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path={"/api/v1/todos"}, produces = "application/json")
public class TodosController {

    private static final Logger logger = LoggerFactory.getLogger(TodosController.class);
    private static final String TODOS_UPDATED_LOG = "Todo:{} was updated";
    private static final String TODOS_DELETED_LOG = "Todo:{} was deleted";
    private static final String NEW_ORDER_LOG = "New Todo was created id:{}";
    private static final String TODOS_NOT_EXISTS = "Todo (id=%s) does not exists";
    private static final String ID = "todosId";
    private TodosService todosService;

    public TodosController(TodosService todosService){
        this.todosService = todosService;
    }
    @Operation(summary = "Returns a list of todos")
    @ApiResponse(responseCode = "200", description = "Todos fetched successfully")
    @GetMapping
    public ResponseEntity<List<Todos>> getTodos(
            @RequestParam(required = true, name = "userId") String userId,
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "10") int size,
            @RequestParam(required = false, name = "sortField", defaultValue = "createdAt") String sortField,
            @RequestParam(required = false, name = "direction", defaultValue = "DESC") String direction

    ){
        List<Todos> todos = todosService.getTodos(userId, page, size, sortField, direction);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Update a Todos by its id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Todos updated successfully"),
            @ApiResponse(responseCode = "404", description = "Todos not found")
    })
    @PutMapping(path = "/{todosId}")
    public ResponseEntity<Todos> updateTodos(@PathVariable(value = ID) String id, @RequestBody Todos todos){
        final Optional<Todos> updatedTodos = todosService.updateTodos(id, todos);
        if(updatedTodos.isEmpty()){
            logger.info(String.format(TODOS_NOT_EXISTS, id));
            throw new EntityNotFoundException(Todos.class, "id", id);
        }
        logger.info(TODOS_UPDATED_LOG, updatedTodos.toString());
        return ResponseEntity.ok(updatedTodos.get());
    }

    @Operation(summary = "Delete a Todo by its id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Todos deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Todos not found")
    })
    @DeleteMapping(path = "/{todosId}")
    public ResponseEntity<Todos> deleteTodos(@PathVariable(value = ID) String id){
        final Optional<Todos> deletedTodos = todosService.deleteTodos(id);
        if(deletedTodos.isEmpty()){
            logger.info(String.format(TODOS_NOT_EXISTS, id));
            throw new EntityNotFoundException(Todos.class, "id", id);
        }
        logger.info(TODOS_DELETED_LOG, deletedTodos.toString());
        return ResponseEntity.ok(deletedTodos.get());
    }

    @Operation(summary = "Create a Todo")
    @ApiResponse(responseCode = "201", description = "Todos created successfully")
    @PostMapping
    public ResponseEntity<Todos> createTodos(@RequestBody Todos todos){
        final Todos createdTodos = todosService.createTodos(todos);
        logger.info(NEW_ORDER_LOG, createdTodos.toString());
        return ResponseEntity.ok(createdTodos);
    }

}
