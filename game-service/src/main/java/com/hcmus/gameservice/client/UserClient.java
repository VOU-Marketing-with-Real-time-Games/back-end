package com.hcmus.gameservice.client;

import com.hcmus.gameservice.quizz.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8005/v3/users")
public interface UserClient {
    @PostMapping("/list")
    List<UserDto> getUsersByListId(List<Long> listId);
}
