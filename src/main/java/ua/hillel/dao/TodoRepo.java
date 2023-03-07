package ua.hillel.dao;

import ua.hillel.exception.TodoAppGeneralException;
import ua.hillel.model.Todo;

import java.util.List;

public interface TodoRepo {
    void addTodo(Todo todo) throws TodoAppGeneralException;
    List<Todo> getTodosByListId(Integer listId) throws TodoAppGeneralException;
    void updateTodo(Todo todo) throws TodoAppGeneralException;
}
