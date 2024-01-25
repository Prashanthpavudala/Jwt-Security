package com.leaning.springboot3security.springboot3security.service;

import com.leaning.springboot3security.springboot3security.dto.UserDTO;

public interface UserService {

    String addUser(UserDTO userDTO);
    String updateUser(UserDTO userDTO);
    String deleteUser(String email);
    Boolean isUserExist(String email);

}
