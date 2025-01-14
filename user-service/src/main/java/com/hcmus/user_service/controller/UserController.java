package com.hcmus.user_service.controller;

import com.hcmus.user_service.dto.AuthRequest;
import com.hcmus.user_service.dto.UserRequestDto;
import com.hcmus.user_service.dto.UserRespondDto;
import com.hcmus.user_service.dto.UserUpdateDto;
import com.hcmus.user_service.exception.PhoneNumberExistedException;
import com.hcmus.user_service.exception.UserEmailExistedException;
import com.hcmus.user_service.exception.UserNameExistedException;
import com.hcmus.user_service.exception.UserNotFoundException;
import com.hcmus.user_service.model.User;
import com.hcmus.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v3/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllUser() {
        List<UserRespondDto> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto userDto) throws UserNameExistedException, UserEmailExistedException, PhoneNumberExistedException {
        UserRespondDto user = userService.create(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable("id") Long id) throws UserNotFoundException {
        UserRespondDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDto userDto)
            throws UserNotFoundException {
        UserRespondDto user = userService.update(id, userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
        UserRespondDto user = userService.delete(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        UserRespondDto user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username){
        UserRespondDto user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/list")
    public List<UserRespondDto> getUsersByListId(@RequestBody List<Long> listId) {
        return userService.findByListId(listId);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateUser(@RequestBody AuthRequest authRequest) throws Exception {
        return ResponseEntity.ok(userService.validateUser(authRequest));
    }

    @GetMapping("/{id}/has-turns")
    public ResponseEntity<?> hasTurnsLeft(@PathVariable("id") Long id) throws UserNotFoundException {
        boolean hasTurns = userService.hasTurnsLeft(id);
        return ResponseEntity.ok(hasTurns);
    }

    @PutMapping("/{id}/decrease-turn")
    public ResponseEntity<?> decreaseTurnNum(@PathVariable("id") Long id) throws UserNotFoundException {
        boolean success = userService.decreaseTurnNum(id);
        return ResponseEntity.ok(success);
    }

    @PutMapping("/decrease-turns")
    public ResponseEntity<?> decreaseTurnNumForUsers(@RequestBody List<Long> ids) {
        userService.decreaseTurnNumForUsers(ids);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/increase-play-turn")
    public ResponseEntity<?> increaseTurnNum(@PathVariable("id") Long id) throws UserNotFoundException {
        boolean success = userService.increaseTurnNum(id);
        return ResponseEntity.ok(success);
    }
}
