package com.vou.backend.user.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vou.backend.user.dto.UserRequestDto;
import com.vou.backend.user.dto.UserRespondDto;
import com.vou.backend.user.exception.PhoneNumberExistedException;
import com.vou.backend.user.exception.UserEmailExistedException;
import com.vou.backend.user.exception.UserNameExistedException;
import com.vou.backend.user.exception.UserNotFoundException;
import com.vou.backend.user.model.User;
import com.vou.backend.user.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<UserRespondDto> findAll() {
        return userRepository.findAll().stream().map(
                userRespondDto -> modelMapper.map(userRespondDto, UserRespondDto.class)).toList();
    }

    public UserRespondDto findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).map(
                userRespondDto -> modelMapper.map(userRespondDto, UserRespondDto.class))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserRespondDto update(Long id, UserRequestDto userDto) throws UserNotFoundException {
        User user = modelMapper.map(userDto, User.class);
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        oldUser.copy(user);
        userRepository.save(oldUser);
        return modelMapper.map(oldUser, UserRespondDto.class);
    }

    public UserRespondDto create(UserRequestDto userDto) throws UserNameExistedException, UserEmailExistedException,
            PhoneNumberExistedException {
        User user = modelMapper.map(userDto, User.class);
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserNameExistedException("Username existed");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserEmailExistedException("Email existed");
        }
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()) != null) {
            throw new PhoneNumberExistedException("Phone number existed");
        }
        user.setTurnNum(0);
        user.setCreatedAt(new Date());
        userRepository.save(user);
        return modelMapper.map(user, UserRespondDto.class);
    }

    public UserRespondDto delete(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
        return modelMapper.map(user, UserRespondDto.class);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findByListId(List<Long> listId) {
        return userRepository.findByIds(listId);
    }
}
