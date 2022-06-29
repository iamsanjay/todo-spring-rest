package org.techkaksha.todo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.techkaksha.todo.model.Todos;
import org.techkaksha.todo.repository.TodosRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodosServiceImpl implements TodosService{

    private TodosRepository todosRepository;

    public TodosServiceImpl(TodosRepository todosRepository){
        this.todosRepository = todosRepository;
    }

    @Override
    public List<Todos> getTodos(String userId, int page, int size, String sortField, String direction) {
        PaginationAndSortingBuilder paginationAndSortingBuilder = new PaginationAndSortingBuilder(page, size, sortField, direction);
        Pageable pageRequest = paginationAndSortingBuilder.build();
        List<Todos> pageTodos = todosRepository.findTodosByUserId(userId, pageRequest);
        return pageTodos;
    }

    @Override
    public Optional<Todos> updateTodos(String id, Todos todos) {
        Optional<Todos> existingTodos = todosRepository.findById(id);
        if(existingTodos.isPresent()){
            existingTodos.get().setContent(todos.getContent());
            todosRepository.save(existingTodos.get());
        }
        return existingTodos;
    }

    @Override
    public Todos createTodos(Todos todos) {
        todos = todosRepository.save(todos);
        return todos;
    }

    @Override
    public Optional<Todos> deleteTodos(String id) {
        Optional<Todos> existingTodos = todosRepository.findById(id);
        if(existingTodos.isPresent()){
            todosRepository.delete(existingTodos.get());
        }
        return existingTodos;
    }


    private class PaginationAndSortingBuilder{
        int page;
        int size;
        String sortField;
        String direction;
        public PaginationAndSortingBuilder(int page, int size, String sortField, String direction){
            this.page = page;
            this.size = size;
            this.sortField = sortField;
            this.direction = direction;
        }
        public Pageable build(){
            if("DESC".equals(direction))
                return PageRequest.of(page, size, Sort.by(sortField).descending());
            else  return PageRequest.of(page, size, Sort.by(sortField).ascending());
        }
    }
}
