package servlets;

import DAO.Interfaces.UsersDao;
import DAO.Repositories.UsersDaoImpl;
import services.Helper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class ProfileServlet extends HttpServlet {
    private Helper helper;
    private UsersDao udi;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        helper.render(req, resp, "profile.ftl", new HashMap<>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public void init() throws ServletException {
        helper = new Helper();
        udi = new UsersDaoImpl();
    }
}
