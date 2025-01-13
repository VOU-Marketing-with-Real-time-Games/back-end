package com.hcmus.user_service.service;
import com.hcmus.user_service.dto.AuthRequest;
import com.hcmus.user_service.dto.UserRequestDto;
import com.hcmus.user_service.dto.UserRespondDto;
import com.hcmus.user_service.dto.UserUpdateDto;
import com.hcmus.user_service.exception.*;
import com.hcmus.user_service.model.User;
import com.hcmus.user_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserRespondDto> findAll() {
        return userRepository.findAll().stream().map(
                userRespondDto -> modelMapper.map(userRespondDto, UserRespondDto.class)).toList();
    }

    public UserRespondDto findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).map(
                userRespondDto -> modelMapper.map(userRespondDto, UserRespondDto.class))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserRespondDto update(Long id, UserUpdateDto userDto) throws UserNotFoundException {
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public UserRespondDto findByEmail(String email) {
        User user =  userRepository.findByEmail(email);
        return modelMapper.map(user, UserRespondDto.class);
    }
    public UserRespondDto findByUsername(String username) {
        User user =  userRepository.findByUsername(username);
        return modelMapper.map(user, UserRespondDto.class);
    }

    public List<UserRespondDto> findByListId(List<Long> listId) {
        List<User> users = userRepository.findByIds(listId);
        return users.stream().map(user -> modelMapper.map(user, UserRespondDto.class)).toList();
    }

    public void resetPlayerPlayTurn() {
        userRepository.resetPlayerPlayTurn();
    }
    public UserRespondDto validateUser(AuthRequest authRequest) throws Exception {
        User user = userRepository.findByUsername(authRequest.getUsername());
        if(user==null)
            throw new UserNotFoundException("User not found");
        if(passwordEncoder.matches(authRequest.getPassword(),user.getPassword()))
            return modelMapper.map(user, UserRespondDto.class);
        else
            throw new ValidationUserException("Invalid password");
    }
}
