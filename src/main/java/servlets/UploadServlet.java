package servlets;

import com.google.gson.Gson;
import models.CarPhoto;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import services.DBStoreService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name;
        if (req.getParameter("id") != null && req.getParameter("name") != null) {
            name = req.getParameter("name");
            resp.setContentType("name=" + name);
            resp.setContentType("image/png");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
            File file = new File("images" + File.separator + req.getParameter("id") + File.separator + name);
            if (file.exists()) {
                try (FileInputStream in = new FileInputStream(file)) {
                    resp.getOutputStream().write(in.readAllBytes());
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<CarPhoto> carPhotos = new ArrayList<>();
        try {
            List<FileItem> items = upload.parseRequest(req);

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    CarPhoto carPhoto = DBStoreService.getStoreService().addCarPhoto(new CarPhoto(item.getName()));

                    File folder = new File("images" + File.separator + carPhoto.getId());
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    File file = new File(folder + File.separator + item.getName());
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                    carPhotos.add(carPhoto);
                }
            }

        } catch (FileUploadException e) {

        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            writer.println(new Gson().toJson(carPhotos));
        }
    }
}