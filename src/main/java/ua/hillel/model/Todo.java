package ua.hillel.model;

import java.util.Objects;

public class Todo {
    private Integer id;
    private String title;
    private Boolean status;
    private TodoList todoList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id) && Objects.equals(title, todo.title) && Objects.equals(status, todo.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, status);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}
