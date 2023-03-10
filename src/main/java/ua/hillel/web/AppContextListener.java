package ua.hillel.web;

import ua.hillel.dao.TodoListRepo;
import ua.hillel.dao.TodoListSqliteJdbcRepo;
import ua.hillel.dao.TodoRepo;
import ua.hillel.dao.TodoSqliteJdbcRepo;
import ua.hillel.db.DbSchemaInitializer;
import ua.hillel.exception.DbInitException;
import ua.hillel.service.TodoListService;
import ua.hillel.service.TodoListServiceImpl;
import ua.hillel.service.TodoService;
import ua.hillel.service.TodoServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppContextListener implements ServletContextListener {
    private Connection connection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            Class.forName("org.sqlite.JDBC");
            ServletContext context = sce.getServletContext();

            String dbFilePath = context.getInitParameter("db-file");
            String dbPath = getClass().getResource(dbFilePath).getPath();

            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            DbSchemaInitializer.init(connection, "/todos-schema.sql");

            TodoListRepo listRepo = new TodoListSqliteJdbcRepo(connection);
            TodoListService listService = new TodoListServiceImpl(listRepo);

            TodoRepo todoRepo = new TodoSqliteJdbcRepo(connection);
            TodoService todoService = new TodoServiceImpl(todoRepo);

            HelloServlet helloServlet = new HelloServlet(listService);

            context.addServlet("hello-servlet", helloServlet)
                    .addMapping("/hello");

            TodoAppServlet todoAppServlet = new TodoAppServlet(listService, todoService);

            context.addServlet("todo-app-servlet", todoAppServlet)
                    .addMapping("/app", "/app/*");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DbInitException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
