package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> findAll() throws ServiceException;
    User save(User user);
    Boolean register(String name, String surname, String password);
    Optional<User> findById(long id);
    Optional<User> findByNameAndSurname(String name, String surname);
    User update(long id, User user) throws ServiceException;
    void delete(long id);
}
