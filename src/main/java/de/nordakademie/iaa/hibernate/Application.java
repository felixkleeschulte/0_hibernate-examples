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
            //create Room and fill parameters
            Room room = new Room();
            room.setBuilding("Haus A");
            room.setProjectorPresent(false);
            room.setSeats(15);
            room.setRoomNumber(1);

            //persist Room
            persistRoom(room);
            //search Room and load it into var result
            Room result = showRoom(room.getId());
            //update Room
            updateRoom(result, result.getBuilding(), result.isProjectorPresent(), result.getSeats(), 2);
            //Test print, if everything is working correctly (should print Buildingname: Haus A | RoomNumber: 2)
            System.out.println("Buildingname: " + result.getBuilding() + " | RoomNumber: " + result.getRoomNumber());
            //delete Room
            deleteRoom(showRoom(room.getId()));

        } catch (RoomAlreadyExistsException | RoomNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Required in order to make the thread exit normally
            HibernateUtil.getEntityManagerFactory().close();
        }
    }

    public static void deleteRoom(Room room) throws RoomNotFoundException {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Room roomToDelete = entityManager.find(Room.class, room.getId());

        if (roomToDelete == null) {
            entityManager.getTransaction().rollback();
            throw new RoomNotFoundException();
        }
        entityManager.remove(roomToDelete);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static void updateRoom(Room room, String building, boolean projectorPresent, Integer seats, Integer roomNumber) throws RoomNotFoundException {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        room.setBuilding(building);
        room.setProjectorPresent(projectorPresent);
        room.setSeats(seats);
        room.setRoomNumber(roomNumber);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static void persistRoom(Room room) throws RoomAlreadyExistsException {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(room);
            entityManager.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            entityManager.getTransaction().rollback();
            throw new RoomAlreadyExistsException();
        }
        entityManager.close();
    }

    public static Room showRoom(Long id) {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Room result = entityManager.find(Room.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

}
