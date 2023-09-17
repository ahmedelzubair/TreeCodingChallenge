package sa.com.tree.account.statment.treecodingchallenge.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.com.tree.account.statment.treecodingchallenge.dto.LoginDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.RegistrationDTO;
import sa.com.tree.account.statment.treecodingchallenge.service.UserService;
import sa.com.tree.account.statment.treecodingchallenge.utils.ApiResponse;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody RegistrationDTO request) {
        ApiResponse apiResponse = ApiResponse.builder().status(200)
                .timestamp(LocalDateTime.now())
                .message("Successfully added new user")
                .data(userService.addNewUser(request))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        ApiResponse apiResponse = ApiResponse.builder().status(200)
                .timestamp(LocalDateTime.now())
                .message("Successfully logged in")
                .data(userService.login(loginDTO, session))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
