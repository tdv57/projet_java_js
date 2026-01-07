package com.ensta.myfilmlist.dao;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.*;

import java.util.*;

public interface DirectorDAO {
    List<Director> findAll();
    Optional<Director> findBySurnameAndName(String surname, String name);
    Optional<Director> findById(long id);
    Director update(long id, Director director) throws ServiceException;
    Director save(Director director) throws ServiceException;
    void delete(long id);
}