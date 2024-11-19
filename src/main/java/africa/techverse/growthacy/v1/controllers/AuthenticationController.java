package africa.techverse.growthacy.v1.controllers;


import africa.techverse.growthacy.v1.dtos.CreateUserDto;
import africa.techverse.growthacy.v1.dtos.LoginDto;
import africa.techverse.growthacy.v1.dtos.ResponseDto;
import africa.techverse.growthacy.v1.dtos.UserDto;
import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.mappers.UserMapper;
import africa.techverse.growthacy.v1.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseDto register(@Valid @RequestBody CreateUserDto userDto) {
        return this.authenticationService.register(userDto);
    }

    @PostMapping("/login")
    public ResponseDto login(@Valid @RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

    @GetMapping("/user")
    public UserDto getUser(@RequestAttribute("user") User user) {
        return UserMapper.toDto(user);
    }
}
