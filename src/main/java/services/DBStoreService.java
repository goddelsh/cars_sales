package services;

import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DBStoreService implements StoreService {

    static private StoreService service = new DBStoreService();

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public DBStoreService() {
        if (getCarBrands().isEmpty()) {
            List<CarModel> carModels = getCarModels();
            if (carModels.isEmpty()) {
                carModels = List.of(new CarModel("X2 (F39)"),
                        new CarModel("X3 (G01)"),
                        new CarModel("X5 (G05)"),
                        new CarModel("AMG A35"),
                        new CarModel("AMG GT 63"),
                        new CarModel("AMG C63"),
                        new CarModel("A3"),
                        new CarModel("A4"));
            }
            init(List.of(new CarBrand("BMW", List.of(carModels.get(0), carModels.get(1), carModels.get(2))),
                    new CarBrand("Mercedes", List.of(carModels.get(3), carModels.get(4), carModels.get(5))),
                    new CarBrand("Audi", List.of(carModels.get(6), carModels.get(7)))));
        }
        if (getCarBodies().isEmpty()) {
            init(List.of(new CarBody("SEDAN"),
                    new CarBody("COUPE"),
                    new CarBody("HATCHBACK"),
                    new CarBody("MINIVAN"),
                    new CarBody("PICKUP")));
        }
        if (getCarEngines().isEmpty()) {
            init(List.of(new CarEngine("Diesel"),
                    new CarEngine("Rotary"),
                    new CarEngine(" Straight"),
                    new CarEngine(" Electrical")));
        }

        if (getCarTypes().isEmpty()) {
            init(List.of(new CarType("CVT"),
                    new CarType("Automatic")));
        }
    }

    static public StoreService getStoreService() {
        return service;
    }

    private <T> void init(List<T> collection) {
        collection.forEach(element -> tx(session -> {
            session.save(element);
            return null;
        }));
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    @Override
    public List<CarBody> getCarBodies() {
        return tx(session -> session.createQuery("from models.CarBody").list());
    }

    @Override
    public List<CarBrand> getCarBrands() {
        return tx(session -> session.createQuery("select distinct b from models.CarBrand b left join fetch b.models").list());
    }

    @Override
    public List<CarEngine> getCarEngines() {
        return tx(session -> session.createQuery("from models.CarEngine").list());
    }

    @Override
    public List<CarModel> getCarModels() {
        return tx(session -> session.createQuery("from models.CarModel ").list());
    }

    @Override
    public List<CarType> getCarTypes() {
        return tx(session -> session.createQuery("from models.CarType").list());
    }

    @Override
    public List<SellerContact> getSellerContact(User user) {
        return null;
    }

    @Override
    public List<Announcement> getAnnouncements(User user, int page) {
        if (page < 1) {
            throw new IllegalStateException("Wrong page value");
        }
        return tx(session -> {
            List<Announcement> result = null;
            if (user != null) {
                result =  session.createQuery("select new Announcement(a.id, a.title, a.description, a.price, a.createTime, a.status) from models.Announcement a where user_id = :user_id order by a.createTime desc")
                        .setParameter("user_id", user.getId()).setFirstResult((page - 1) * 20).setMaxResults(20).list();
            } else {
                result = session.createQuery("select new Announcement(a.id, a.title, a.description, a.price, a.createTime, a.status) from models.Announcement a where a.status = 0 order by a.createTime desc")
                        .setFirstResult((page - 1) * 20).setMaxResults(20).list();
            }
            return result;
        });
    }


    @Override
    public Announcement getFullAnnouncements(Announcement announcement) {
        return tx(session -> (Announcement) session.createQuery("from models.Announcement m "
                + "left join fetch  m.carBody "
                + "left join fetch  m.brand "
                + "left join fetch  m.carEngine "
                + "left join fetch  m.model "
                + "left join fetch  m.type "
                + "left join fetch  m.photos "
                + "where m.id = :id").setParameter("id", announcement.getId()).list().stream().findFirst().orElse(null));
    }

    @Override
    public List<CarPhoto> getCarPhotos(Announcement announcement) {
        return null;
    }

    @Override
    public List<CarPhoto> getCarPhotos(List<CarPhoto> carPhotos) {
        return tx(session -> session.createQuery("from models.CarPhoto where id in :idList")
                .setParameter(":idList", carPhotos.stream().map(photo -> photo.getId()).collect(Collectors.toList())).list()
        );
    }

    @Override
    public void addAnnouncement(Announcement announcement) {
        tx(session -> {
            session.save(announcement);
            return announcement;
        });
    }

    @Override
    public void editAnnouncement(Announcement announcement) {
        tx(session -> {
            session.update(announcement);
            return null;
        });
    }

    @Override
    public List<CarPhoto> addCarPhotos(List<CarPhoto> carPhotos) {
        return tx(session -> {
            carPhotos.forEach(photo -> photo.setId((Integer) session.save(photo)));
            return carPhotos;
        });
    }

    @Override
    public CarPhoto addCarPhoto(CarPhoto carPhotos) {
        return tx(session -> {
            carPhotos.setId((Integer) session.save(carPhotos));
            return carPhotos;
        });
    }


    @Override
    public void deleteCarPhoto(CarPhoto carPhoto) {

    }

    @Override
    public void addSellerContact(SellerContact sellerContact) {

    }

    @Override
    public void deleteSellerContact(SellerContact sellerContact) {

    }

    @Override
    public User addUser(User user) {
        return tx(session -> {
            session.save(user);
            return user;
        });
    }

    @Override
    public User getUser(User user) {
        return tx(session -> (User) session.createQuery("from models.User where email=:email and password=:password")
                .setParameter("email", user.getEmail())
                .setParameter("password", user.getPassword())
                .list().stream()
                .findFirst().orElse(null));
    }


}
