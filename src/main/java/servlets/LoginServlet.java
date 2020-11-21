package servlets;

import DAO.Interfaces.UsersDao;
import DAO.Repositories.UsersDaoImpl;
import models.User;
import services.CookieService;
import services.Helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private Helper helper;
    private UsersDao udi;
    private CookieService cs;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        helper.render(req, resp, "login.ftl",new HashMap<>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String check = req.getParameter("remember");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        Map<String, Object> root = new HashMap<>();

        Optional<Long> id = udi.login(username, password);
        if(id.isPresent()){
            HttpSession session = req.getSession();
            session.setAttribute("user_id", id);
            if (check.equals("true")) {
                Cookie cookie = new Cookie("user_id", id.toString());
                addCookie(cookie, resp);
            }
            helper.render(req, resp, "profile.ftl", root);
        }else{
            root.put("message","incorrect password or username");
            helper.render(req, resp, "login.ftl", root);
        }
    }

    private void addCookie(Cookie c, HttpServletResponse resp) {
        c.setMaxAge(-1);
        resp.addCookie(c);
    }

    @Override
    public void init() throws ServletException {
        helper = new Helper();
        udi = new UsersDaoImpl();
        cs = new CookieService();
    }
}