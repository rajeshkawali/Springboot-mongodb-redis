package com.rajeshkawali.controller;

import com.rajeshkawali.dto.UserDto;
import com.rajeshkawali.exception.InvalidUserDataException;
import com.rajeshkawali.exception.UserNotFoundException;
import com.rajeshkawali.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rajesh_Kawali
 */
@Slf4j
@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get all users
     *
     * @return ResponseEntity<List<UserDto>> - A list of all users
     */
    @GetMapping("/v1/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        try {
            List<UserDto> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all users", e);
            throw new RuntimeException("Unable to fetch users");
        }
    }

    /**
     * Get user by ID
     *
     * @param id - The ID of the user to fetch
     * @return ResponseEntity<UserDto> - The user details
     * @throws Exception if user is not found
     */
    @GetMapping("/v1/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        try {
            UserDto user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching user with ID: {}", id, e);
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    /**
     * Add a new user
     *
     * @param userDto - The user data to add
     * @return ResponseEntity<UserDto> - The added user data
     */
    @PostMapping("/v1/users/add")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        try {
            if (userDto.getFirstName() == null || userDto.getLastName() == null) {
                throw new InvalidUserDataException("User first name and last name are required");
            }
            UserDto addedUser = userService.addUser(userDto);
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
        } catch (InvalidUserDataException e) {
            log.error("Invalid user data", e);
            throw e;
        } catch (Exception e) {
            log.error("Error adding user", e);
            throw new RuntimeException("Unable to add user");
        }
    }

    /**
     * Update an existing user
     *
     * @param id - The ID of the user to update
     * @param userDto - The updated user data
     * @return ResponseEntity<UserDto> - The updated user data
     * @throws Exception if user is not found
     */
    @PutMapping("/v1/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("Error updating user with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            log.error("Error updating user", e);
            throw new RuntimeException("Unable to update user");
        }
    }

    /**
     * Delete user by ID
     *
     * @param id - The ID of the user to delete
     * @return ResponseEntity<Void> - No content response
     */
    @DeleteMapping("/v1/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            log.error("Error deleting user with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            log.error("Error deleting user", e);
            throw new RuntimeException("Unable to delete user");
        }
    }

    /**
     * Delete all users
     *
     * @return ResponseEntity<Void> - No content response
     */
    @DeleteMapping("/v1/users/all")
    public ResponseEntity<Void> deleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting all users", e);
            throw new RuntimeException("Unable to delete all users");
        }
    }

    /**
     * Save or Update a user.
     * If a user with the given ID exists, it updates the user; otherwise, it creates a new user.
     *
     * @param userDto - The user data to save or update
     * @return ResponseEntity<UserDto> - The saved or updated user data
     */
    @PostMapping("/v1/users/saveOrUpdate")
    public ResponseEntity<UserDto> saveOrUpdateUser(@RequestBody UserDto userDto) {
        try {
            UserDto savedUser = userService.saveOrUpdateUser(userDto);
            return new ResponseEntity<>(savedUser, HttpStatus.OK); // Return OK status with the saved user
        } catch (InvalidUserDataException e) {
            log.error("Error saving or updating user: ", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Return 400 Bad Request if data is invalid
        } catch (Exception e) {
            log.error("Unexpected error occurred: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 Internal Server Error for general issues
        }
    }
}