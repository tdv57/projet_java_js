package com.ensta.myfilmlist.dao;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorDAO {
    List<Director> findAll();
    Optional<Director> findByNameAndSurname(String name, String surname);
    Optional<Director> findById(long id);
    Director update(long id, Director director) throws ServiceException;
    Director save(Director director) throws ServiceException;
    void delete(long id) throws ServiceException;
}