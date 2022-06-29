package org.techkaksha.todo.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.techkaksha.todo.model.Todos;

import java.util.List;

public interface TodosRepository extends PagingAndSortingRepository<Todos, String> {
    public List<Todos> findTodosByUserId(String userId, Pageable pageable);
}
