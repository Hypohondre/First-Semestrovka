package servlets;

import DAO.Repositories.UsersDaoImpl;
import models.User;
import services.Helper;
import services.RegistrationHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private Helper helper;
    private RegistrationHandler regService;
    private UsersDaoImpl udi;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        helper.render(req, resp, "registration.ftl", new HashMap<>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String mail = req.getParameter("email");
        String date = req.getParameter("birth");

        Map<String,Object> root = regService.validate(username, password, mail, date);

        if (root.isEmpty()) {
            User user = new User(username, password, mail, Date.valueOf(date));
            udi.save(user);
            resp.sendRedirect("/login");
        } else {
            helper.render(req, resp, "registration.ftl", root);
        }
    }

    @Override
    public void init() throws ServletException {
        helper = new Helper();
        regService = new RegistrationHandler();
        udi = new UsersDaoImpl();
    }
}
