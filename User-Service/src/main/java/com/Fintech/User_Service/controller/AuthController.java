package com.Fintech.User_Service.controller;

import com.Fintech.User_Service.advice.ApiResponse;
import com.Fintech.User_Service.dto.LoginResponseDTO;
import com.Fintech.User_Service.dto.UserDTO;
import com.Fintech.User_Service.dto.UserLogInDTO;
import com.Fintech.User_Service.dto.UserSignUpDTO;
import com.Fintech.User_Service.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${deploy.env}")
    private String deployEnv;

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserSignUpDTO signupRequestDto){
        UserDTO userDto = authService.signUp(signupRequestDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> logIn(@RequestBody UserLogInDTO loginRequestDto , HttpServletRequest request , HttpServletResponse response){
        LoginResponseDTO logInResponseDTO = authService.login(loginRequestDto);

        Cookie cookie = new Cookie("refreshToken", logInResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new ApiResponse<>(logInResponseDTO));
    }


}
