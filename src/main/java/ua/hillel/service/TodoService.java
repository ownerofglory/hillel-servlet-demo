package ua.hillel.service;

import ua.hillel.exception.TodoAppGeneralException;
import ua.hillel.model.Todo;
import ua.hillel.model.TodoList;

import java.util.List;

public interface TodoService {
    void addTodo(Todo todo) throws TodoAppGeneralException;
    List<Todo> getTodosByList(TodoList todoList) throws TodoAppGeneralException;
    void toggleTodoStatus(Todo todo) throws TodoAppGeneralException;
}
