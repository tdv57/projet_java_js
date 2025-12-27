package com.ensta.myfilmlist.mapper;

import com.ensta.myfilmlist.dto.UserDTO;
import com.ensta.myfilmlist.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User convertUserDTOToUser(UserDTO userDTO) {
        if (userDTO == null) return null;
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        return user;
    }

    public static UserDTO convertUserToUserDTO(User user) {
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        return userDTO;
    }
}


