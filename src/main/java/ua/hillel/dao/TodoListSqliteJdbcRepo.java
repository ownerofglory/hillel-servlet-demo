package ua.hillel.dao;

import ua.hillel.exception.TodoAppGeneralException;
import ua.hillel.model.TodoList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoListSqliteJdbcRepo implements TodoListRepo {
    private final Connection connection;

    public TodoListSqliteJdbcRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<TodoList> getAll() throws TodoAppGeneralException {
        String sql = "SELECT * FROM todoList";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {

            ResultSet resultSet = pst.executeQuery();

            List<TodoList> todoLists = new ArrayList<>();
            while (resultSet.next()) {
                TodoList todoList = new TodoList();
                todoList.setId(resultSet.getInt("id"));
                todoList.setName(resultSet.getString("name"));
                todoLists.add(todoList);
            }
            return todoLists;
        } catch (SQLException e) {
            throw new TodoAppGeneralException("Unable to read todo lists: " + e.getMessage(), e);
        }
    }

    @Override
    public void add(TodoList todoList) throws TodoAppGeneralException {
        String sql = "INSERT INTO todoList (" +
                "name" +
                ") VALUES(?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, todoList.getName());

            pst.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new TodoAppGeneralException("Unable to create a todo list: " + e.getMessage(), e);
        }
    }
}
