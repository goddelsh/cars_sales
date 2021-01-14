package servlets;

import models.Announcement;
import models.User;
import services.DBStoreService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddAnnouncement extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
            Announcement announcement = DBStoreService.getStoreService().getFullAnnouncements(new Announcement(Integer.parseInt(req.getParameter("id"))));
            req.getSession().setAttribute("announcment", announcement);
            if (user.getId().equals(announcement.getUser().getId())) {
                req.getSession().setAttribute("owner", true);
            } else {
                req.getSession().setAttribute("owner", false);
            }
        }
        req.getRequestDispatcher("/addAnnouncement.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/announcement.do").forward(req, resp);
    }
}
