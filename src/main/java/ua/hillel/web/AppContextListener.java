package ua.hillel.web;

import ua.hillel.dao.TodoListRepo;
import ua.hillel.dao.TodoListSqliteJdbcRepo;
import ua.hillel.db.DbSchemaInitializer;
import ua.hillel.exception.DbInitException;
import ua.hillel.service.TodoListService;
import ua.hillel.service.TodoListServiceImpl;

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

            String connString = context.getInitParameter("connection-string");
            connection = DriverManager.getConnection(connString);
            DbSchemaInitializer.init(connection, "todos-schema.sql");

            TodoListRepo listRepo = new TodoListSqliteJdbcRepo(connection);
            TodoListService listService = new TodoListServiceImpl(listRepo);

            HelloServlet helloServlet = new HelloServlet(listService);

            context.addServlet("hello-servlet", helloServlet)
                    .addMapping("/hello");

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
