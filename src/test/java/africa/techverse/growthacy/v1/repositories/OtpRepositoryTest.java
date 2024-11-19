package africa.techverse.growthacy.v1.repositories;

import africa.techverse.growthacy.v1.entities.Otp;
import africa.techverse.growthacy.v1.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OtpRepositoryTest {
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        User user = userRepository.findByEmail("john@doe.com").orElseThrow();
        Otp otp = Otp.builder()
                .code(123456)
                .user(user)
                .build();
        otpRepository.save(otp);
    }

    @Test
    public void findByUser(){
        User user = userRepository.findByEmail("john@doe.com").orElseThrow();
        List<Otp> otps = otpRepository.findByUser(user);
        System.out.println(otps);
    }
}