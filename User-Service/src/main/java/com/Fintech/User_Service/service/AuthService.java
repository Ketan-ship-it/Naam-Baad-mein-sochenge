package com.Fintech.User_Service.service;

import com.Fintech.User_Service.dto.LoginResponseDTO;
import com.Fintech.User_Service.dto.UserDTO;
import com.Fintech.User_Service.dto.UserLogInDTO;
import com.Fintech.User_Service.dto.UserSignUpDTO;
import com.Fintech.User_Service.enitities.User;
import com.Fintech.User_Service.exceptions.BadRequestException;
import com.Fintech.User_Service.exceptions.ResourceNotFoundException;
import com.Fintech.User_Service.repository.UserRepository;
import com.Fintech.User_Service.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO signUp(UserSignUpDTO signupRequestDto) {
        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists) {
            throw new BadRequestException("User already exists, cannot signup again.");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public LoginResponseDTO login(UserLogInDTO loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: "+loginRequestDto.getEmail()));

        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDto.getPassword(), user.getPassword());

        if(!isPasswordMatch) {
            throw new BadRequestException("Incorrect password");
        }

        String access = jwtService.generateAccessToken(user);
        String refresh = jwtService.generateRefreshToken(user);

        LoginResponseDTO loginResponseDTO = LoginResponseDTO
                .builder()
                .accessToken(access)
                .refreshToken(refresh)
                .build();
        return loginResponseDTO;
    }
}
