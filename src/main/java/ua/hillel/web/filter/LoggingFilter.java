package ua.hillel.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoggingFilter extends HttpFilter {
    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String method = req.getMethod();
        if (method.equalsIgnoreCase("GET")) {
            String queryString = req.getQueryString();

            System.out.printf("Incoming request: %s; query: %s %n", method, queryString);
        } else if (method.equalsIgnoreCase("POST")) {
            Map<String, String[]> parameterMap = req.getParameterMap();

            System.out.printf("Incoming request: %s; params: %s %n", method, parameterMap);
        }

        chain.doFilter(req, res);

        int status = res.getStatus();

        System.out.printf("Response status: %d %n", status);
    }
}
