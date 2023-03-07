package ua.hillel.service;

import ua.hillel.exception.TodoAppGeneralException;
import ua.hillel.model.TodoList;

import java.util.List;

public interface TodoListService {
    void addList(String name) throws TodoAppGeneralException;
    List<TodoList> getTodoLists() throws TodoAppGeneralException;
}
