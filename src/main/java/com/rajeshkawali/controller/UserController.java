package com.rajeshkawali.controller;

import com.rajeshkawali.dto.UserDto;
import com.rajeshkawali.service.ExternalServiceImpl;
import com.rajeshkawali.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rajesh_Kawali
 */
@Slf4j
@RequestMapping("/api")
@RestController
public class UserController {

    public static final String CLASS_NAME = UserController.class.getName();
    String methodName = "";

    @Autowired
    private UserService userService;

    private ExternalServiceImpl externalService;

    @GetMapping("/v1/getAll")
    public List<UserDto> getAllUsers() {
        methodName = ".getAllUsers";
        log.info(CLASS_NAME + methodName + "::ENTER");
        List<UserDto> usersList = new ArrayList<>();
        usersList = userService.getAllUsers();
        log.info(CLASS_NAME + methodName + "::EXIT");
        return usersList;
    }

    @PostMapping("/v1/add")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        methodName = ".addUser";
        log.info(CLASS_NAME + methodName + "::ENTER");
        log.debug(CLASS_NAME + methodName + "::Request to add a new User into the DB: {} ", userDto);
        UserDto addedUser = userService.addUser(userDto);
        log.info(CLASS_NAME + methodName + "::User SuccessFully added to the DB: {}", addedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
    }

}