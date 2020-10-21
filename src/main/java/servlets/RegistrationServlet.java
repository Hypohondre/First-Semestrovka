package servlets;

import models.User;
import services.Helper;
import services.RegService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private Helper helper;
    private RegService regService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        helper.render(req, resp, "registration.ftl", new HashMap<>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User(username, password);
        if (regService.registration(user)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            resp.sendRedirect("/login");
        } else {
            Map<String, Object> root = new HashMap<>();
            root.put("errorMessage", "Incorrect login or password!");
            helper.render(req, resp, "registration.ftl", root);
        }
    }

    @Override
    public void init() throws ServletException {
        helper = new Helper();
        regService = new RegService();
    }
}
