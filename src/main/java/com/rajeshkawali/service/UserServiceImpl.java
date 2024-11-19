package com.rajeshkawali.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.rajeshkawali.dto.UserDto;
import com.rajeshkawali.entity.User;
import com.rajeshkawali.exception.InvalidUserDataException;
import com.rajeshkawali.exception.UserNotFoundException;
import com.rajeshkawali.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rajesh_Kawali
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Use of DozerMapper for object transformation between User and UserDto
    Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    /**
     * Method to fetch all users from the database.
     * It maps the User entities to UserDto and returns a list of UserDto objects.
     *
     * @return List<UserDto> - List of all users
     */
    @Override
    public List<UserDto> getAllUsers() {
        try {
            List<UserDto> usersList = userRepository.findAll().stream()
                    .map(user -> new UserDto(
                            user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getAge(),
                            user.getGender(),
                            user.getRole()
                    ))
                    .collect(Collectors.toList());

            return usersList;
        } catch (Exception e) {
            log.error("Error fetching all users", e);
            throw new RuntimeException("Unable to fetch users");
        }
    }

    /**
     * Method to add a new user to the database.
     * It maps the input UserDto to a User entity, saves it, and returns the saved UserDto.
     *
     * @param userDto - The user data to be added
     * @return UserDto - The newly added user as a DTO
     * @CacheEvict - Evicts all entries from the "users" cache after adding a new user
     */
    @Override
    @CacheEvict(cacheNames = "users", allEntries = true)
    public UserDto addUser(UserDto userDto) {
        try {
            // Mapping DTO to Entity
            User user = mapper.map(userDto, User.class);
            // Saving the new user to DB
            User addedUser = userRepository.save(user);
            // Returning the saved user as a DTO
            return mapper.map(addedUser, UserDto.class);
        } catch (Exception e) {
            log.error("Error adding user", e);
            throw new InvalidUserDataException("Failed to add user: " + e.getMessage());
        }
    }

    /**
     * Method to fetch a user by ID from the database.
     * The result is cached in Redis to avoid repeated database queries.
     *
     * @param id - The ID of the user to fetch
     * @return UserDto - The user details as a DTO
     * @Cacheable - Caches the response with the "user" cache and "id" as key
     */
    @Override
    @Cacheable(value = "user", key = "#id")
    public UserDto getUserById(String id) {
        try {
            // Fetching user from the DB
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
            // Returning the user as a DTO
            return mapper.map(user, UserDto.class);
        } catch (UserNotFoundException e) {
            log.error("Error fetching user with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            log.error("Error fetching user", e);
            throw new RuntimeException("Unable to fetch user");
        }
    }

    /**
     * Method to update an existing user in the database.
     * First, fetches the user by ID, then updates its fields with the new data and saves it.
     *
     * @param id      - The ID of the user to update
     * @param userDto - The user data to update
     * @return UserDto - The updated user as a DTO
     * @CachePut - Updates the cache for "users" with the new data for this user
     */
    @Override
    //@CachePut(value = "users", key = "#user.id")
    //@CacheEvict(cacheNames = "users", key = "#user.id")
    @CacheEvict(cacheNames = "users", allEntries = true)
    public UserDto updateUser(String id, UserDto userDto) {
        try {
            // Fetch the existing user from the database
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

            // Map the updated fields from the DTO
            existingUser.setFirstName(userDto.getFirstName());
            existingUser.setLastName(userDto.getLastName());
            existingUser.setAge(userDto.getAge());
            existingUser.setGender(userDto.getGender());
            existingUser.setRole(userDto.getRole());

            // Save the updated user back to the database and return it as a DTO
            return mapper.map(userRepository.save(existingUser), UserDto.class);
        } catch (UserNotFoundException e) {
            log.error("Error updating user with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            log.error("Error updating user", e);
            throw new RuntimeException("Unable to update user");
        }
    }

    /**
     * Method to delete a user by ID.
     *
     * @param id - The ID of the user to delete
     * @CacheEvict - Evicts the cache entry for the user with the given ID
     */
    @Override
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(String id) {
        try {
            // Deleting the user from the database
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting user with ID: {}", id, e);
            throw new RuntimeException("Unable to delete user");
        }
    }

    /**
     * Method to delete all users from the database.
     *
     * @CacheEvict - Evicts all entries from the "users" cache after deleting all users
     */
    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void deleteAllUsers() {
        try {
            // Deleting all users from the database
            userRepository.deleteAll();
        } catch (Exception e) {
            log.error("Error deleting all users", e);
            throw new RuntimeException("Unable to delete all users");
        }
    }

    /**
     * Method to either save a new user or update an existing one.
     *
     * @param userDto - The user object to be saved or updated
     * @return User - The saved or updated user
     * @Caching - Combines CachePut and CacheEvict annotations.
     * Updates the cache for the "users" and "userByFirstName" cache entries,
     * and also evicts the old user cache with the given user ID.
     */
    @Caching(
            put = {
                    @CachePut(value = "users", key = "#user.id"),
                    @CachePut(value = "userByFirstName", key = "#user.firstName")
            },
            evict = {
                    @CacheEvict(value = "users", key = "#user.id")
            }
    )
    @Override
    public UserDto saveOrUpdateUser(UserDto userDto) {
        try {
            // Saving or updating the user
            User user = mapper.map(userDto, User.class);
            return mapper.map(userRepository.save(user), UserDto.class);
        } catch (Exception e) {
            log.error("Error saving or updating user", e);
            throw new InvalidUserDataException("Unable to save or update user");
        }
    }
}