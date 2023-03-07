package ua.hillel.model;

import java.util.List;
import java.util.Objects;

public class TodoList {
    private Integer id;
    private String name;

    private List<Todo> todos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoList todoList = (TodoList) o;
        return Objects.equals(id, todoList.id) && Objects.equals(name, todoList.name) && Objects.equals(todos, todoList.todos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, todos);
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", todos=" + todos +
                '}';
    }
}
