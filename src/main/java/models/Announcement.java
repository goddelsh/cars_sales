package models;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "announcements")
@DynamicUpdate
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "car_type_id")
    private CarType type;
    private Long price;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarPhoto> photos = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "announcements_contacs")
    private List<SellerContact> contacs = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "car_brand_id")
    private CarBrand brand;
    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel model;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @ManyToOne
    @JoinColumn(name = "car_engine_id")
    private CarEngine carEngine;
    @ManyToOne
    @JoinColumn(name = "car_body_id")
    private CarBody carBody;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Integer status;

    private String contact;


    public Announcement(Integer id, String title, String description, Long price, Date createTime, Integer status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.createTime = createTime;
        this.status = status;
    }

    public Announcement(Integer id) {
        this.id = id;
    }

    public Announcement() {
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
//  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  //  private List<SellerContact> contactList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public List<CarPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<CarPhoto> photos) {
        this.photos = photos;
    }

    public List<SellerContact> getContacs() {
        return contacs;
    }

    public void setContacs(List<SellerContact> contacs) {
        this.contacs = contacs;
    }

    public CarBrand getBrand() {
        return brand;
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public CarModel getModel() {
        return model;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CarEngine getCarEngine() {
        return carEngine;
    }

    public void setCarEngine(CarEngine carEngine) {
        this.carEngine = carEngine;
    }

    public CarBody getCarBody() {
        return carBody;
    }

    public void setCarBody(CarBody carBody) {
        this.carBody = carBody;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

//    public List<SellerContact> getContactList() {
//        return contactList;
//    }
//
//    public void setContactList(List<SellerContact> contactList) {
//        this.contactList = contactList;
//    }
}
