package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.UserDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class JpaUserDAO implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * Returns the list of all Users present in the database.
     * A ServiceException is thrown in case of an error (can't get Users, list empty)
     *
     * @return      the list of Users
     */
    @Override
    public List<User> findAll() throws ServiceException {
        List<User> users = entityManager
                .createQuery("SELECT u FROM User u", User.class)
                .getResultList();
        if (users.isEmpty()) {
            throw new ServiceException("Can't find Users.");
        }
        return users;
    }

    /**
     * Creates a User in the database based on a user argument
     *
     * @param  user     the user to register
     * @return          the user created
     */
    @Override
    public User save(User user){
        entityManager.persist(user);
        return user;
    }

    @Override
    public Boolean register(String name, String surname, String password) {
        return true;
    }

    /**
     * Returns the User corresponding to the id argument (or an empty option if there is none)
     *
     * @param  id   the id of the user to return
     * @return      the corresponding user
     */
    @Override
    public Optional<User> findById(long id){
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    /**
     * Returns the User corresponding to the name and surname arguments (or an empty option if there is none)
     *
     * @param name      the name of the user to return
     * @param surname   the surname of the user to return
     * @return          the corresponding user
     */
    @Override
    public Optional<User> findByNameAndSurname(String name, String surname){
        List<User> users = entityManager
                .createQuery("SELECT u FROM User u WHERE u.name = :name AND u.surname = :surname", User.class)
                .setParameter("name", name)
                .setParameter("surname", surname)
                .getResultList();
        return Optional.ofNullable(users.get(0));
    }

    /**
     * Updates the User corresponding to the id argument with the user argument
     *
     * @param  id   the id of the user to update
     * @param user  the state of the user updated
     * @return      the corresponding user updated
     */
    @Override
    public User update(long id, User user)  throws ServiceException {
        Optional<User> prev_user = this.findById(id);
        if  (prev_user.isEmpty()) {
            throw new ServiceException("Can't update User.");
        }
        User user_to_modify = entityManager.merge(prev_user.get());
        user_to_modify.setName(user.getName());
        user_to_modify.setSurname(user.getSurname());
        user_to_modify.setPassword(user.getPassword());
        entityManager.merge(user_to_modify);
        return user_to_modify;
    }

    /**
     * Deletes the User corresponding to the user argument
     *
     * @param id    the id of the user to delete
     */
    @Override
    public void delete(long id){
        User managedUser = entityManager.find(User.class, id);
        if (managedUser != null) {
            entityManager.remove(managedUser);
        }
    }
}
