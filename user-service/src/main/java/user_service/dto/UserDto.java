package user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import user_service.model.Role;
import user_service.model.Gender;
import user_service.model.BloodType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String tckn;
    String name;
    String surname;
    String email;
    String phoneNumber;
    LocalDate birthDate;
    Gender gender;
    String address;
    BloodType bloodType;
    Role role;
}
