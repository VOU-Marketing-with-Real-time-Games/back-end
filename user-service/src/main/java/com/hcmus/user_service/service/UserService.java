package com.hcmus.user_service.service;
import com.hcmus.user_service.dto.*;
import com.hcmus.user_service.exception.*;
import com.hcmus.user_service.model.User;
import com.hcmus.user_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        user.setStatus("INACTIVE");
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
    // UserService.java
    public boolean hasTurnsLeft(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.getTurnNum() > 0;
    }

    // UserService.java
    public boolean decreaseTurnNum(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getTurnNum() > 0) {
            user.setTurnNum(user.getTurnNum() - 1);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // UserService.java
    public void decreaseTurnNumForUsers(List<Long> ids) {
        List<User> users = userRepository.findByIds(ids);
        for (User user : users) {
            if (user.getTurnNum() > 0) {
                user.setTurnNum(user.getTurnNum() - 1);
            }
        }
        userRepository.saveAll(users);
    }
    // UserService.java
    public boolean increaseTurnNum(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setTurnNum(user.getTurnNum() + 1);
        userRepository.save(user);
        return true;
    }

    public UserStatisticsDto getUserStatistics() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Integer> dailyUserCounts = new ArrayList<>();
        int totalUsers = 0;
        int previousDayCount = 0;
        boolean isTrendUp = false;

        for (int i = 0; i < 30; i++) {
            LocalDate date = startDate.plusDays(i);
            String formattedDate = date.format(formatter);
            int userCount = userRepository.countUsersByDate(formattedDate);
            dailyUserCounts.add(userCount);
            totalUsers += userCount;

            if (i > 0 && userCount > previousDayCount) {
                isTrendUp = true;
            }
            previousDayCount = userCount;
        }

        UserStatisticsDto stats = new UserStatisticsDto();
        stats.setTitle("Users");
        stats.setValue(String.valueOf(totalUsers));
        stats.setInterval("Last 30 days");
        stats.setTrend(isTrendUp ? "up" : "down");
        stats.setData(dailyUserCounts);

        return stats;
    }

    public UserRespondDto createByAdmin(UserRequestDto userDto)
            throws UserNameExistedException, UserEmailExistedException,
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
        user.setStatus("ACTIVE");
        userRepository.save(user);
        return modelMapper.map(user, UserRespondDto.class);
    }

    public List<UserDailyCountDto> getUserDailyCounts() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<UserDailyCountDto> dailyCounts = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            LocalDate date = startDate.plusDays(i);
            String formattedDate = date.format(formatter);
            int userCount = userRepository.countUsersByDate(formattedDate);
            dailyCounts.add(new UserDailyCountDto(userCount, formattedDate));
        }

        return dailyCounts;
    }
    public List<UserRespondDto> getAdmins()
    {
        List<User> users = userRepository.findByRole("ADMIN");
        return users.stream().map(user -> modelMapper.map(user, UserRespondDto.class)).toList();
    }
}
