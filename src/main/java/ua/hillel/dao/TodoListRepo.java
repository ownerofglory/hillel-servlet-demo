package ua.hillel.dao;

import ua.hillel.exception.TodoAppGeneralException;
import ua.hillel.model.TodoList;

import java.util.List;

public interface TodoListRepo {
    List<TodoList> getAll() throws TodoAppGeneralException;
    void add(TodoList todoList) throws TodoAppGeneralException;
}
