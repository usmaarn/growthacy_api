package africa.techverse.growthacy.v1.repositories;

import africa.techverse.growthacy.v1.entities.Company;
import africa.techverse.growthacy.v1.entities.Otp;
import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.enums.UserType;
import africa.techverse.growthacy.v1.utils.Encoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Encoder encoder;

    @Test
    public void saveAmbassador(){
        User ambassador = User.builder()
                .name("John Doe")
                .email("john@doe.com")
                .phone("07088990099")
                .password(encoder.encode("123456"))
                .referralCode("Test_coder")
                .type(UserType.AMBASSADOR.getValue())
                .build();
        userRepository.save(ambassador);
    }

    @Test
    public void saveCompany(){
        Company companyInfo = Company.builder()
                .address("test address")
                .build();
        User company = User.builder()
                .name("Test Company")
                .email("company@test.com")
                .phone("07088990098")
                .password(encoder.encode("123456"))
                .type(UserType.COMPANY.getValue())
                .company(companyInfo)
                .build();
        userRepository.save(company);
    }

    @Test
    public void printAllUsers(){
        List<User> users = userRepository.findAll();
        System.out.println("All Users = "+users);
    }

    @Test
    public void printAmbassadors(){
        List<User> users = userRepository.findByType(UserType.AMBASSADOR.getValue());
        System.out.println("Ambassadors = "+users);
    }

    @Test
    public void printCompanies(){
        List<User> users = userRepository.findByType(UserType.COMPANY.getValue());
        System.out.println("Companies = "+users);
    }

    @Test
    public void saveWithOtp(){
        Otp otp = Otp.builder()
                .code(123456)
                .build();

        User ambassador = User.builder()
                .name("John Doe")
                .email("john@doe.com")
                .phone("07088990099")
                .password(encoder.encode("123456"))
                .referralCode("Test_coder")
                .type(UserType.AMBASSADOR.getValue())
                .build();
        ambassador.setOtps(List.of(otp));
        userRepository.save(ambassador);
    }
}