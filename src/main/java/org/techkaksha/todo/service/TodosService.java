package org.techkaksha.todo.service;

import org.techkaksha.todo.model.Todos;

import java.util.List;
import java.util.Optional;

public interface TodosService {
    public List<Todos> getTodos(String userId, int page, int size, String sortField, String direction);
    public Optional<Todos> updateTodos(String id, Todos todos);
    public Todos createTodos(Todos todos);
    public Optional<Todos> deleteTodos(String id);
}
