package ua.hillel.db;


import org.apache.ibatis.jdbc.ScriptRunner;
import ua.hillel.exception.DbInitException;

import java.io.*;
import java.sql.Connection;

public class DbSchemaInitializer {
    public static void init(Connection connection, String filePath) throws DbInitException {
        InputStream resourceAsStream = DbSchemaInitializer.class.getResourceAsStream(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));

        ScriptRunner scriptRunner = new ScriptRunner(connection);

        scriptRunner.runScript(bufferedReader);
    }
}
