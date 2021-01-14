package servlets;

import com.google.gson.Gson;
import models.Announcement;
import models.User;
import models.Wrapper;
import org.apache.commons.io.IOUtils;
import services.AnnouncementService;
import services.AnnouncementServiceImpl;
import services.DBStoreService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class AnnouncementServlet extends HttpServlet {

    private AnnouncementService announcementService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("announcment");
        req.getRequestDispatcher("/").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String s = IOUtils.toString(new InputStreamReader(req.getInputStream(),
                StandardCharsets.UTF_8));
        Wrapper parsedRequest = new Gson().fromJson(
                s, Wrapper.class);

        User user = (User) req.getSession().getAttribute("user");
        Wrapper responseWrapper = new Wrapper();
        switch (parsedRequest.getAction()) {
            case GET_ANNOUNCEMENTS:
                responseWrapper.setAnnouncements(DBStoreService.getStoreService().getAnnouncements(null, parsedRequest.getPage()));
                responseWrapper.setStatus("OK");
                break;
            case GET_MYANNOUNCEMENTS:
                if (user != null) {
                    responseWrapper.setAnnouncements(DBStoreService.getStoreService().getAnnouncements(user, parsedRequest.getPage()));
                    responseWrapper.setStatus("OK");
                }
                break;
            case GET_ANNOUNCEMENT:
                Announcement forFullAnnouncement = parsedRequest.getAnnouncements().stream().findFirst().orElse(null);
                if (forFullAnnouncement != null) {
                    responseWrapper.setAnnouncements(List.of(DBStoreService.getStoreService().getFullAnnouncements(forFullAnnouncement)));
                    responseWrapper.setStatus("OK");
                }
                break;
            case GET_CARTYPES:
                responseWrapper.setCarTypes(DBStoreService.getStoreService().getCarTypes());
                responseWrapper.setStatus("OK");
                break;
            case GET_CARBODIES:
                responseWrapper.setCarBodies(DBStoreService.getStoreService().getCarBodies());
                responseWrapper.setStatus("OK");
                break;
            case GET_CARBRANDS:
                responseWrapper.setCarBrands(DBStoreService.getStoreService().getCarBrands());
                responseWrapper.setStatus("OK");
                break;
            case GET_CARMODELS:
                responseWrapper.setCarModels(DBStoreService.getStoreService().getCarModels());
                responseWrapper.setStatus("OK");
                break;
            case GET_CARPHOTOS:
                responseWrapper.setCarPhotos(DBStoreService.getStoreService().getCarPhotos(parsedRequest
                        .getAnnouncements().stream()
                        .findFirst().orElse(null)));
                responseWrapper.setStatus("OK");
                break;
            case GET_CARENGINES:
                responseWrapper.setCarEngines(DBStoreService.getStoreService().getCarEngines());
                responseWrapper.setStatus("OK");
                break;
            case GET_SELLERCONTACS:
                responseWrapper.setSellerContacts(DBStoreService.getStoreService().getSellerContact(user));
                responseWrapper.setStatus("OK");
                break;
            case ADD_ANNOUNCEMENTS:
                Announcement announcement = parsedRequest
                        .getAnnouncements().stream()
                        .findFirst().orElse(null);
                if (announcement != null && user != null) {
                    announcement.setUser(user);
                    announcement.setStatus(0);
                    announcement.setCreateTime(new Date(System.currentTimeMillis()));
                    DBStoreService.getStoreService().addAnnouncement(announcement);
                    responseWrapper.setStatus("OK");
                }
                break;
            case EDIT_ANNOUNCEMENTS:
                Announcement announcementFroEditing = parsedRequest
                        .getAnnouncements().stream()
                        .findFirst().orElse(null);
                if (announcementFroEditing != null && user != null) {
                    announcementFroEditing.setUser(user);
                    announcementFroEditing.setCreateTime(new Date(System.currentTimeMillis()));
                    DBStoreService.getStoreService().editAnnouncement(announcementFroEditing);
                    responseWrapper.setStatus("OK");
                }
                DBStoreService.getStoreService().editAnnouncement(parsedRequest
                        .getAnnouncements().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            case ADD_SELLERCONTACT:
                DBStoreService.getStoreService().addSellerContact(parsedRequest
                        .getSellerContacts().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            case DELETE_SELLERCONTACT:
                DBStoreService.getStoreService().deleteSellerContact(parsedRequest
                        .getSellerContacts().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            case ADD_CARPHOTO:
                DBStoreService.getStoreService().addCarPhoto(parsedRequest
                        .getCarPhotos().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            case DELETE_CARPHOTO:
                DBStoreService.getStoreService().deleteCarPhoto(parsedRequest
                        .getCarPhotos().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            default:
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            writer.println(new Gson().toJson(responseWrapper));
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        DBStoreService.getStoreService();
        announcementService = new AnnouncementServiceImpl();
    }

    public static void main(String[] args) {
        String s = "{\"action\":\"ADD_ANNOUNCEMENTS\",\"announcements\":[{\"title\":\"mixed\",\"description\":\"sdf\\ns\\ndf\\n\",\"photos\":[{\"id\":38,\"name\":\"Ajin_ch12_12.png\"},{\"id\":39,\"name\":\"Ajin_ch12_19.png\"}],\"price\":7777,\"type\":{\"id\":1},\"contacs\":[{\"text\":\"111111111\"},{\"text\":\"2222222222\"},{\"text\":\"23333333333\"}],\"brand\":{\"id\":2},\"model\":{\"id\":0},\"carEngine\":{\"id\":3},\"carBody\":{\"id\":1}}]}";
        Wrapper parsedRequest = new Gson().fromJson(s, Wrapper.class);
        System.out.println("11111");
    }
}
