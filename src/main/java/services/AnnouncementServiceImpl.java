package services;

import models.Announcement;
import models.User;

import java.util.List;

public class AnnouncementServiceImpl implements AnnouncementService {
    @Override
    public List<Announcement> getAnnouncements(User user) {
        return null;
    }

    @Override
    public Announcement createAnnouncement(User user, Announcement announcement) {
        return null;
    }

    @Override
    public boolean deleteAnnouncement(User user, Announcement announcement) {
        return false;
    }

    @Override
    public boolean editAnnouncement(User user, Announcement announcement) {
        return false;
    }
}
