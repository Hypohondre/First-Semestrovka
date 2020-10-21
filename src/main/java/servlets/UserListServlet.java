package servlets;

import models.User;
import services.Helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static services.LoginService.userList;

@WebServlet("/list")
public class UserListServlet extends HttpServlet {
    private Helper helper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> root = new HashMap<>();

        List<String> names = new ArrayList<>();
        for (User user : userList) {
            names.add(user.getUsername());
        }
        root.put("name", names);

        helper.render(req, resp, "list.ftl", root);
    }

    @Override
    public void init() throws ServletException {
        helper = new Helper();
    }
}
