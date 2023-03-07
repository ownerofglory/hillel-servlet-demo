package ua.hillel.service;

import ua.hillel.exception.TodoAppGeneralException;
import ua.hillel.model.Todo;
import ua.hillel.model.TodoList;
import ua.hillel.dao.TodoRepo;

import java.util.List;

public class TodoServiceImpl implements TodoService {
    private final TodoRepo repo;

    public TodoServiceImpl(TodoRepo repo) {
        this.repo = repo;
    }

    @Override
    public void addTodo(Todo todo) throws TodoAppGeneralException {
        repo.addTodo(todo);
    }

    @Override
    public List<Todo> getTodosByList(TodoList todoList) throws TodoAppGeneralException {
        Integer id = todoList.getId();
        List<Todo> todos = repo.getTodosByListId(id);
        return todos;
    }

    @Override
    public void toggleTodoStatus(Todo todo) throws TodoAppGeneralException {
        todo.setStatus(!todo.getStatus());
        repo.updateTodo(todo);
    }
}
