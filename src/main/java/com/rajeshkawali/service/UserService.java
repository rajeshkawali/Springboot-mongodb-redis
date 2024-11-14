package com.rajeshkawali.service;

import com.rajeshkawali.dto.UserDto;

import java.util.List;

/**
 * @author Rajesh_Kawali
 *
 */
public interface UserService {
    public List<UserDto> getAllUsers();

    public UserDto addUser(UserDto userDto);
}
