package user_service.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import user_service.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RegisterReq {

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 15, message = "Name must be at least 3 characters")
    private String name;
    @NotNull(message = "Surname is required")
    @Size(min = 3, max = 20, message = "Surname must be at least 3 characters")
    private String surname;
    @NotNull(message = "Email is required")
    @Email
    private String email;
    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
