package com.rajeshkawali.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.rajeshkawali.dto.UserDto;
import com.rajeshkawali.entity.User;
import com.rajeshkawali.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return List.of();
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        User user = mapper.map(userDto, User.class);
        userRepository.save(user);
        return null;
    }
}
