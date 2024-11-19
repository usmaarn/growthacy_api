package africa.techverse.growthacy.v1.services;

import africa.techverse.growthacy.v1.dtos.CreateUserDto;
import africa.techverse.growthacy.v1.dtos.LoginDto;
import africa.techverse.growthacy.v1.dtos.ResponseDto;
import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.enums.UserStatus;
import africa.techverse.growthacy.v1.exceptions.HttpException;
import africa.techverse.growthacy.v1.repositories.UserRepository;
import africa.techverse.growthacy.v1.utils.Encoder;
import africa.techverse.growthacy.v1.utils.JsonWebToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final Encoder encoder;
    private final UserRepository userRepository;
    private final JsonWebToken jsonWebToken;
    private final EmailService emailService;

    public AuthenticationService(
            africa.techverse.growthacy.v1.services.UserService userService,
            Encoder encoder,
            UserRepository userRepository,
            JsonWebToken jsonWebToken,
            africa.techverse.growthacy.v1.services.EmailService emailService
    ) {
        this.userService = userService;
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.jsonWebToken = jsonWebToken;
        this.emailService = emailService;
    }

    public ResponseDto register(CreateUserDto userDto){
        var userEntity = User
                .builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .type(userDto.getType())
                .status(UserStatus.ACTIVE.getValue())
                .password(encoder.encode(userDto.getPassword()))
                .build();
        User user = userRepository.save(userEntity);
        String token = jsonWebToken.generateToken(user.getEmail());
        try {
            emailService.sendEmail(userDto.getEmail(), "Registration Successful", "Your registration was successful");
            return ResponseDto.builder().data(Map.of("token", token)).build();
        }catch (Exception e){
            throw new HttpException(500, e.getMessage());
        }
    }

    public ResponseDto login(LoginDto loginDto){
        var userEntity = userService.getUserByEmail(loginDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        if (!encoder.matches(loginDto.getPassword(), userEntity.getPassword())){
            throw new IllegalArgumentException("Incorrect password");
        }
        String token = jsonWebToken.generateToken(userEntity.getEmail());
        return ResponseDto.builder().data(Map.of("token", token)).build();
    }
}
