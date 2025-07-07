package user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    Long id;

    String name;
    String surname;
    @Column(unique = true)
    @Email
    String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password;
    String phoneNumber;
    Role role;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean emailVerified;
}
