package com.hcmus.gameservice.client;

import com.hcmus.gameservice.quizz.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8005/v3/users")
public interface UserClient {
    @PostMapping("/list")
    List<UserDto> getUsersByListId(List<Long> listId);

    @PutMapping("/{id}/decrease-turn")
    ResponseEntity<?> decreaseTurnNum(@PathVariable("id") Long id);

    @PutMapping("/decrease-turns")
    ResponseEntity<?> decreaseTurnNumForUsers(@RequestBody List<Long> ids);

    @GetMapping("/email/{email}")
    ResponseEntity<?> getUserByEmail(@PathVariable("email") String email);
}
