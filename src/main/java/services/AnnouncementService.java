package services;

import models.Announcement;
import models.User;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> getAnnouncements(User user);
    Announcement createAnnouncement(User user, Announcement announcement);
    boolean deleteAnnouncement(User user, Announcement announcement);
    boolean editAnnouncement(User user, Announcement announcement);
}
