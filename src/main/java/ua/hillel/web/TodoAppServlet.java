package ua.hillel.web;

import ua.hillel.exception.TodoAppGeneralException;
import ua.hillel.model.Todo;
import ua.hillel.model.TodoList;
import ua.hillel.service.TodoListService;
import ua.hillel.service.TodoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TodoAppServlet extends HttpServlet {
    private TodoListService todoListService;
    private TodoService todoService;

    public TodoAppServlet(TodoListService todoListService, TodoService todoService) {
        this.todoListService = todoListService;
        this.todoService = todoService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String queryString = req.getQueryString();

            List<TodoList> todoLists = todoListService.getTodoLists();
            TodoList[] listData = todoLists.toArray(new TodoList[0]);
            req.setAttribute("list-data", listData);

            TodoList activeList = todoLists.get(0);

            int activeListId;
            if (queryString != null) {
                String idString = queryString.split("=")[1];
                activeListId = Integer.parseInt(idString);
                activeList = todoLists.stream()
                        .filter(list -> list.getId() == activeListId)
                        .findFirst().get();
            } else {
                activeListId = activeList.getId();
            }

            req.setAttribute("active-list-id", activeListId);


            List<Todo> todosByList = todoService.getTodosByList(activeList);
            Todo[] todoData = todosByList.toArray(new Todo[0]);
            req.setAttribute("todo-data", todoData);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
            requestDispatcher.include(req, resp);

        } catch (TodoAppGeneralException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Error when getting data");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            List<TodoList> todoLists = todoListService.getTodoLists();
            TodoList[] listData = todoLists.toArray(new TodoList[0]);
            req.setAttribute("list-data", listData);

            TodoList activeList = todoLists.get(0);

            int activeListId = activeList.getId();

            String pathInfo = req.getPathInfo();
            if (pathInfo != null && pathInfo.matches("\\/list\\/\\d{0,10}")) {
                // add new ToDo
                String idStr = pathInfo.split("/")[2];
                int listId = Integer.parseInt(idStr);
                activeListId = listId;

                String title = req.getParameter("todo");

                Todo newTodo = new Todo();
                newTodo.setTitle(title);
                TodoList todoList = new TodoList();
                todoList.setId(listId);
                newTodo.setTodoList(todoList);

                todoService.addTodo(newTodo);
            } else {
                // add new ToDo list
                String name = req.getParameter("list");
                todoListService.addList(name);
            }

            // set active list id
            req.setAttribute("active-list-id", activeListId);
            activeList.setId(activeListId);

            List<Todo> todosByList = todoService.getTodosByList(activeList);
            Todo[] todoData = todosByList.toArray(new Todo[0]);
            req.setAttribute("todo-data", todoData);

            resp.setStatus(HttpServletResponse.SC_CREATED);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
            requestDispatcher.include(req, resp);



        } catch (TodoAppGeneralException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Error when saving data");
        }
    }

}
