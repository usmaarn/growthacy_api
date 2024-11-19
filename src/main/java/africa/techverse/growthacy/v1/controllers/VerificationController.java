package africa.techverse.growthacy.v1.controllers;

import africa.techverse.growthacy.v1.entities.Otp;
import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.services.VerificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/verification")
public class VerificationController {
    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping("/email/send-code")
    public ResponseEntity<Map<String, String>> sendVerificationEmail(@RequestAttribute("user") User user) {
        try {
            Otp otp = verificationService.sendEmailVerificationCode(user);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message", "Email verification Sent",
                    "code", otp.getCode().toString(),
                        "expiresAt", otp.getExpiresAt().toString()
            ));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("message", "Error Sending mail", "error", e.getMessage())
            );
        }
    }

    public record CodeRequest(@NotBlank String code) {}

    @PostMapping("/email/verify")
    public ResponseEntity<Map<String, String>> verifyEmail(
            @RequestAttribute("user") User user,
            @Valid @RequestBody CodeRequest request
    ) {
        if (this.verificationService.verifyCode(user, Integer.parseInt(request.code))) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Email Verified Successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("message", "Invalid Email Code or Code Expired")
        );
    }
}
