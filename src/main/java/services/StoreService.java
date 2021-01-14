package services;

import models.*;

import java.util.List;

public interface StoreService {

    List<CarBody> getCarBodies();
    List<CarBrand> getCarBrands();
    List<CarEngine> getCarEngines();
    List<CarModel> getCarModels();
    List<CarType> getCarTypes();

    List<SellerContact> getSellerContact(User user);
    List<Announcement> getAnnouncements(User user, int page);
    Announcement getFullAnnouncements(Announcement announcement);
    List<CarPhoto> getCarPhotos(Announcement announcement);
    List<CarPhoto> getCarPhotos(List<CarPhoto> carPhotos);
    void addAnnouncement(Announcement announcement);
    void editAnnouncement(Announcement announcement);

    List<CarPhoto> addCarPhotos(List<CarPhoto> carPhotos);
    CarPhoto addCarPhoto(CarPhoto carPhotos);
    void deleteCarPhoto(CarPhoto carPhoto);

    void addSellerContact(SellerContact sellerContact);
    void deleteSellerContact(SellerContact sellerContact);

    User addUser(User user);
    User getUser(User user);
}
