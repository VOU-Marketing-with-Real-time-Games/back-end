package com.vou.backend.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vou.backend.user.dto.PlayTurnRequestDto;
import com.vou.backend.user.dto.UserRequestDto;
import com.vou.backend.user.dto.UserRespondDto;
import com.vou.backend.user.exception.InvalidRedeemException;
import com.vou.backend.user.exception.PhoneNumberExistedException;
import com.vou.backend.user.exception.UserEmailExistedException;
import com.vou.backend.user.exception.UserNameExistedException;
import com.vou.backend.user.exception.UserNotFoundException;
import com.vou.backend.user.model.User;
import com.vou.backend.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Get all users.
     *
     * @return a list of UserRespondDto
     */
    @GetMapping()
    public ResponseEntity<?> getAllUser() {
        List<UserRespondDto> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Create a new user.
     *
     * @param userDto the UserRequestDto containing the details of the user
     * @return the created UserRespondDto
     */
    @PostMapping("register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto userDto)
            throws UserNameExistedException, UserEmailExistedException, PhoneNumberExistedException {
        UserRespondDto user = userService.create(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Get a user by its ID.
     *
     * @param id the ID of the user
     * @return the UserRespondDto
     * @throws UserNotFoundException if the user is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable("id") Long id) throws UserNotFoundException {
        UserRespondDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Update a user.
     *
     * @param id      the ID of the user
     * @param userDto the UserRequestDto containing the details of the user
     * @return the updated UserRespondDto
     * @throws UserNotFoundException if the user is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserRequestDto userDto)
            throws UserNotFoundException {
        UserRespondDto user = userService.update(id, userDto);
        return ResponseEntity.ok(user);
    }

    /**
     * Delete a user.
     *
     * @param id the ID of the user
     * @return the deleted UserRespondDto
     * @throws UserNotFoundException if the user is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
        UserRespondDto user = userService.delete(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Create a new user by admin.
     *
     * @param userDto the UserRequestDto containing the details of the user
     * @return the created UserRespondDto
     */
    @PostMapping("create")

    public ResponseEntity<?> createUserByAdmin(@Valid @RequestBody UserRequestDto userDto)
            throws UserNameExistedException, UserEmailExistedException, PhoneNumberExistedException {
        UserRespondDto user = userService.createByAdmin(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/increase-play-turn")
    public ResponseEntity<?> increasePlayTurn(@Valid @RequestBody PlayTurnRequestDto playTurnRequestDto)
            throws InvalidRedeemException, UserNotFoundException {
        UserRespondDto newPlayCount = userService.increasePlayCount(playTurnRequestDto);
        return ResponseEntity.ok(newPlayCount);
    }
}
