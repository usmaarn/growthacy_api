package africa.techverse.growthacy.v1.configurations;

import africa.techverse.growthacy.v1.utils.Encoder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabaseConfiguration {
    private final Encoder encoder;

    public LoadDatabaseConfiguration(Encoder encoder) {
        this.encoder = encoder;
    }

//    @Bean
//    public CommandLineRunner initDatabase(UserRepository userRepository) {
//        return args -> {
//            User user = User
//                    .builder()
//                    .name("Dev Test")
//                    .email("dev@test.com")
//                    .phone("09077022461")
//                    .type(UserType.AMBASSADOR.getValue())
//                    .status(UserStatus.ACTIVE.getValue())
//                    .referralCode("testCode")
//                    .password(encoder.encode("password"))
//                    .build();
//            userRepository.save(user);
//        };
//    }
}
