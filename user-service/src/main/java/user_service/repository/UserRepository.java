package user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user_service.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByEmail(String email);
    List<User> findByName(String name);
    List<User> findBySurname(String surname);
    List<User> findByNameAndSurname(String name, String surname);
    
}
