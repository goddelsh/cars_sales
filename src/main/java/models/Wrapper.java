package models;

import java.util.List;

public class Wrapper {

    private Actions action;
    private int page = 1;
    private int pages = 10;
    private String filter;
    private List<Announcement> announcements;
    private List<CarBody> carBodies;
    private List<CarBrand> carBrands;
    private List<CarEngine> carEngines;
    private List<CarModel> carModels;
    private List<CarType> carTypes;
    private List<SellerContact> sellerContacts;
    private List<CarPhoto> carPhotos;
    private String status;

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public List<CarBody> getCarBodies() {
        return carBodies;
    }

    public void setCarBodies(List<CarBody> carBodies) {
        this.carBodies = carBodies;
    }

    public List<CarBrand> getCarBrands() {
        return carBrands;
    }

    public void setCarBrands(List<CarBrand> carBrands) {
        this.carBrands = carBrands;
    }

    public List<CarEngine> getCarEngines() {
        return carEngines;
    }

    public void setCarEngines(List<CarEngine> carEngines) {
        this.carEngines = carEngines;
    }

    public List<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(List<CarModel> carModels) {
        this.carModels = carModels;
    }

    public List<CarType> getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(List<CarType> carTypes) {
        this.carTypes = carTypes;
    }

    public List<SellerContact> getSellerContacts() {
        return sellerContacts;
    }

    public void setSellerContacts(List<SellerContact> sellerContacts) {
        this.sellerContacts = sellerContacts;
    }

    public List<CarPhoto> getCarPhotos() {
        return carPhotos;
    }

    public void setCarPhotos(List<CarPhoto> carPhotos) {
        this.carPhotos = carPhotos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

}
