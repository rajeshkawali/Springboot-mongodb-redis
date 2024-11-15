package com.rajeshkawali.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.rajeshkawali.dto.UserDto;
import com.rajeshkawali.entity.User;
import com.rajeshkawali.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rajesh_Kawali
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {

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
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        User user = mapper.map(userDto, User.class);
        User addedUser = userRepository.save(user);
        return mapper.map(addedUser, UserDto.class);
    }
}
