package ua.hillel.dao;

import ua.hillel.exception.TodoAppGeneralException;
import ua.hillel.model.Todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoSqliteJdbcRepo implements TodoRepo {
    private final Connection connection;

    public TodoSqliteJdbcRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addTodo(Todo todo) throws TodoAppGeneralException {
        String sql = "INSERT INTO todo (" +
                "title, todoListId" +
                ") VALUES(?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, todo.getTitle());
            pst.setInt(2, todo.getTodoList().getId());

            pst.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new TodoAppGeneralException("Unable to create todo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Todo> getTodosByListId(Integer listId) throws TodoAppGeneralException {
        String sql = "SELECT * FROM todo " +
                "WHERE todoListId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, listId);

            ResultSet resultSet = pst.executeQuery();

            List<Todo> todos = new ArrayList<>();
            while (resultSet.next()) {
                Todo todo = new Todo();
                todo.setId(resultSet.getInt("id"));
                todo.setTitle(resultSet.getString("title"));
                todo.setStatus(resultSet.getInt("status") == 1);
                todos.add(todo);
            }
            return todos;
        } catch (SQLException e) {
            throw new TodoAppGeneralException("Unable to read todos listId: " + listId + e.getMessage(), e);
        }
    }

    @Override
    public void updateTodo(Todo todo) throws TodoAppGeneralException {
        String sql = "UPDATE todo " +
                "SET status = ? " +
                "WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, todo.getStatus() ? 1 : 0);
            pst.setInt(2, todo.getId());

            pst.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new TodoAppGeneralException("Unable to update todo: " + todo + e.getMessage(), e);
        }
    }
}
