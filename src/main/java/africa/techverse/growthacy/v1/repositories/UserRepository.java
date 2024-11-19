package africa.techverse.growthacy.v1.repositories;

import africa.techverse.growthacy.v1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByType(int type);
    List<User> findByStatus(int status);
}