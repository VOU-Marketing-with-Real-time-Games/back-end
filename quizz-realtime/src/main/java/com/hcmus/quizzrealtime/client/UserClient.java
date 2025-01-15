package com.hcmus.quizzrealtime.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8005/v3/users")
public interface UserClient {
    @PutMapping("/decrease-turns")
    ResponseEntity<?> decreaseTurnNumForUsers(@RequestBody List<Long> ids);
}
