package africa.techverse.growthacy.v1.services;


import africa.techverse.growthacy.v1.entities.Otp;
import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Service
public class VerificationService {
    private final africa.techverse.growthacy.v1.services.EmailService emailService;
    private final africa.techverse.growthacy.v1.services.OtpService otpService;
    private final UserRepository userRepository;

    public VerificationService(africa.techverse.growthacy.v1.services.EmailService emailService, africa.techverse.growthacy.v1.services.OtpService otpService, UserRepository userRepository) {
        this.emailService = emailService;
        this.otpService = otpService;
        this.userRepository = userRepository;
    }

    public Otp sendEmailVerificationCode(User user) throws MessagingException, UnsupportedEncodingException {
        Otp otp = otpService.generateCode(user);
        emailService.sendEmail(
                user.getEmail(),
                "Email Verification Code",
                "This is your verification code: "+otp.getCode() + " to verify your email address"
        );
        return otp;
    }

    public Boolean verifyCode(User user, int code) {
        boolean isValidCode = this.otpService.verifyCode(user, code);
        if (isValidCode) {
            user.setEmailVerifiedAt(LocalDateTime.now());
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
