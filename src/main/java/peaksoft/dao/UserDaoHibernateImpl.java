package peaksoft.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;
import peaksoft.model.User;
import peaksoft.util.Util;
import java.util.List;

@NoArgsConstructor
public class UserDaoHibernateImpl implements UserDao, AutoCloseable {

    private static final EntityManagerFactory entityManagerFactory = Util.createEntityManagerFactory();

    @Override
    public void createUsersTable() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void dropUsersTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("drop table if exists USER", User.class).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = new User(name, lastName, age);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void removeUserById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class,id);
        entityManager.remove(user);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public List<User> getAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<User> users = entityManager.createQuery("select u from User u ", User.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("truncate table users", User.class).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public boolean existsByFirstName(String firstName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Boolean singleResult = entityManager.createQuery("select case when count(u)>0 then true else false end from User u where u.name like ?1", Boolean.class).setParameter(1, firstName).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return singleResult;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
