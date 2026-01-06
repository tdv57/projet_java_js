package com.ensta.myfilmlist.dao;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.*;

import java.util.*;

public interface DirectorDAO {
    List<Director> findAll();
    Optional<Director> findByNameAndSurname(String name, String surname);
    Optional<Director> findById(long id);
    Director update(long id, Director director) throws ServiceException;
    Director save(Director director);
    void delete(long id);
}