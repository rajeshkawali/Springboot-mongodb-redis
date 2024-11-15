package com.rajeshkawali.service;

import com.rajeshkawali.dto.UserDto;

import java.util.List;

/**
 * @author Rajesh_Kawali
 */
public interface UserService {

    public List<UserDto> getAllUsers();

    public UserDto addUser(UserDto userDto);

    public UserDto getUserById(String id);

    public UserDto updateUser(String id, UserDto userDto);

    public void deleteUser(String id);

    public void deleteAllUsers();

    public UserDto saveOrUpdateUser(UserDto user);
}
