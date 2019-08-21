package de.nordakademie.iaa.hibernate;

import de.nordakademie.iaa.hibernate.model.Room;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.internal.build.AllowSysOut;

import javax.persistence.EntityManager;

/**
 * Main class for the example application.
 */
public class Application {

    public static void main(String[] args) {
        try {

            Room room = new Room();
            room.setBuilding("Haus A");
            room.setProjectorPresent(false);
            room.setSeats(15);
            persistRoom(room);

            Room result = showRoom(room.getId());
            System.out.println("Buildingname: " + result.getBuilding());

        } catch (RoomAlreadyExistsException e) {
            e.printStackTrace();
        } finally {
            // Required in order to make the thread exit normally
            HibernateUtil.getEntityManagerFactory().close();
        }
    }

    public static void persistRoom(Room room) throws RoomAlreadyExistsException {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(room);
            entityManager.getTransaction().commit();
        }
        catch (ConstraintViolationException e) {
            entityManager.getTransaction().rollback();
            throw new RoomAlreadyExistsException();
        }
    }

    public static Room showRoom(Long id) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Room result = entityManager.find(Room.class, id);
        entityManager.getTransaction().commit();
        return result;
    }

}
