package com.cungthinh.authservices.controller.internal;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cungthinh.authservices.dto.response.ApiResponse;
import com.cungthinh.authservices.service.UserService;

@RestController
@RequestMapping("api/v1/internal/users")
public class UserInternalController {

    private final UserService userService;

    public UserInternalController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-all-ids")
    public ResponseEntity<Object> getAllUserId() {
        List<String> result = userService.getAllUserId();
        ApiResponse<Object> response = ApiResponse.success(result, "Lấy danh sách id của user thành công");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
