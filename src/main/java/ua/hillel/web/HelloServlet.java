package ua.hillel.web;

import ua.hillel.dao.TodoListRepo;
import ua.hillel.dao.TodoListSqliteJdbcRepo;
import ua.hillel.exception.TodoAppGeneralException;
import ua.hillel.model.TodoList;
import ua.hillel.service.TodoListService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class HelloServlet extends HttpServlet {
    private TodoListService service;

    public HelloServlet(TodoListService service) {
        this.service = service;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Received request" + req);

        List<TodoList> todoLists = null;
        try {
            todoLists = service.getTodoLists();

            PrintWriter writer = resp.getWriter();
            writer.println(todoLists);

        } catch (TodoAppGeneralException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        try {
            service.addList(name);
        } catch (TodoAppGeneralException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
