package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Token;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenDAO {
    Optional<Token> findById(long id);
    Token updateConfirmedAt(long id, LocalDateTime confirmedAt) throws ServiceException;
}
