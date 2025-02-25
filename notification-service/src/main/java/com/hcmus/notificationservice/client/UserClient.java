package com.hcmus.notificationservice.client;
import com.hcmus.notificationservice.config.FeignClientConfig;
import com.hcmus.notificationservice.dto.UserRespondDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service", url = "http://user-service:8005/v3/users", configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("/admins")
    List<UserRespondDto> getAdmins();
}
