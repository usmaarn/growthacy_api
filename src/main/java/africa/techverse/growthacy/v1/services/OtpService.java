package africa.techverse.growthacy.v1.services;


import africa.techverse.growthacy.v1.entities.Otp;
import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.repositories.OtpRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class OtpService {
    private final OtpRepository otpRepository;
    Faker faker = new Faker(new Locale("en-US"));

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public Otp generateCode(User user){
        int code = faker.number().numberBetween(100000, 999999);
        Otp otp = Otp.builder()
                .code(code)
                .user(user)
                .build();
        return otpRepository.save(otp);
    }

    public boolean verifyCode(User user, int code){
        Otp otp = otpRepository.findByCode(code).orElse(null);
        if(otp == null) return false;
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())){
            otpRepository.delete(otp);
            return false;
        }
        return otp.getUser().getId().equals(user.getId());
    }

    public void deleteOtp(int code){
        Otp otp = otpRepository.findByCode(code).orElse(null);
        if (otp == null) return;
        otpRepository.delete(otp);
    }
}
