package com.ensta.myfilmlist.mapper;

import com.ensta.myfilmlist.dto.UserDTO;
import com.ensta.myfilmlist.form.UserForm;
import com.ensta.myfilmlist.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private static BCryptPasswordEncoder passwordEncoder;

    public UserMapper() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Convert a list of users into a list of users' DTO.
     *
     * @param users     list of directors to be converted
     * @return          list of DirectorDTO created from the parameter
     */
    public static List<UserDTO> convertUsersToUserDTOs(List<User> users) {
        return users.stream()
                .map(UserMapper::convertUserToUserDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert a user's DTO into a user.
     *
     * @param userDTO   user's DTO to be converted
     * @return          user created from the parameter
     */
    public static User convertUserDTOToUser(UserDTO userDTO) {
        if (userDTO == null) return null;
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());
        return user;
    }

    /**
     * Convert a user into a user's DTO.
     *
     * @param user      user to be converted
     * @return          user's DTO created from the parameter
     */
    public static UserDTO convertUserToUserDTO(User user) {
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

    /**
     * Convert a user's form into a user.
     *
     * @param userForm   user's form to be converted
     * @return          user created from the parameter
     */
    public static User convertUserFormToUser(UserForm userForm) {
        User user = new User();
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setPassword(passwordEncoder.encode(userForm.getName()));
        user.setRoles("USER");
        return user;
    }
}


