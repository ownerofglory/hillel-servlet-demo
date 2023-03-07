package ua.hillel.db;


import org.apache.ibatis.jdbc.ScriptRunner;
import ua.hillel.exception.DbInitException;

import java.io.*;
import java.sql.Connection;

public class DbSchemaInitializer {
    public static void init(Connection connection, String filePath) throws DbInitException {
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        String script = "CREATE TABLE IF NOT EXISTS todoList (\n" +
                "  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  name TEXT NOT NULL\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS todo (\n" +
                "  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  title TEXT NOT NULL,\n" +
                "  status INTEGER DEFAULT 0,\n" +
                "  todoListId INTEGER,\n" +
                "  FOREIGN KEY (todoListId) REFERENCES todoList(id)\n" +
                ");\n";

        byte[] bytes = script.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        scriptRunner.runScript(bufferedReader);
    }
}
